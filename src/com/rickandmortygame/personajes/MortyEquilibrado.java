package com.rickandmortygame.personajes;

import com.rickandmortygame.habilidades.Habilidad;
import com.rickandmortygame.habilidades.Habilidad.TipoHabilidad;

public class MortyEquilibrado extends Morty {
    public MortyEquilibrado(String nombre, int nivel) {
        super(nombre, nivel);
        inicializarHabilidades();
    }

    private void inicializarHabilidades() {
        // Conjunto equilibrado de habilidades
        habilidades.add(new Habilidad("Ataque Balanceado", TipoHabilidad.ATAQUE, 25));
        habilidades.add(new Habilidad("Defensa Estable", TipoHabilidad.DEFENSA, 25));
        habilidades.add(new Habilidad("Curación Moderada", TipoHabilidad.CURACION, 25));
        habilidades.add(new Habilidad("Combo Equilibrado", TipoHabilidad.ATAQUE, 30));
    }

    @Override
    public void atacar(Morty enemigo, Habilidad habilidad) {
        // Bonus moderado en todas las acciones
        int bonusEquilibrado = 5;
        if (habilidad.getTipo() == TipoHabilidad.ATAQUE) {
            int dañoTotal = habilidad.getPoder() + bonusEquilibrado + (getNivel() * 2);
            if (enemigo.estaEnDefensa()) {
                dañoTotal /= 2;
            }
            enemigo.recibirDaño(dañoTotal);
        } else {
            super.atacar(enemigo, habilidad);
        }
    }

    @Override
    public void defender() {
        super.defender();
        // Pequeña curación al defender
        curar(10);
    }
}