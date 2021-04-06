package com.example.hospitales.models;

import java.io.Serializable;

public class Sedes implements Serializable {

    public int id;
    public String nombre;
    public String cp;
    public String colonia;
    public String calle;
    public String loteymanzana;
    public String numero;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getLoteymanzana() {
        return loteymanzana;
    }

    public void setLoteymanzana(String loteymanzana) {
        this.loteymanzana = loteymanzana;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
}
