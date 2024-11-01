package com.rickandmortygame.personajes;

import com.rickandmortygame.habilidades.Habilidad;
import com.rickandmortygame.habilidades.Habilidad.TipoHabilidad;

public class MortyDefensivo extends Morty {
    private int escudoExtra;

    public MortyDefensivo(String nombre, int nivel) {
        super(nombre, nivel);
        this.escudoExtra = 20;
        inicializarHabilidades();
    }

    private void inicializarHabilidades() {
        // Habilidades defensivas fuertes
        habilidades.add(new Habilidad("Escudo Portal", TipoHabilidad.DEFENSA, 40));
        habilidades.add(new Habilidad("Barrera Dimensional", TipoHabilidad.DEFENSA, 35));
        // Ataque básico
        habilidades.add(new Habilidad("Golpe Defensivo", TipoHabilidad.ATAQUE, 20));
        // Curación moderada
        habilidades.add(new Habilidad("Recuperación Táctica", TipoHabilidad.CURACION, 25));
    }

    @Override
    public void defender() {
        super.defender();
        // Bonus de defensa adicional
        this.escudoExtra += 5;
    }

    @Override
    public void recibirDaño(int daño) {
        if (estaEnDefensa()) {
            daño = Math.max(0, daño - escudoExtra);
        }
        super.recibirDaño(daño);
    }
}