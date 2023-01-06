package com.knight.wanandroid.library_network.cookie;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.knight.wanandroid.library_network.model.InternalCookie;

import java.lang.reflect.Type;
import java.net.HttpCookie;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Author:Knight
 * Time:2023/1/5 15:15
 * Description:SharedPreferencesCookieStore
 */
public class SharedPreferencesCookieStore extends InMemoryCookieStore {

    private SharedPreferences preference;
    private Gson gson = new Gson();

    public SharedPreferencesCookieStore (Context context,String name) {
        super(name);
        synchronized (SharedPreferencesCookieStore.class) {
            preference = context.getSharedPreferences(name,Context.MODE_PRIVATE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                preference.getAll().forEach(((key,value) ->{
                    URI index =  URI.create(key);
                    Type listType = new TypeToken<List<InternalCookie>>() {}.getType();
                    List<InternalCookie> internalCookies = gson.fromJson(value.toString(),listType);
                    List<HttpCookie> cookies = internalCookies.stream().map(it->it.toHttpCookie(it.getName(),it.getValue())).collect(Collectors.toList());
                    uriIndex.put(index,cookies);
                }));
            }
        }
    }


    @Override
    public boolean removeAll() {
        synchronized (SharedPreferencesCookieStore.class) {
            super.removeAll();
            preference.edit().clear().apply();
            return true;
        }
    }

    @Override
    public void add(URI uri, HttpCookie cookie) {
        synchronized (SharedPreferencesCookieStore.class) {
            if (uri == null) {
                return;
            }
            super.add(uri, cookie);
            URI index = getEffectiveURI(uri);
            List<HttpCookie> cookies = uriIndex.get(index);
            if (cookies == null) {
                return;
            }

            List<InternalCookie> internalCookies = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                internalCookies = cookies.stream().map(it -> {
                    InternalCookie internalCookie = new InternalCookie(it);
                    internalCookie.setHttpOnly(it.isHttpOnly());
                    return internalCookie;
                }).collect(Collectors.toList());
            }
            Type listType = new TypeToken<List<InternalCookie>>() {}.getType();
            String json = gson.toJson(internalCookies,listType);
            preference.edit().putString(index.toString(),json).apply();
        }

    }


    @Override
    public boolean remove(URI uri, HttpCookie cookie) {
        synchronized (SharedPreferencesCookieStore.class) {
            if (uri == null) {
                return false;

            }
            boolean result = super.remove(uri, cookie);
            URI index = getEffectiveURI(uri);
            List<HttpCookie> cookies = uriIndex.get(index);
            List<InternalCookie> internalCookies = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                internalCookies = cookies.stream().map(it -> {
                    InternalCookie internalCookie = new InternalCookie(it);
                    internalCookie.setHttpOnly(it.isHttpOnly());
                    return internalCookie;
                }).collect(Collectors.toList());
            }
            Type listType = new TypeToken<List<InternalCookie>>() {}.getType();
            String json = gson.toJson(internalCookies,listType);

            if (getCookies() == null) {
                preference.edit().remove(index.toString()).apply();
            } else {
                preference.edit().putString(index.toString(),json).apply();
            }
            return result;

        }



    }
}
