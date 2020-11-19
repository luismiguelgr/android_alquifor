package com.brulesoft.alquifor.models;

import com.google.gson.annotations.SerializedName;

public class Comentario {
    @SerializedName("id")
    private Integer id;

    @SerializedName("texto")
    private String texto;

    public Comentario(Integer id, String texto) {
        this.id = id;
        this.texto = texto;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
}
