package com.tecdesoftware.market.persistence.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity

@Table(name="categorias")

public class Categoria {

/*hola*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria")
    private Integer idCategoria;

    private String descripcion;

    private Boolean estado;

    public Integer getIdCategoria() {
        return idCategoria;
    }

    //Aqui se conecta con la entidad compra
    @OneToMany(mappedBy = "categoria")
    private List<Producto> productos;

    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
}