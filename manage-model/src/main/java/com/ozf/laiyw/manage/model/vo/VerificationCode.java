package com.ozf.laiyw.manage.model.vo;

import java.io.Serializable;

/**
 * @Description:
 * @Auther: Laiyw
 * @Date: 2018/12/7 11:09
 */
public class VerificationCode implements Serializable {

    public static final String SEND_TYPE_EMAIL = "email";
    public static final String SEND_TYPE_PHONE = "phone";

    private String type;
    private String prefix;
    private String account;
    private String email;
    private String phone;
    private String verificationCode;

    public static VerificationCode buildMail(String prefix, String email, String account) {
        return new VerificationCode(SEND_TYPE_EMAIL, prefix, email, null, account);
    }

    public static VerificationCode buildPhone(String prefix, String phone, String account) {
        return new VerificationCode(SEND_TYPE_PHONE, prefix, null, phone, account);
    }

    public VerificationCode() {

    }

    public VerificationCode(String type, String prefix, String email, String phone, String account) {
        this.type = type;
        this.prefix = prefix;
        this.email = email;
        this.phone = phone;
        this.account = account;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }
}
