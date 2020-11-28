package com.brulesoft.alquifor.models;

public class LoginCredenciales {


        private String username;
        private String password;

        public LoginCredenciales(String username, String password) {
            this.username = username;
            this.password = password;
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

}

