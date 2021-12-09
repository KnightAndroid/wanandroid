package com.knight.wanandroid.library_network.model;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.gson.JsonSyntaxException;
import com.knight.wanandroid.library_common.utils.CacheUtils;
import com.knight.wanandroid.library_network.EasyLog;
import com.knight.wanandroid.library_network.R;
import com.knight.wanandroid.library_network.config.IRequestApi;
import com.knight.wanandroid.library_network.config.IRequestHandler;
import com.knight.wanandroid.library_network.exception.CancelException;
import com.knight.wanandroid.library_network.exception.DataException;
import com.knight.wanandroid.library_network.exception.HttpException;
import com.knight.wanandroid.library_network.exception.NetworkException;
import com.knight.wanandroid.library_network.exception.ResponseException;
import com.knight.wanandroid.library_network.exception.ResultException;
import com.knight.wanandroid.library_network.exception.ServerException;
import com.knight.wanandroid.library_network.exception.TimeoutException;
import com.knight.wanandroid.library_network.exception.TokenException;
import com.knight.wanandroid.library_network.json.GsonFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import androidx.lifecycle.LifecycleOwner;
import okhttp3.Headers;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2020/12/25 18:39
 * @descript:请求处理类
 */
public final class RequestHandler implements IRequestHandler {

    private final Application mApplication;

    public RequestHandler(Application application) {
        mApplication = application;
    }

    @Override
    public Object requestSucceed(LifecycleOwner lifecycle, IRequestApi api, Response response, Type type) throws Exception {

        if (Response.class.equals(type)) {
            return response;
        }

        if (!response.isSuccessful()) {
            // 返回响应异常
            throw new ResponseException(mApplication.getString(R.string.library_network_http_response_error) + "，responseCode：" + response.code() + "，message：" + response.message(), response);
        }

        if (Headers.class.equals(type)) {
            return response.headers();
        }

        ResponseBody body = response.body();
        if (body == null) {
            return null;
        }

        if (InputStream.class.equals(type)) {
            return body.byteStream();
        }

        String text;
        try {
            text = body.string();
        } catch (IOException e) {
            // 返回结果读取异常
            throw new DataException(mApplication.getString(R.string.library_network_http_data_explain_error), e);
        }
        // 打印这个 Json
        EasyLog.json(text);

        if (String.class.equals(type)) {
            return text;
        }

        if (JSONObject.class.equals(type)) {
            try {
                // 如果这是一个 JSONObject 对象
                return new JSONObject(text);
            } catch (JSONException e) {
                throw new DataException(mApplication.getString(R.string.library_network_http_data_explain_error), e);
            }
        }

        if (JSONArray.class.equals(type)) {
            try {
                // 如果这是一个 JSONArray 对象
                return new JSONArray(text);
            } catch (JSONException e) {
                throw new DataException(mApplication.getString(R.string.library_network_http_data_explain_error), e);
            }
        }
        final Object result;

        try {
            result = GsonFactory.getSingletonGson().fromJson(text, type);
        } catch (JsonSyntaxException e) {
            // 返回结果读取异常
            throw new DataException(mApplication.getString(R.string.library_network_http_data_explain_error), e);
        }

        if (result instanceof HttpData) {
            HttpData<?> model = (HttpData<?>) result;

            if (model.isRequestSucceed()) {
                // 代表执行成功
                return result;
            }

            if (model.isTokenFailure()) {
                // 代表登录失效，需要重新登录
                throw new TokenException(mApplication.getString(R.string.library_network_http_token_error));
            }

            // 代表执行失败
            throw new ResultException(model.getMessage(), model);
        }
        return result;
    }

    @Override
    public Exception requestFail(LifecycleOwner lifecycle, IRequestApi api, Exception e) {
        // 判断这个异常是不是自己抛的
        if (e instanceof HttpException) {
            if (e instanceof TokenException) {
                // 登录信息失效，跳转到登录页

            }

            return e;
        }

        if (e instanceof SocketTimeoutException) {
            return new TimeoutException(mApplication.getString(R.string.library_network_http_server_out_time), e);
        }

        if (e instanceof UnknownHostException) {
            NetworkInfo info = ((ConnectivityManager) mApplication.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
            // 判断网络是否连接
            if (info != null && info.isConnected()) {
                // 有连接就是服务器的问题
                return new ServerException(mApplication.getString(R.string.library_network_http_server_error), e);
            }
            // 没有连接就是网络异常
            return new NetworkException(mApplication.getString(R.string.library_network_http_network_error), e);
        }

        if (e instanceof IOException) {
            //e = new CancelException(context.getString(R.string.http_request_cancel), e);
            return new CancelException("", e);
        }

        return new HttpException(e.getMessage(), e);

    }

    @Override
    public Object readCache(LifecycleOwner lifecycle, IRequestApi api, Type type) {
        String cacheKey = GsonFactory.getSingletonGson().toJson(api);
        String cacheValue = CacheUtils.getCacheValue(cacheKey);
        if (cacheValue == null || "".equals(cacheValue) || "{}".equals(cacheValue)) {
            return null;
        }
        EasyLog.print("---------- cacheKey ----------");
        EasyLog.json(cacheKey);
        EasyLog.print("---------- cacheValue ----------");
        EasyLog.json(cacheValue);
        return GsonFactory.getSingletonGson().fromJson(cacheValue, type);
    }

    @Override
    public boolean writeCache(LifecycleOwner lifecycle, IRequestApi api, Response response, Object result) {
        String cacheKey = GsonFactory.getSingletonGson().toJson(api);
        String cacheValue = GsonFactory.getSingletonGson().toJson(result);
        if (cacheValue == null || "".equals(cacheValue) || "{}".equals(cacheValue)) {
            return false;
        }
        EasyLog.print("---------- cacheKey ----------");
        EasyLog.json(cacheKey);
        EasyLog.print("---------- cacheValue ----------");
        EasyLog.json(cacheValue);
        return CacheUtils.saveCacheValue(cacheKey, cacheValue);
    }
}
