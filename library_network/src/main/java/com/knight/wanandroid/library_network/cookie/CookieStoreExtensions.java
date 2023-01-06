package com.knight.wanandroid.library_network.cookie;

import android.os.Build;
import android.webkit.CookieManager;

import java.net.CookieStore;
import java.net.HttpCookie;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Author:Knight
 * Time:2022/11/14 10:56
 * Description:CookieStoreExtensions
 */
public class CookieStoreExtensions {

    public static String toSetCookieString(HttpCookie httpCookie) {
        String expires = "";
        String path = "";
        String domain = "";
        String secure = "";
        String httpOnly = "";
        if (httpCookie.getMaxAge() != -1L) {
            DateFormat dateFormat = new SimpleDateFormat("EEE,dd MMM yyyy HH:mm:ss z", Locale.UK);
            TimeZone timeZone = TimeZone.getTimeZone("GMT");
            Calendar calendar = Calendar.getInstance(Locale.UK);
            calendar.set(Calendar.SECOND, (int) httpCookie.getMaxAge());
            expires = ";expires= " +dateFormat.format(calendar.getTime());
        }
        if (httpCookie.getPath() != null) {
            path = ";path=" + httpCookie.getPath();
        }

        if (httpCookie.getDomain() != null) {
            domain = ";domain=" + httpCookie.getDomain();
        }

        if (httpCookie.getSecure()) {
            secure = ";secure";
        }

        if (Build.VERSION.SDK_INT >= 24) {
            if (httpCookie.isHttpOnly()) {
                httpOnly = ";httponly";
            }
        }
        return httpCookie.getName() +"=" +httpCookie.getValue()+expires+path+domain+secure+httpOnly;

    }

    public synchronized static void syncToWebKitCookieManager(CookieStore cookieStore) {
       CookieManager cookieManager =  CookieManager.getInstance();
       for (HttpCookie httpCookie: cookieStore.getCookies()) {
           String hostUrl = "";
           if (httpCookie.getSecure()) {
               hostUrl = "https://" + httpCookie.getDomain();
           } else {
               hostUrl = "http://" + httpCookie.getDomain();
           }
           cookieManager.setCookie(hostUrl,toSetCookieString(httpCookie));
       }

       if (Build.VERSION.SDK_INT >= 21) {
            cookieManager.flush();
       }
    }


    public synchronized static void removeAll(CookieManager cookieManager) {
        if (Build.VERSION.SDK_INT >= 21) {
            cookieManager.removeAllCookies(null);
            cookieManager.flush();
        } else {
            cookieManager.removeAllCookie();
        }
    }
}
