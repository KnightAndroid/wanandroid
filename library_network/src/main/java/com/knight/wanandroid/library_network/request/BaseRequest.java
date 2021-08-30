package com.knight.wanandroid.library_network.request;

import com.knight.wanandroid.library_network.EasyLog;
import com.knight.wanandroid.library_network.HttpConfig;
import com.knight.wanandroid.library_network.NetWorkUtils;
import com.knight.wanandroid.library_network.annotation.HttpHeader;
import com.knight.wanandroid.library_network.annotation.HttpIgnore;
import com.knight.wanandroid.library_network.annotation.HttpRename;
import com.knight.wanandroid.library_network.callback.NormalCallback;
import com.knight.wanandroid.library_network.config.IRequestApi;
import com.knight.wanandroid.library_network.config.IRequestCache;
import com.knight.wanandroid.library_network.config.IRequestClient;
import com.knight.wanandroid.library_network.config.IRequestHandler;
import com.knight.wanandroid.library_network.config.IRequestHost;
import com.knight.wanandroid.library_network.config.IRequestInterceptor;
import com.knight.wanandroid.library_network.config.IRequestPath;
import com.knight.wanandroid.library_network.config.IRequestServer;
import com.knight.wanandroid.library_network.config.IRequestType;
import com.knight.wanandroid.library_network.config.RequestApi;
import com.knight.wanandroid.library_network.config.RequestServer;
import com.knight.wanandroid.library_network.data.BodyType;
import com.knight.wanandroid.library_network.data.CacheMode;
import com.knight.wanandroid.library_network.data.CallProxy;
import com.knight.wanandroid.library_network.data.HttpHeaders;
import com.knight.wanandroid.library_network.data.HttpParams;
import com.knight.wanandroid.library_network.data.ResponseClass;
import com.knight.wanandroid.library_network.lifecycle.HttpLifecycleControl;
import com.knight.wanandroid.library_network.listener.OnHttpListener;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import androidx.lifecycle.LifecycleOwner;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2020/12/25 18:08
 * @descript:
 */
public abstract class BaseRequest<T extends BaseRequest<?>> {


    /** 请求处理策略 */
    private IRequestHandler mRequestHandler = HttpConfig.getInstance().getHandler();

    /** 接口主机地址 */
    private IRequestHost mRequestHost = HttpConfig.getInstance().getServer();
    /** 接口路径地址 */
    private IRequestPath mRequestPath = HttpConfig.getInstance().getServer();
    /** OkHttp 客户端 */
    private IRequestClient mRequestClient = HttpConfig.getInstance().getServer();
    /** 提交参数类型 */
    private IRequestType mRequestType = HttpConfig.getInstance().getServer();
    /** 接口缓存方式 */
    private IRequestCache mRequestCache = HttpConfig.getInstance().getServer();
    /** 请求接口配置 */
    private IRequestApi mRequestApi;

    /** 请求生命周期控制 */
    private final LifecycleOwner mLifecycleOwner;

    /** 请求执行代理类 */
    private CallProxy mCallProxy;

    /** 请求标记 */
    private String mTag;


    /** 请求延迟 */
    private long mDelayMillis;
    public BaseRequest(LifecycleOwner lifecycleOwner) {
        if (lifecycleOwner == null) {
            throw new IllegalArgumentException("are you ok?");
        }
        mLifecycleOwner = lifecycleOwner;
        tag(lifecycleOwner);
    }

    public T api(Class<? extends IRequestApi> api) {
        try {
            return api(api.newInstance());
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public T api(String api) {
        return api(new RequestApi(api));
    }

    /**
     * 设置请求配置
     */
    public T api(IRequestApi api) {
        mRequestApi = api;
        if (api instanceof IRequestHost) {
            mRequestHost = (IRequestHost) api;
        }
        if (api instanceof IRequestPath) {
            mRequestPath = (IRequestPath) api;
        }
        if (api instanceof IRequestClient) {
            mRequestClient = (IRequestClient) api;
        }
        if (api instanceof IRequestType) {
            mRequestType = (IRequestType) api;
        }
        if (api instanceof IRequestCache) {
            mRequestCache = (IRequestCache) api;
        }
        if (api instanceof IRequestHandler) {
            mRequestHandler = (IRequestHandler) api;
        }
        return (T) this;
    }

    public T server(Class<? extends IRequestServer> api) {
        try {
            return server(api.newInstance());
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public T server(String host) {
        return server(new RequestServer(host));
    }

    /**
     * 替换服务器配器（推荐使用 api 的方式来替代 server，具体实现可见 api 方法源码）
     */
    public T server(IRequestServer server) {
        mRequestHost = server;
        mRequestPath = server;
        mRequestClient = server;
        mRequestType = server;
        mRequestCache = server;
        return (T) this;
    }

    public T delay(long delay, TimeUnit unit) {
        return delay(unit.toMillis(delay));
    }

    /**
     * 设置请求延迟执行
     *
     * @param delayMillis       延迟毫秒数
     */
    public T delay(long delayMillis) {
        mDelayMillis = delayMillis;
        return (T) this;
    }

    /**
     * 替换默认的请求处理策略
     */
    public T handler(IRequestHandler handler) {
        mRequestHandler = handler;
        return (T) this;
    }


    /**
     * 设置标记
     */
    public T tag(Object tag) {
        if (tag != null) {
            return tag(String.valueOf(tag));
        }
        return (T) this;
    }

    public T tag(String tag) {
        mTag = tag;
        return (T) this;
    }

    /**
     * 创建连接对象
     */
    protected Call createCall() {

        BodyType type = mRequestType.getType();

        HttpParams params = new HttpParams();
        HttpHeaders headers = new HttpHeaders();

        List<Field> fields = new ArrayList<>();
        Class<?> clazz = mRequestApi.getClass();

        do {
            Field[] declaredFields = clazz.getDeclaredFields();
            fields.addAll(0, Arrays.asList(declaredFields));
            // 遍历获取父类的字段
            clazz = clazz.getSuperclass();
        } while (clazz != null && !Object.class.equals(clazz));

        //当前请求是否存在流参数
        params.setMultipart(NetWorkUtils.isMultipart(fields));

        // 如果参数中包含流参数并且当前请求方式不是表单的话
        if (params.isMultipart() && type != BodyType.FORM) {
            // 就强制设置成以表单形式提交参数
            type = BodyType.FORM;
        }

        for (Field field : fields) {
            // 允许访问私有字段
            field.setAccessible(true);

            try {
                // 获取字段的对象
                Object value = field.get(mRequestApi);
                // 获取字段的名称
                String key;
                HttpRename annotation = field.getAnnotation(HttpRename.class);
                if (annotation != null) {
                    key = annotation.value();
                } else {
                    key = field.getName();
                    // 如果是内部类则会出现一个字段名为 this$0 的外部类对象，会导致无限递归，这里要忽略掉，如果使用静态内部类则不会出现这个问题
                    // 和规避 Kotlin 自动生成的伴生对象：https://github.com/getActivity/EasyHttp/issues/15
                    if (key.matches("this\\$\\d+") || "Companion".equals(key)) {
                        continue;
                    }
                }

                // 如果这个字段需要忽略，则进行忽略
                if (field.isAnnotationPresent(HttpIgnore.class)) {
                    if (field.isAnnotationPresent(HttpHeader.class)) {
                        headers.remove(key);
                    } else {
                        params.remove(key);
                    }
                    continue;
                }

                // 前提是这个字段值不能为空（基本数据类型有默认的值，而对象默认的值为 null）
                if (NetWorkUtils.isEmpty(value)) {
                    // 遍历下一个字段
                    continue;
                }

                // 如果这是一个请求头参数
                if (field.isAnnotationPresent(HttpHeader.class)) {
                    if (value instanceof Map) {
                        Map<?, ?> map = ((Map<?, ?>) value);
                        for (Object o : map.keySet()) {
                            if (o != null && map.get(o) != null) {
                                headers.put(String.valueOf(o), String.valueOf(map.get(o)));
                            }
                        }
                    } else {
                        headers.put(key, String.valueOf(value));
                    }
                    continue;
                }

                // 否则这就是一个普通的参数
                switch (type) {
                    case FORM:
                        if (value instanceof Map) {
                            Map<?, ?> map = ((Map<?, ?>) value);
                            for (Object o : map.keySet()) {
                                if (o != null && map.get(o) != null) {
                                    params.put(String.valueOf(o), map.get(o));
                                }
                            }
                        } else {
                            params.put(key, value);
                        }
                        break;
                    case JSON:
                        if (value instanceof List) {
                            // 如果这是一个 List 参数
                            params.put(key, NetWorkUtils.listToJsonArray(((List<?>) value)));
                        } else if (value instanceof Map) {
                            // 如果这是一个 Map 参数
                            params.put(key, NetWorkUtils.mapToJsonObject(((Map<?, ?>) value)));
                        } else if (NetWorkUtils.isBeanType(value)) {
                            // 如果这是一个 Bean 参数
                            params.put(key, NetWorkUtils.mapToJsonObject(NetWorkUtils.beanToHashMap(value)));
                        } else {
                            // 如果这是一个普通的参数
                            params.put(key, value);
                        }
                        break;
                    default:
                        break;
                }

            } catch (IllegalAccessException e) {
                EasyLog.print(e);
            }
        }

        String url = mRequestHost.getHost() + mRequestPath.getPath() + mRequestApi.getApi();
        IRequestInterceptor interceptor = HttpConfig.getInstance().getInterceptor();
        if (interceptor != null) {
            interceptor.interceptArguments(mRequestApi,params, headers);
        }
        return mRequestClient.getClient().newCall(createRequest(url, mTag, params, headers, type));
    }

    /**
     * 执行异步请求
     */
    public void request(OnHttpListener<?> listener) {
        if (mDelayMillis > 0) {
            EasyLog.print("RequestDelay", String.valueOf(mDelayMillis));
        }
        StackTraceElement[] stackTrace = new Throwable().getStackTrace();

        NetWorkUtils.postDelayed(()->{
            if (!HttpLifecycleControl.isLifecycleActive(mLifecycleOwner)) {
                EasyLog.print("宿主已被销毁，请求无法进行");
                return;
            }
            EasyLog.print(stackTrace);
            mCallProxy = new CallProxy(createCall());
            new NormalCallback(this)
                    .setListener(listener)
                    .setCall(mCallProxy)
                    .start();
        },mDelayMillis);

    }

    /**
     * 执行同步请求
     * @param responseClass                       需要解析泛型的对象
     * @return                  返回解析完成的对象
     * @throws Exception        如果请求失败或者解析失败则抛出异常
     */
    public <Bean> Bean execute(ResponseClass<Bean> responseClass) throws Exception {
        if (mDelayMillis > 0) {
            // 打印请求延迟时间
            EasyLog.print("RequestDelay", String.valueOf(mDelayMillis));
            Thread.sleep(mDelayMillis);
        }

        if (HttpLifecycleControl.isLifecycleActive(mLifecycleOwner)) {
            EasyLog.print("宿主已被销毁，请求无法进行");
            throw new IllegalStateException("The host has been destroyed and the request cannot proceed");
        }
        EasyLog.print(new Throwable().getStackTrace());
        Type reflectType = NetWorkUtils.getReflectType(responseClass);
        // 必须将 Call 对象创建放到这里来，否则无法显示请求日志
        mCallProxy = new CallProxy(createCall());
        CacheMode cacheMode = getRequestCache().getMode();

        if (cacheMode == CacheMode.USE_CACHE_ONLY || cacheMode == CacheMode.USE_CACHE_FIRST) {
            try {
                Object result = mRequestHandler.readCache(mLifecycleOwner, mRequestApi, reflectType);
                EasyLog.print("ReadCache result：" + result);
                if (cacheMode == CacheMode.USE_CACHE_FIRST) {
                    // 使用异步请求来刷新缓存
                    new NormalCallback(this)
                            .setCall(mCallProxy)
                            .start();
                }
                if (result != null) {
                    return (Bean) result;
                }
            } catch (Throwable throwable) {
                EasyLog.print("ReadCache error");
                EasyLog.print(throwable);
            }
        }


        try {
            Response response = mCallProxy.execute();
            Object result = mRequestHandler.requestSucceed(mLifecycleOwner, mRequestApi, response, reflectType);

            if (cacheMode == CacheMode.USE_CACHE_ONLY) {
                try {
                    boolean writeSucceed = mRequestHandler.writeCache(mLifecycleOwner, mRequestApi, response, result);
                    EasyLog.print("WriteCache result：" + writeSucceed);
                } catch (Throwable throwable) {
                    EasyLog.print("WriteCache error");
                    EasyLog.print(throwable);
                }
            }

            return (Bean) result;

        } catch (Exception e) {

            // 如果设置了只在网络请求失败才去读缓存
            if (e instanceof IOException && cacheMode == CacheMode.USE_CACHE_AFTER_FAILURE) {
                try {
                    Object result = mRequestHandler.readCache(mLifecycleOwner, mRequestApi, reflectType);
                    EasyLog.print("ReadCache result：" + result);
                    if (result != null) {
                        return (Bean) result;
                    }
                } catch (Throwable throwable) {
                    EasyLog.print("ReadCache error");
                    EasyLog.print(throwable);
                }
            }

            throw mRequestHandler.requestFail(mLifecycleOwner, mRequestApi, e);
        }
    }

    /**
     * 取消请求
     */
    public T cancel() {
        if (mCallProxy != null) {
            mCallProxy.cancel();
        }
        return (T) this;
    }

    /**
     * 获取生命周期管控对象
     */
    public LifecycleOwner getLifecycleOwner() {
        return mLifecycleOwner;
    }

    /**
     * 获取请求接口对象
     */
    public IRequestApi getRequestApi() {
        return mRequestApi;
    }

    /**
     * 获取请求处理对象
     */
    public IRequestHandler getRequestHandler() {
        return mRequestHandler;
    }

    /**
     * 获取请求缓存策略
     */
    public IRequestCache getRequestCache() {
        return mRequestCache;
    }

    /**
     * 获取请求的方式
     */
    protected abstract String getRequestMethod();

    /**
     * 获取延迟请求时间
     */
    protected long getDelayMillis() {
        return mDelayMillis;
    }

    /**
     * 创建请求的对象
     */
    protected abstract Request createRequest(String url, String tag, HttpParams params, HttpHeaders headers, BodyType type);
}