package com.tecdesoftware.market.persistence.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name= "clientes")

public class Cliente {
    @Id //Indica que es PK
    //Ejemplo que no lleva: @GeneretValue

    private String id;

    private String nombre;

    @Column(name = "apellidos")
    private String apellido;

    private Long celular;
    //Long significa que le vamos a pasar un dato largo

    private String direccion;

    @Column(name = "correo_electronico")
    private String correoElectronico;

    //Aquí se conecta con la entidad Compra
    @OneToMany(mappedBy = "cliente" )
    private List<Compra> compra;

    private String contrasena;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Long getCelular() {
        return celular;
    }

    public void setCelular(Long celular) {
        this.celular = celular;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}
