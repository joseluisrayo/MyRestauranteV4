package com.example.joserayo.myrestaurantev3.Model;

public class UsersModel {
    private String nombreUser;
    private String apellidoUser;
    private String email;
    private String password;
    private String mImagenUrl;

    public UsersModel() {
    }

    public UsersModel(String nombreUser, String apellidoUser, String email, String password, String mImagenUrl) {
        this.nombreUser = nombreUser;
        this.apellidoUser = apellidoUser;
        this.email = email;
        this.password = password;
        this.mImagenUrl = mImagenUrl;
    }

    public String getNombreUser() {
        return nombreUser;
    }

    public void setNombreUser(String nombreUser) {
        this.nombreUser = nombreUser;
    }

    public String getApellidoUser() {
        return apellidoUser;
    }

    public void setApellidoUser(String apellidoUser) {
        this.apellidoUser = apellidoUser;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getmImagenUrl() {
        return mImagenUrl;
    }

    public void setmImagenUrl(String mImagenUrl) {
        this.mImagenUrl = mImagenUrl;
    }
}
