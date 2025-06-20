package com.example.indie_sell_buy;

public class storinguserdataClass {

    String email,username,password,mobileno;

    public storinguserdataClass() {

    }
    public storinguserdataClass(String email, String username, String password, String mobileno) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.mobileno = mobileno;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }
}
