package com.knight.wanandroid.library_network.cookie;

import android.net.Uri;

import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import okhttp3.Cookie;

/**
 * Author:Knight
 * Time:2022/11/14 14:24
 * Description:InMemoryCookieStore
 */
public class InMemoryCookieStore implements CookieStore {

    private Map<URI, List<HttpCookie>> uriIndex = null;
    private ReentrantLock lock = null;

    @Override
    public void add(URI uri, HttpCookie cookie) {
        if (cookie == null) {
            throw new NullPointerException("cookie is null");
        }

        if (uri == null) {
            throw new NullPointerException("uri is null");
        }

        lock.lock();
        try {
           addIndex(this.getEffectiveURI(uri), cookie);
        } finally {
            lock.unlock();
        }



//        else {
//            this.lock.lock();
//
//            try {
//                this.cookieJar.remove(cookie);
//                if (cookie.getMaxAge() != 0L) {
//                    this.cookieJar.add(cookie);
//                    if (cookie.getDomain() != null) {
//                        this.addIndex(this.domainIndex, cookie.getDomain(), cookie);
//                    }
//
//                    if (uri != null) {
//                        this.addIndex(this.uriIndex, this.getEffectiveURI(uri), cookie);
//                    }
//                }
//            } finally {
//                this.lock.unlock();
//            }
//
//        }
    }

    @Override
    public List<HttpCookie> get(URI uri) {
        return null;
    }

    @Override
    public List<HttpCookie> getCookies() {

        List rt = new ArrayList<HttpCookie>();
        this.lock.lock();

        try {
            for (List list:uriIndex.values()) {
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    HttpCookie cookie = (HttpCookie) it.next();
                    if (cookie.hasExpired()) {
                        it.remove();
                    } else if (!rt.contains(cookie)) {
                        rt.add(cookie);
                    }
                }
            }
        } finally {
            lock.unlock();
        }
        return Collections.unmodifiableList(rt);
    }

    @Override
    public List<URI> getURIs() {
        List<URI> uris = new ArrayList<URI>();
        this.lock.lock();
        try {
            List result = new ArrayList(uriIndex.keySet());
            result.remove(null);
            return Collections.unmodifiableList(result);
        } finally {
            uris.addAll(uriIndex.keySet());
            lock.unlock();
        }
    }

    @Override
    public boolean remove(URI uri, HttpCookie cookie) {
        if (cookie == null) {
            throw new NullPointerException("cookie is null");
        }

        if (uri == null) {
            throw new NullPointerException("uri is null");
        }

        lock.lock();
        try {
            get
        } finally {
            lock.unlock();
        }

        return false;
    }

    @Override
    public boolean removeAll() {
        this.lock.lock();
        boolean result;
        try {
            result = !uriIndex.isEmpty();
            uriIndex.clear();
        } finally {
            lock.unlock();
        }
        return result;
    }

    private URI getEffectiveURI(URI uri) {
        try {
            if (uri.getScheme() == null) {
                return new URI("http",uri.getHost(),null,null,null);
            } else {
                return new URI(uri.getScheme(),uri.getHost(),null,null,null);
            }

        } catch (URISyntaxException ignored) {
            return uri;
        }
    }


    private void addIndex(URI index,HttpCookie cookie) {
        List<HttpCookie> cookies = uriIndex.get(index);
        if (cookies != null) {
            cookies.remove(cookie);
            cookies.add(cookie);
        } else {
            List<HttpCookie> newCookies = new ArrayList<>();
            newCookies.add(cookie);
            uriIndex.put(index,newCookies);
        }
    }


    private boolean netscapeDomainMatches(String domain,String host) {
        if (domain == null || host == null) {
            return false;
        }
        boolean isLocalDomain = ".local".equalsIgnoreCase(domain);
        int embeddedDotInDomain = domain.indexOf('.');
        if (embeddedDotInDomain == 0) {
            embeddedDotInDomain = domain.indexOf('.',1);
        }
        if (!isLocalDomain && (embeddedDotInDomain == -1 || embeddedDotInDomain == domain.length() - 1)) {
            return false;
        }

        int firstDotInHost = host.indexOf('.');

    }

}
