package com.videxedge.fbaudemo;

public class User {
    private String email, phone, uid;

    public User (){}
    public User (String email, String phone, String uid) {
        this.email=email;
        this.phone=phone;
        this.uid=uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email=email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone=phone;
    }
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid=uid;
    }
}
