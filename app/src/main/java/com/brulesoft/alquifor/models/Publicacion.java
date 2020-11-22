package com.brulesoft.alquifor.models;

import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Array;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Publicacion {

    @SerializedName("id")
    private Integer id;

    @SerializedName("titulo")
    private String titulo;

    @SerializedName("foto")
    private String foto;

    @SerializedName("descripcion")
    private String descripcion;

    @SerializedName("pros")
    private ArrayList<String> pros;

    @SerializedName("contras")
    private ArrayList<String> contras;

    @SerializedName("visitas")
    private Integer visitas;

    @SerializedName("created_at")
    private String created_at;

    @SerializedName("updated_at")
    private String updated_at;

    @SerializedName("id_usuario")
    private Integer id_usuario;


    public Publicacion(Integer id, String titulo, String foto, String descripcion, Integer visitas, Integer id_usuario) {
        this.id = id;
        this.titulo = titulo;
        this.foto = foto;
        this.descripcion = descripcion;
        this.pros = new ArrayList<String>();
        this.contras = new ArrayList<String>();
        this.visitas = visitas;
        this.id_usuario = id_usuario;
    }

    public Publicacion(){
        this.pros = new ArrayList<String>();
        this.contras = new ArrayList<String>();
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

    public Integer getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Integer id_usuario) {
        this.id_usuario = id_usuario;
    }

    public ArrayList<String> getPros() {
        return pros;
    }

    public void setPros(ArrayList<String> pros) {
        this.pros = pros;
    }

    public ArrayList<String> getContras() {
        return contras;
    }

    public void setContras(ArrayList<String> contras) {
        this.contras = contras;
    }

    public Integer getVisitas() {
        return visitas;
    }

    public void setVisitas(Integer visitas) {
        this.visitas = visitas;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public void addPro(String pro) {
        this.pros.add(pro);
    }

    public void removePro(String pro) {
        this.pros.remove(pro);
    }

    public void addContra(String contra) {
        this.contras.add(contra);
    }

    public void removeContra(String contra) {
        this.contras.remove(contra);
    }

    @Override
    public String toString() {
        return "Publicacion{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", foto='" + foto + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", pros=" + pros +
                ", contras=" + contras +
                ", visitas=" + visitas +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                ", id_usuario=" + id_usuario +
                '}';
    }

    //
//    public Integer getId_usuario() {
//        return id_usuario;
//    }
//
//    public void setId_usuario(Integer id_usuario) {
//        this.id_usuario = id_usuario;
//    }
}
