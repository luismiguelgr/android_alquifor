package com.brulesoft.alquifor.models;

import com.google.gson.annotations.SerializedName;

public class Usuario {

    @SerializedName("nombre")
    private String name;


    public Usuario(String name) {
        this.name = name;

    }

    public String getUser() {
        return name;
    }

    public void setUser(String name) {
        this.name = name;
    }

}
