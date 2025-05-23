package com.tecdesoftware.market.persistance.entity;

import jakarta.persistence.*;

@Entity

@Table (name="compras")

public class Compra {

    @Id//Llave primaria
    //Hace el ID autoincremental
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id_compra")
    private Integer idCompra;

    private String nombre;
    @Column(name="id_cliente")
    private Integer idCategoria;

    private String fecha;

    @Column(name="medio_pago")
    private Double medioPago;


    private Integer comentario;

    private Boolean estado;

    public Integer getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(Integer idCompra) {
        this.idCompra = idCompra;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Double getMedioPago() {
        return medioPago;
    }

    public void setMedioPago(Double medioPago) {
        this.medioPago = medioPago;
    }

    public Integer getComentario() {
        return comentario;
    }

    public void setComentario(Integer comentario) {
        this.comentario = comentario;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
}