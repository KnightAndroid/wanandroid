package com.knight.wanandroid.library_network.cookie;

import android.os.Build;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

/**
 * Author:Knight
 * Time:2022/11/14 14:24
 * Description:InMemoryCookieStore
 */
public class InMemoryCookieStore implements CookieStore {

    private String name;
    public InMemoryCookieStore(String name) {
        this.name = name;
    }


    public  LinkedHashMap<URI, List<HttpCookie>> uriIndex = new LinkedHashMap<>();
    private ReentrantLock lock = new ReentrantLock(false);

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

    }

    @Override
    public List<HttpCookie> get(URI uri) {
        if (uri == null) {
            return Collections.emptyList();
        }

        List<HttpCookie> cookies = new ArrayList<>();
        lock.lock();
        try {
            if (uri.getHost() != null) {
                cookies.addAll(getInternal1(uri.getHost()));
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    List<HttpCookie> internal2 = getInternal2(getEffectiveURI(uri)).stream().filter(it -> !cookies.contains(it)).collect(Collectors.toList());
                    cookies.addAll(internal2);
                }
            }
        } finally {
            lock.unlock();
        }
        return cookies;
    }

    @Override
    public List<HttpCookie> getCookies() {

        List<HttpCookie> rt = new ArrayList<>();
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
        return rt;
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
           URI lintedURI  = getEffectiveURI(uri);
           if (uriIndex.get(lintedURI) == null) {
               return false;
           } else {
               List<HttpCookie> httpCookies = uriIndex.get(lintedURI);
               return httpCookies.remove(cookie);
           }
        } finally {
            lock.unlock();
        }
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

    public URI getEffectiveURI(URI uri) {
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
        if (firstDotInHost == -1 && isLocalDomain) {
            return true;
        }

        int domainLength = domain.length();
        int lengthDiff = host.length() - domainLength;
        if (lengthDiff == 0) {
            return host.equalsIgnoreCase(domain);
        } else if (lengthDiff > 0) {
            String D = host.substring(lengthDiff);
            if (Build.VERSION.SDK_INT <= 23 && !domain.startsWith(".")) {
                return false;
            } else {
                return D.equalsIgnoreCase(domain);
            }

        } else if (lengthDiff == -1) {
            return domain.charAt(0) == '.' && host.equalsIgnoreCase(domain.substring(1));
        }
        return false;
    }


    private List<HttpCookie> getInternal1(String host) {
        List<HttpCookie> toRemove = new ArrayList<>();
        List<HttpCookie> cookies = new ArrayList<>();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uriIndex.forEach(((uri, httpCookies) -> {
                for (HttpCookie httpCookie : httpCookies) {
                    String domain = httpCookie.getDomain();
                    if (httpCookie.getVersion() == 0 && netscapeDomainMatches(domain,host) || httpCookie.getVersion() == 1 && HttpCookie.domainMatches(domain,host)) {
                        if (!httpCookie.hasExpired()) {
                            if (!cookies.contains(httpCookie)) {
                                cookies.add(httpCookie);
                            }
                        } else {
                            toRemove.add(httpCookie);
                        }
                    }
                }

                for (HttpCookie mHttpCookie : toRemove) {
                    httpCookies.remove(mHttpCookie);
                }
                toRemove.clear();
            }));
        }
        return cookies;
    }


    private List<HttpCookie> getInternal2 (URI comparator) {
        List<HttpCookie> cookies = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uriIndex.forEach(((uri, httpCookies) -> {
                if (uri == comparator || comparator.compareTo(uri) == 0) {
                    List<HttpCookie> indexedCookies = uriIndex.get(uri);
                    if (indexedCookies != null) {
                          Iterator<HttpCookie> it = indexedCookies.iterator();
                          while (it.hasNext()) {
                              HttpCookie ck = it.next();
                              if (!ck.hasExpired()) {
                                   if (!cookies.contains(ck)) {
                                       cookies.add(ck);
                                   }
                              } else {
                                  it.remove();
                              }
                          }
                    }
                }

            }));
        }
        return cookies;
    }

}
