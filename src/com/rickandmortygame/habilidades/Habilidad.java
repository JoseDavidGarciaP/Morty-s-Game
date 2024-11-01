package com.rickandmortygame.habilidades;

public class Habilidad {
    private String nombre;
    private TipoHabilidad tipo;
    private int poder;

    public enum TipoHabilidad {
        ATAQUE,
        DEFENSA,
        CURACION
    }

    public Habilidad(String nombre, TipoHabilidad tipo, int poder) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.poder = poder;
    }

    public int usar() {
        return poder;
    }

    // Getters
    public String getNombre() {
        return nombre;
    }

    public TipoHabilidad getTipo() {
        return tipo;
    }

    public int getPoder() {
        return poder;
    }
}