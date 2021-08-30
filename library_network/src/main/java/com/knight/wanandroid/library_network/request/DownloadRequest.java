package com.knight.wanandroid.library_network.request;

import android.content.ContentResolver;
import android.net.Uri;

import com.knight.wanandroid.library_network.EasyLog;
import com.knight.wanandroid.library_network.NetWorkUtils;
import com.knight.wanandroid.library_network.callback.DownloadCallback;
import com.knight.wanandroid.library_network.config.RequestApi;
import com.knight.wanandroid.library_network.config.RequestServer;
import com.knight.wanandroid.library_network.data.BodyType;
import com.knight.wanandroid.library_network.data.CallProxy;
import com.knight.wanandroid.library_network.data.FileContentResolver;
import com.knight.wanandroid.library_network.data.FileWrapper;
import com.knight.wanandroid.library_network.data.HttpHeaders;
import com.knight.wanandroid.library_network.data.HttpMethod;
import com.knight.wanandroid.library_network.data.HttpParams;
import com.knight.wanandroid.library_network.data.ResponseClass;
import com.knight.wanandroid.library_network.lifecycle.HttpLifecycleControl;
import com.knight.wanandroid.library_network.listener.OnDownloadListener;
import com.knight.wanandroid.library_network.listener.OnHttpListener;

import java.io.File;

import androidx.lifecycle.LifecycleOwner;
import okhttp3.Request;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2020/12/25 18:11
 * @descript:
 */
public final class DownloadRequest extends BaseRequest<DownloadRequest> {

    /** 下载请求方式 */
    private HttpMethod mMethod = HttpMethod.GET;

    /** 保存的文件 */
    private FileWrapper mFile;

    /** 校验的 md5 */
    private String mMd5;

    /** 下载监听回调 */
    private OnDownloadListener mListener;

    /** 请求执行对象 */
    private CallProxy mCallProxy;

    public DownloadRequest(LifecycleOwner lifecycleOwner) {
        super(lifecycleOwner);
    }

    /**
     * 设置请求方式
     */
    public DownloadRequest method(HttpMethod method) {
        mMethod = method;
        return this;
    }

    /**
     * 设置下载地址
     */
    public DownloadRequest url(String url) {
        server(new RequestServer(url));
        api(new RequestApi(""));
        return this;
    }

    /**
     * 设置保存的路径
     */

    public DownloadRequest file(String filePath) {
        return file(new File(filePath));
    }

    public DownloadRequest file(File file) {
        if (file instanceof FileContentResolver) {
            return file((FileContentResolver) file);
        }

        mFile = new FileWrapper(file);
        return this;
    }

    public DownloadRequest file(ContentResolver resolver, Uri uri) {
        return file(new FileContentResolver(resolver, uri));
    }

    public DownloadRequest file(FileContentResolver file) {
        mFile = file;
        return this;
    }

    /**
     * 设置 MD5 值
     */
    public DownloadRequest md5(String md5) {
        mMd5 = md5;
        return this;
    }

    /**
     * 设置下载监听
     */
    public DownloadRequest listener(OnDownloadListener listener) {
        mListener = listener;
        return this;
    }

    @Override
    protected Request createRequest(String url, String tag, HttpParams params, HttpHeaders headers, BodyType type) {
        switch (mMethod) {
            case GET:
                // 如果这个下载请求方式是 Get
                return new GetRequest(getLifecycleOwner()).createRequest(url, tag, params, headers, type);
            case POST:
                // 如果这个下载请求方式是 Post
                return new PostRequest(getLifecycleOwner()).createRequest(url, tag, params, headers, type);
            default:
                throw new IllegalStateException("are you ok?");
        }
    }

    /**
     * 开始下载
     */
    public DownloadRequest start() {
        long delayMillis = getDelayMillis();
        if (delayMillis > 0) {
            // 打印请求延迟时间
            EasyLog.print("RequestDelay", String.valueOf(delayMillis));
        }

        StackTraceElement[] stackTrace = new Throwable().getStackTrace();

        NetWorkUtils.postDelayed(() -> {
            if (!HttpLifecycleControl.isLifecycleActive(getLifecycleOwner())) {
                EasyLog.print("宿主已被销毁，请求无法进行");
                return;
            }
            EasyLog.print(stackTrace);
            mCallProxy = new CallProxy(createCall());
            new DownloadCallback(this)
                    .setFile(mFile)
                    .setMd5(mMd5)
                    .setListener(mListener)
                    .setCall(mCallProxy)
                    .start();
        }, delayMillis);

        return this;
    }

    /**
     * 取消下载
     */
    public DownloadRequest stop() {
        if (mCallProxy != null) {
            mCallProxy.cancel();
        }
        return this;
    }

    @Override
    public void request(OnHttpListener<?> listener) {
        // 请调用 start 方法
        throw new IllegalStateException("Call the start method");
    }

    @Override
    public <Bean> Bean execute(ResponseClass<Bean> responseClass) {
        // 请调用 start 方法
        throw new IllegalStateException("Call the start method");
    }

    @Override
    public DownloadRequest cancel() {
        // 请调用 stop 方法
        throw new IllegalStateException("Call the start method");
    }

    @Override
    protected String getRequestMethod() {
        return String.valueOf(mMethod);
    }
}