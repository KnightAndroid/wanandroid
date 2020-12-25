package com.knight.wanandroid.library_network;

import com.knight.wanandroid.library_network.request.DeleteRequest;
import com.knight.wanandroid.library_network.request.DownloadRequest;
import com.knight.wanandroid.library_network.request.GetRequest;
import com.knight.wanandroid.library_network.request.HeadRequest;
import com.knight.wanandroid.library_network.request.PatchRequest;
import com.knight.wanandroid.library_network.request.PostRequest;
import com.knight.wanandroid.library_network.request.PutRequest;

import androidx.lifecycle.LifecycleOwner;
import okhttp3.Call;
import okhttp3.OkHttpClient;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2020/12/25 18:21
 * @descript:
 */
public class GoHttp {



    /**
     * Get 请求
     *
     * @param lifecycleOwner      请传入 AppCompatActivity 或者 AndroidX.Fragment 子类
     *                            如需传入其他对象请参考以下两个类
     *                            {@linkcom.knight.wanandroid.library_newwork.lifecycle.ActivityLifecycle}
     *                            {@link com.knight.wanandroid.library_network.lifecycle.ApplicationLifecycle}
     */
    public static GetRequest get(LifecycleOwner lifecycleOwner) {
        return new GetRequest(lifecycleOwner);
    }

    /**
     * Post 请求
     *
     * @param lifecycleOwner      请传入 AppCompatActivity 或者 AndroidX.Fragment 子类
     *                            如需传入其他对象请参考以下两个类
     *                            {@link com.knight.wanandroid.library_network.lifecycle.ActivityLifecycle}
     *                            {@link com.knight.wanandroid.library_network.lifecycle.ApplicationLifecycle}
     */
    public static PostRequest post(LifecycleOwner lifecycleOwner) {
        return new PostRequest(lifecycleOwner);
    }

    /**
     * Head 请求
     *
     * @param lifecycleOwner      请传入 AppCompatActivity 或者 AndroidX.Fragment 子类
     *                            如需传入其他对象请参考以下两个类
     *                            {@link com.knight.wanandroid.library_network.lifecycle.ActivityLifecycle}
     *                            {@link com.knight.wanandroid.library_network.lifecycle.ApplicationLifecycle}
     */
    public static HeadRequest head(LifecycleOwner lifecycleOwner) {
        return new HeadRequest(lifecycleOwner);
    }

    /**
     * Delete 请求
     *
     * @param lifecycleOwner      请传入 AppCompatActivity 或者 AndroidX.Fragment 子类
     *                            如需传入其他对象请参考以下两个类
     *                            {@link com.knight.wanandroid.library_network.lifecycle.ActivityLifecycle}
     *                            {@link com.knight.wanandroid.library_network.lifecycle.ApplicationLifecycle}
     */
    public static DeleteRequest delete(LifecycleOwner lifecycleOwner) {
        return new DeleteRequest(lifecycleOwner);
    }

    /**
     * Put 请求
     *
     * @param lifecycleOwner      请传入 AppCompatActivity 或者 AndroidX.Fragment 子类
     *                            如需传入其他对象请参考以下两个类
     *                            {@link com.knight.wanandroid.library_network.lifecycle.ActivityLifecycle}
     *                            {@link com.knight.wanandroid.library_network.lifecycle.ApplicationLifecycle}
     */
    public static PutRequest put(LifecycleOwner lifecycleOwner) {
        return new PutRequest(lifecycleOwner);
    }

    /**
     * Patch 请求
     *
     * @param lifecycleOwner      请传入 AppCompatActivity 或者 AndroidX.Fragment 子类
     *                            如需传入其他对象请参考以下两个类
     *                            {@link com.knight.wanandroid.library_network.lifecycle.ActivityLifecycle}
     *                            {@link com.knight.wanandroid.library_network.lifecycle.ApplicationLifecycle}
     */
    public static PatchRequest patch(LifecycleOwner lifecycleOwner) {
        return new PatchRequest(lifecycleOwner);
    }

    /**
     * 下载请求
     *
     * @param lifecycleOwner      请传入 AppCompatActivity 或者 AndroidX.Fragment 子类
     *                            如需传入其他对象请参考以下两个类
     *                            {@link com.knight.wanandroid.library_network.lifecycle.ActivityLifecycle}
     *                            {@link com.knight.wanandroid.library_network.lifecycle.ApplicationLifecycle}
     */
    public static DownloadRequest download(LifecycleOwner lifecycleOwner) {
        return new DownloadRequest(lifecycleOwner);
    }

    /**
     * 取消请求
     */
    public static void cancel(LifecycleOwner lifecycleOwner) {
        cancel(String.valueOf(lifecycleOwner));
    }

    /**
     * 根据 TAG 取消请求任务
     */
    public static void cancel(Object tag) {
        if (tag == null) {
            return;
        }

        OkHttpClient client = HttpConfig.getInstance().getClient();

        // 清除排队等候的任务
        for (Call call : client.dispatcher().queuedCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }

        // 清除正在执行的任务
        for (Call call : client.dispatcher().runningCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
    }

    /**
     * 清除所有请求任务
     */
    public static void cancel() {
        OkHttpClient client = HttpConfig.getInstance().getClient();

        // 清除排队等候的任务
        for (Call call : client.dispatcher().queuedCalls()) {
            call.cancel();
        }

        // 清除正在执行的任务
        for (Call call : client.dispatcher().runningCalls()) {
            call.cancel();
        }
    }
}