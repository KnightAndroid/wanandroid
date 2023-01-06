package com.knight.wanandroid.library_network.cookie;


import androidx.annotation.NonNull;
import java.io.IOException;
import java.net.CookieHandler;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * Author:Knight
 * Time:2023/1/5 17:31
 * Description:JavaNetCookieJar
 */
public class JavaNetCookieJar implements CookieJar {


    private final ThreadLocal<DateFormat> STANDARD_DATE_FORMAT = new ThreadLocal<DateFormat>(){
        @NonNull
        protected DateFormat initialValue() {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.US);
            simpleDateFormat.setLenient(false);
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            return simpleDateFormat;
        }
    };

    private CookieHandler cookieHandler;
    public JavaNetCookieJar(CookieHandler cookieHandler) {
        this.cookieHandler = cookieHandler;
    }

    private Logger getLogger() {
        return Logger.getLogger(getClass().getName());
    }

    private void log(int level, String message, Throwable t) {
        Level logLevel = level == 5 ? Level.WARNING : Level.INFO;
        this.getLogger().log(logLevel, message, t);
    }


    private String toHttpDateString(Date date) {
        return STANDARD_DATE_FORMAT.get().format(date);
    }

    private String toString(Cookie cookie, boolean forObsoleteRfc2965) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(cookie.name());
        stringBuilder.append('=');
        stringBuilder.append(cookie.value());
        if (cookie.persistent()) {
            if (cookie.expiresAt() == Long.MIN_VALUE) {
                stringBuilder.append("; max-age=0");
            } else {
                stringBuilder.append("; expires=").append(this.toHttpDateString(new Date(cookie.expiresAt())));
            }
        }

        if (!cookie.hostOnly()) {
            stringBuilder.append("; domain=");
            if (forObsoleteRfc2965) {
                stringBuilder.append(".");
            }

            stringBuilder.append(cookie.domain());
        }

        stringBuilder.append("; path=").append(cookie.path());
        if (cookie.secure()) {
            stringBuilder.append("; secure");
        }

        if (cookie.httpOnly()) {
            stringBuilder.append("; httponly");
        }
        return stringBuilder.toString();
    }


    private int delimiterOffset(String input, String delimiters, int startIndex, int endIndex) {
        for(int i = startIndex; i < endIndex; ++i) {
            if (delimiters.indexOf(input.charAt(i)) != -1) {
                return i;
            }
            return endIndex;
        }

        return endIndex;
    }

    //Kotlin支持的转移序列：\t、 \b、\n、\r、\'、\"、\\ 和 \$。转移其他字符要用Unicode系列转移语法：'\uFF00'
    private int indexOfFirstNonAsciiWhitespace(String delimiters, int startIndex, int endIndex) {
        int i = startIndex;
        while(i < endIndex) {
            switch(delimiters.charAt(i)) {
                case '\t':
                case '\n':
                case '\f':
                case '\r':
                case ' ':
                    ++i;
                    break;
                default:
                    return i;
            }
        }
        return endIndex;
    }



    private int indexOfLastNonAsciiWhitespace(String delimiters, int startIndex, int endIndex) {
        int i = endIndex - 1;
        if (i >= startIndex) {
            while(true) {
                switch(delimiters.charAt(i)) {
                    case '\t':
                    case '\n':
                    case '\f':
                    case '\r':
                    case ' ':
                        if (i == startIndex) {
                            return startIndex;
                        }
                        --i;
                        break;
                    default:
                        return i + 1;
                }
            }
        } else {
            return startIndex;
        }
    }


    private String trimSubstring(String delimiters, int startIndex, int endIndex) {
        int start = this.indexOfFirstNonAsciiWhitespace(delimiters, startIndex, endIndex);
        int end = this.indexOfLastNonAsciiWhitespace(delimiters, start, endIndex);
        return delimiters.substring(start, end);
    }

    @NonNull
    @Override
    public List<Cookie> loadForRequest(@NonNull HttpUrl httpUrl) {
        Map<String,List<String>> cookieHeaders;
        try {
            cookieHeaders = this.cookieHandler.get(httpUrl.uri(), new HashMap<>());
        } catch (IOException e) {
            StringBuilder stringBuilder = (new StringBuilder()).append("Loading cookies failed for ");
            HttpUrl mHttpUrl = httpUrl.resolve("/...");
            log(5, stringBuilder.append(mHttpUrl).toString(), (Throwable)e);
            return new ArrayList<>();
        }
        List<Cookie> cookies = new ArrayList<>();
        Set<String> set = cookieHeaders.keySet();
        for (String key : set) {
            if (("Cookie".equalsIgnoreCase(key) || "Cookie2".equalsIgnoreCase(key)) && cookieHeaders.get(key) != null) {
                    for (String header : cookieHeaders.get(key)) {
                        cookies.addAll(decodeHeaderAsJavaNetCookies(httpUrl,header));
                    }

                }
        }
        return cookies;
    }

    @Override
    public void saveFromResponse(@NonNull HttpUrl httpUrl, @NonNull List<Cookie> cookies) {
        List<String> cookieStrings = new ArrayList<>();
        for (Cookie cookie : cookies) {
            cookieStrings.add(toString(cookie, true));
        }
        Map<String,List<String>> multimap = new HashMap<>();
        multimap.put("Set-Cookie",cookieStrings);
        try {
            this.cookieHandler.put(httpUrl.uri(), multimap);
        } catch (IOException e) {
            StringBuilder stringBuilder = (new StringBuilder()).append("Saving cookies failed for ");
            HttpUrl mHttpUrl = httpUrl.resolve("/...");
            log(5, stringBuilder.append(mHttpUrl ).toString(), (Throwable)e);
        }
    }

    private List<Cookie> decodeHeaderAsJavaNetCookies(HttpUrl url, String header) {
        List<Cookie> result = new ArrayList<>();
        int pos = 0;
        int limit = header.length();
        while(pos < limit) {
            int pairEnd = delimiterOffset(header, ";,", pos, limit);
            int equalsSign = delimiterOffset(header, "=", pos, pairEnd);
            String name = trimSubstring(header, pos, equalsSign);

            if (name.startsWith("$")) {
                pos = pairEnd + 1;
            } else {
                String value = "";
                if (equalsSign < pairEnd) {
                    value = trimSubstring(header,equalsSign + 1,pairEnd);
                }

                if (value.startsWith("\"") && value.endsWith("\"")) {
                    value = value.substring(1,value.length() - 1);
                }

                result.add(new Cookie.Builder().name(name).value(value).domain(url.host()).build());
                pos = pairEnd + 1;
            }
        }
        return result;
    }
}
