package com.videxedge.fbaudemo;

public class User {
    private String email, phone;

    public User (){}
    public User (String email, String phone) {
        this.email=email;
        this.phone=phone;
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
}
