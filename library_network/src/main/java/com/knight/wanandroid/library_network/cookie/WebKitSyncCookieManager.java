package com.knight.wanandroid.library_network.cookie;

import android.util.Log;

import androidx.annotation.Nullable;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.CookieStore;
import java.net.URI;
import java.util.Map;

/**
 * Author:Knight
 * Time:2023/1/5 16:45
 * Description:WebKitSyncCookieManager
 */
public class WebKitSyncCookieManager extends CookieManager {


    public WebKitSyncCookieManager(CookieStore store, CookiePolicy cookiePolicy) {
        super(store, cookiePolicy);
        try {
            android.webkit.CookieManager.getInstance().setAcceptCookie(true);
        } catch (Throwable throwable) {
            //if (this.onWebKitCookieManagerError == null || this.onWebKitCookieManagerError.invoke(throwable) == null) {
                Log.e("COOKIE-STORE", "Unhandled WebKitSyncCookieManager error. You could handle it by setting onWebKitCookieManagerError when you create WebKitSyncCookieManager. This exception is caused by the underlying android.webkit.CookieManager", throwable);
           // }
        }
    }



    @Override
    public void put(@Nullable URI uri, @Nullable Map responseHeaders) throws IOException {
        super.put(uri, responseHeaders);
        try {
            CookieStoreExtensions.syncToWebKitCookieManager(getCookieStore());
        } catch (Throwable throwable) {
           // if (onWebKitCookieManagerError == null || (Unit)onWebKitCookieManagerError.invoke(throwable) == null) {
                Log.e("COOKIE-STORE", "Unhandled WebKitSyncCookieManager error. You could handle it by setting onWebKitCookieManagerError when you create WebKitSyncCookieManager. This exception is caused by the underlying android.webkit.CookieManager", throwable);
          //  }
        }

    }


    public void removeAll() {
        getCookieStore().removeAll();
        try {
            CookieStoreExtensions.removeAll(android.webkit.CookieManager.getInstance());
        } catch (Throwable throwable) {
          //  if (onWebKitCookieManagerError == null || (Unit)onWebKitCookieManagerError.invoke(throwable) == null) {
                Log.e("COOKIE-STORE", "Unhandled WebKitSyncCookieManager error. You could handle it by setting onWebKitCookieManagerError when you create WebKitSyncCookieManager. This exception is caused by the underlying android.webkit.CookieManager", throwable);
          //  }
        }
    }





}
