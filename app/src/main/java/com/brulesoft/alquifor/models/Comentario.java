package com.brulesoft.alquifor.models;

import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.bind.DateTypeAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Comentario {
    @SerializedName("id")
    private Integer id;

    @SerializedName("texto")
    private String texto;

    @SerializedName("fecha_creacion")
    private String fechaCreacion;

    @SerializedName("id_publicacion")
    private Integer id_publicacion;

    public Comentario(Integer id, String texto, String fechaCreacion, Integer id_publicacion) {
        this.id = id;
        this.texto = texto;
        this.fechaCreacion = fechaCreacion;
        this.id_publicacion = id_publicacion;
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

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public Integer getIdPublicacion() {
        return id_publicacion;
    }

    public void setIdPublicacion(Integer id_publicacion) {
        this.id_publicacion = id_publicacion;
    }

}
