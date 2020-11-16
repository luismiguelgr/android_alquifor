package com.brulesoft.alquifor.models;

import com.google.gson.annotations.SerializedName;

import java.time.format.DateTimeFormatter;

public class Publicacion {

    @SerializedName("id")
    private Integer id;

    @SerializedName("titulo")
    private String titulo;

    @SerializedName("foto")
    private String foto;

    @SerializedName("descripcion")
    private String descripcion;

//    @SerializedName("pros")
//    private String pros;
//
//    @SerializedName("contras")
//    private String contras;

    @SerializedName("visitas")
    private Integer visitas;

//    @SerializedName("created_at")
//    private String created_at;
//
//    @SerializedName("updated_at")
//    private String updated_at;

    @SerializedName("id_usuario")
    private Integer id_usuario;


    public Publicacion(Integer id, String titulo, String foto, String descripcion, String pros, String contras, Integer visitas, String created_at, String updated_at, Integer id_usuario) {
        this.id = id;
        this.titulo = titulo;
        this.foto = foto;
        this.descripcion = descripcion;
//        this.pros = pros;
//        this.contras = contras;
        this.visitas = visitas;
//        this.created_at = created_at;
//        this.updated_at = updated_at;
        this.id_usuario = id_usuario;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

//    public String getPros() {
//        return pros;
//    }
//
//    public void setPros(String pros) {
//        this.pros = pros;
//    }
//
//    public String getContras() {
//        return contras;
//    }
//
//    public void setContras(String contras) {
//        this.contras = contras;
//    }
//
    public Integer getVisitas() {
        return visitas;
    }

    public void setVisitas(Integer visitas) {
        this.visitas = visitas;
    }

//    public String getCreated_at() {
//        return created_at;
//    }
//
//    public void setCreated_at(String created_at) {
//        this.created_at = created_at;
//    }
//
//    public String getUpdated_at() {
//        return updated_at;
//    }
//
//    public void setUpdated_at(String updated_at) {
//        this.updated_at = updated_at;
//    }
//
//    public Integer getId_usuario() {
//        return id_usuario;
//    }
//
//    public void setId_usuario(Integer id_usuario) {
//        this.id_usuario = id_usuario;
//    }
}
