package com.knight.wanandroid.library_base.entity;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/9/15 19:48
 * @descript:
 */
public class LoginEntity extends BaseEntity {


    private String loginName;
    private String loginPassword;

    public LoginEntity(String loginName, String loginPassword) {
        this.loginName = loginName;
        this.loginPassword = loginPassword;
    }

    public String getLoginName() {
        return loginName == null ? "" : loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getLoginPassword() {
        return loginPassword == null ? "" : loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }
}
