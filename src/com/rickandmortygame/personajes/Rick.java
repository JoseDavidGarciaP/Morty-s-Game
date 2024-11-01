
package com.rickandmortygame.personajes;



public class Rick {
    private String nombre;
    private String universo;
    private Morty mortySeleccionado;

    public Rick(String nombre, String universo) {
        this.nombre = nombre;
        this.universo = universo;
    }

    public String getNombreCompleto() {
        return "Rick " + nombre + ", Universo " + universo;
    }

    public void seleccionarMorty(Morty morty) {
        this.mortySeleccionado = morty;
    }

    // Getters y setters
    public Morty getMortySeleccionado() {
        return mortySeleccionado;
    }
}