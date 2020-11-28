package com.brulesoft.alquifor.models;

import com.google.gson.annotations.SerializedName;

public class RespuestaLogin {

    @SerializedName("status")
    private Integer status;

    @SerializedName("error")
    private String error;

    @SerializedName("token")
    private String token;

    public RespuestaLogin(Integer status, String error, String token) {
        this.status = status;
        this.error = error;
        this.token = token;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
