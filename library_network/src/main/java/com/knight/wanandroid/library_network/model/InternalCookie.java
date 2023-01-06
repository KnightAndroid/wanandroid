package com.knight.wanandroid.library_network.model;

import android.os.Build;

import java.io.Serializable;
import java.net.HttpCookie;

/**
 * Author:Knight
 * Time:2023/1/5 14:34
 * Description:InternalCookie
 */
public class InternalCookie implements Serializable {

    private String comment;
    private String commentURL;
    private boolean discard;
    private String domain;
    private long maxAge;
    private String name;
    private String path;
    private String portlist;
    private boolean secure;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCommentURL() {
        return commentURL;
    }

    public void setCommentURL(String commentURL) {
        this.commentURL = commentURL;
    }

    public boolean isDiscard() {
        return discard;
    }

    public void setDiscard(boolean discard) {
        this.discard = discard;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public long getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(long maxAge) {
        this.maxAge = maxAge;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPortlist() {
        return portlist;
    }

    public void setPortlist(String portlist) {
        this.portlist = portlist;
    }

    public boolean isSecure() {
        return secure;
    }

    public void setSecure(boolean secure) {
        this.secure = secure;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public boolean isHttpOnly() {
        return httpOnly;
    }

    public void setHttpOnly(boolean httpOnly) {
        this.httpOnly = httpOnly;
    }

    private String value;
    private int version;
    private boolean httpOnly;

    public InternalCookie(HttpCookie cookie) {
        comment = cookie.getComment();
        commentURL = cookie.getCommentURL();
        discard = cookie.getDiscard();
        domain = cookie.getDomain();
        maxAge = cookie.getMaxAge();
        name = cookie.getName();
        path = cookie.getPath();
        portlist = cookie.getPortlist();
        secure = cookie.getSecure();
        value = cookie.getValue();
        version = cookie.getVersion();
    }

    public HttpCookie toHttpCookie(String name,String value) {
        HttpCookie httpCookie = new HttpCookie(name, value);
        httpCookie.setComment(comment);
        httpCookie.setCommentURL(commentURL);
        httpCookie.setDiscard(discard);
        httpCookie.setDomain(domain);
        httpCookie.setMaxAge(maxAge);
        httpCookie.setPath(path);
        httpCookie.setPortlist(portlist);
        httpCookie.setSecure(secure);
        httpCookie.setVersion(version);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            httpCookie.setHttpOnly(httpOnly);
        }
        return httpCookie;

    }
}
