package com.rickandmortygame.personajes;

import java.util.ArrayList;
import java.util.List;
import com.rickandmortygame.habilidades.Habilidad;

public abstract class Morty {
    protected String nombre;
    protected int nivel;
    protected int saludMaxima;
    protected int saludActual;
    protected List<Habilidad> habilidades;
    protected boolean enDefensa;

    public Morty(String nombre, int nivel) {
        this.nombre = nombre;
        this.nivel = nivel;
        this.saludMaxima = 100 + (nivel * 10);
        this.saludActual = saludMaxima;
        this.habilidades = new ArrayList<>();
        this.enDefensa = false;
    }

    public void atacar(Morty enemigo, Habilidad habilidad) {
        if (habilidad != null && habilidades.contains(habilidad)) {
            int daño = habilidad.getPoder() + (nivel * 2);
            if (enemigo.estaEnDefensa()) {
                daño /= 2;
            }
            enemigo.recibirDaño(daño);
        }
    }

    public void curar(int cantidad) {
        saludActual = Math.min(saludActual + cantidad, saludMaxima);
    }

    public void defender() {
        enDefensa = true;
    }

    public void recibirDaño(int daño) {
        saludActual = Math.max(0, saludActual - daño);
    }

    public boolean estaVivo() {
        return saludActual > 0;
    }

    public boolean estaEnDefensa() {
        return enDefensa;
    }

    // Getters y setters
    public String getNombre() {
        return nombre;
    }

    public int getNivel() {
        return nivel;
    }

    public int getSaludActual() {
        return saludActual;
    }

    public List<Habilidad> getHabilidades() {
        return new ArrayList<>(habilidades);
    }
}