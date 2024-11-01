package com.rickandmortygame.logica;

import com.rickandmortygame.personajes.Morty;

public class Combate {
    private Morty morty1;
    private Morty morty2;
    private boolean combateTerminado;

    public Combate(Morty morty1, Morty morty2) {
        this.morty1 = morty1;
        this.morty2 = morty2;
        this.combateTerminado = false;
    }

    public void iniciarCombate() {
        // La lógica del combate se implementará aquí
        combateTerminado = true;
    }

    public Morty determinarGanador() {
        if (!morty1.estaVivo()) return morty2;
        if (!morty2.estaVivo()) return morty1;
        return null;
    }

    public boolean estaCombateTerminado() {
        return combateTerminado;
    }
}