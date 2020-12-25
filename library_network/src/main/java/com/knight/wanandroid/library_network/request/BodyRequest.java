package com.knight.wanandroid.library_network.request;

import com.knight.wanandroid.library_network.EasyLog;
import com.knight.wanandroid.library_network.HttpConfig;
import com.knight.wanandroid.library_network.NetWorkUtils;
import com.knight.wanandroid.library_network.body.JsonBody;
import com.knight.wanandroid.library_network.body.ProgressBody;
import com.knight.wanandroid.library_network.body.StringBody;
import com.knight.wanandroid.library_network.body.UpdateBody;
import com.knight.wanandroid.library_network.data.BodyType;
import com.knight.wanandroid.library_network.data.HttpHeaders;
import com.knight.wanandroid.library_network.data.HttpParams;
import com.knight.wanandroid.library_network.listener.OnHttpListener;
import com.knight.wanandroid.library_network.listener.OnUpdateListener;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import androidx.lifecycle.LifecycleOwner;
import okhttp3.FormBody;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2020/12/25 18:09
 * @descript:带 RequestBody 请求
 */
public abstract class BodyRequest<T extends BodyRequest> extends BaseRequest<T> {

    private OnUpdateListener mUpdateListener;

    private RequestBody mRequestBody;

    public BodyRequest(LifecycleOwner lifecycleOwner) {
        super(lifecycleOwner);
    }

    public T body(Map map) {
        if (map == null) {
            return (T) this;
        }
        return body(new JsonBody(map));
    }

    public T body(List list) {
        if (list == null) {
            return (T) this;
        }
        return body(new JsonBody(list));
    }

    public T body(String text) {
        if (text == null) {
            return (T) this;
        }
        return body(new StringBody(text));
    }

    /**
     * 自定义 RequestBody
     */
    public T body(RequestBody body) {
        mRequestBody = body;
        return (T) this;
    }

    @Override
    protected Request createRequest(String url, String tag, HttpParams params, HttpHeaders headers, BodyType type) {
        Request.Builder request = new Request.Builder();
        request.url(url);

        EasyLog.print("RequestUrl", url);
        EasyLog.print("RequestMethod", getRequestMethod());

        if (tag != null) {
            request.tag(tag);
        }

        // 添加请求头
        if (!headers.isEmpty()) {
            for (String key : headers.getNames()) {
                request.addHeader(key, headers.get(key));
            }
        }

        RequestBody body = mRequestBody != null ? mRequestBody : createBody(params, type);
        request.method(getRequestMethod(), body);

        // 打印请求头和参数的日志
        if (HttpConfig.getInstance().isLogEnabled()) {

            if (!headers.isEmpty() || !params.isEmpty()) {
                EasyLog.print();
            }

            for (String key : headers.getNames()) {
                EasyLog.print(key, headers.get(key));
            }

            if (!headers.isEmpty() && !params.isEmpty()) {
                EasyLog.print();
            }

            if (body instanceof FormBody ||
                    body instanceof MultipartBody ||
                    body instanceof ProgressBody) {
                // 打印表单
                for (String key : params.getNames()) {
                    Object value = params.get(key);
                    if (value instanceof String) {
                        EasyLog.print(key, "\"" + value + "\"");
                    } else {
                        EasyLog.print(key, String.valueOf(value));
                    }
                }
            } else if (body instanceof JsonBody) {
                // 打印 Json
                EasyLog.json(body.toString());
            } else {
                // 打印文本
                EasyLog.print(body.toString());
            }

            if (!headers.isEmpty() || !params.isEmpty()) {
                EasyLog.print();
            }
        }

        return request.build();
    }

    /**
     * 执行异步请求（执行传入上传进度监听器）
     */
    @Override
    public T request(OnHttpListener<?> listener) {
        if (listener instanceof OnUpdateListener) {
            mUpdateListener = (OnUpdateListener) listener;
        }
        return super.request(listener);
    }

    /**
     * 组装 RequestBody 对象
     */
    private RequestBody createBody(HttpParams params, BodyType type) {
        if (params.isMultipart() && !params.isEmpty()) {
            MultipartBody.Builder builder = new MultipartBody.Builder();
            builder.setType(MultipartBody.FORM);
            for (String key : params.getNames()) {
                Object object = params.get(key);

                // 如果这是一个文件
                if (object instanceof File) {
                    MultipartBody.Part part = NetWorkUtils.createPart(key, (File) object);
                    if (part != null) {
                        builder.addPart(part);
                    }
                    continue;
                }

                // 如果这是一个输入流
                if (object instanceof InputStream) {
                    MultipartBody.Part part = NetWorkUtils.createPart(key, (InputStream) object);
                    if (part != null) {
                        builder.addPart(part);
                    }
                    continue;
                }

                // 如果这是一个自定义 RequestBody
                if (object instanceof RequestBody) {
                    if (object instanceof UpdateBody) {
                        builder.addFormDataPart(key, NetWorkUtils.encodeString(((UpdateBody) object).getName()), (RequestBody) object);
                    } else {
                        builder.addFormDataPart(key, null, (RequestBody) object);
                    }
                    continue;
                }

                // 上传文件列表
                if (object instanceof List && NetWorkUtils.isFileList((List) object)) {
                    for (Object item : (List) object) {
                        MultipartBody.Part part = NetWorkUtils.createPart(key, (File) item);
                        if (part != null) {
                            builder.addPart(part);
                        }
                    }
                    continue;
                }

                // 如果这是一个普通参数
                builder.addFormDataPart(key, String.valueOf(object));
            }

            if (mUpdateListener != null) {
                return new ProgressBody(builder.build(), getLifecycleOwner(), mUpdateListener);
            } else {
                return builder.build();
            }
        }

        if (type == BodyType.JSON) {
            if (mUpdateListener != null) {
                return new ProgressBody(new JsonBody(params.getParams()), getLifecycleOwner(), mUpdateListener);
            } else {
                return new JsonBody(params.getParams());
            }
        }

        FormBody.Builder builder = new FormBody.Builder();
        if (!params.isEmpty()) {
            for (String key : params.getNames()) {
                builder.add(key, String.valueOf(params.get(key)));
            }
        }
        if (mUpdateListener != null) {
            return new ProgressBody(builder.build(), getLifecycleOwner(), mUpdateListener);
        } else {
            return builder.build();
        }
    }
}