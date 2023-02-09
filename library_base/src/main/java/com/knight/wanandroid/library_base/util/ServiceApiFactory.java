package com.knight.wanandroid.library_base.util;

import android.util.ArrayMap;

import androidx.annotation.Nullable;

import java.util.ServiceLoader;

/**
 * Author:Knight
 * Time:2023/2/8 16:08
 * Description:ServiceFactory
 */
public class ServiceApiFactory {
    private static class SingleTonHolder {
        private static final ServiceApiFactory INSTANCE = new ServiceApiFactory();
    }

    public static ServiceApiFactory getInstance() {
        return SingleTonHolder.INSTANCE;
    }

    private final ArrayMap<Class, ServiceLoader> loaderMap = new ArrayMap<>();
    private final ArrayMap<Class, Object> serviceMap = new ArrayMap<>();

    private ServiceApiFactory() {

    }

    @Nullable
    public <T> T getService(Class<T> clazz) {
        Object o = serviceMap.get(clazz);
        if (o != null && isInterface(o.getClass(),clazz.getName())) {
            return (T) o;
        }
        ServiceLoader serviceLoader = loaderMap.get(clazz);
        if (serviceLoader == null) {
            serviceLoader = ServiceLoader.load(clazz);
            loaderMap.put(clazz, serviceLoader);
        }
        if (serviceLoader != null && serviceLoader.iterator().hasNext()) {
            T next = (T) serviceLoader.iterator().next();
            serviceMap.put(clazz, next);
            return next;
        }
        return null;
    }


    public boolean isInterface(Class c, String szInterface) {
        Class[] face = c.getInterfaces();
        for (Class aClass : face) {
            if (aClass.getName().equals(szInterface)) {
                return true;
            } else {
                Class[] face1 = aClass.getInterfaces();
                for (Class value : face1) {
                    if (value.getName().equals(szInterface)) {
                        return true;
                    } else if (isInterface(value, szInterface)) {
                        return true;
                    }
                }
            }
        }
        if (null != c.getSuperclass()) {
            return isInterface(c.getSuperclass(), szInterface);
        }
        return false;
    }
}
