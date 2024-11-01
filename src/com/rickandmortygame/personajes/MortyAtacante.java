package com.rickandmortygame.personajes;

import com.rickandmortygame.habilidades.Habilidad;
import com.rickandmortygame.habilidades.Habilidad.TipoHabilidad;

public class MortyAtacante extends Morty {
    public MortyAtacante(String nombre, int nivel) {
        super(nombre, nivel);
        inicializarHabilidades();
    }

    private void inicializarHabilidades() {
        // Habilidades ofensivas poderosas
        habilidades.add(new Habilidad("Mega Puñetazo", TipoHabilidad.ATAQUE, 30));
        habilidades.add(new Habilidad("Patada Portal", TipoHabilidad.ATAQUE, 25));
        habilidades.add(new Habilidad("Grito Dimensional", TipoHabilidad.ATAQUE, 35));
        // Habilidad de curación básica
        habilidades.add(new Habilidad("Venda Improvisada", TipoHabilidad.CURACION, 15));
    }

    @Override
    public void atacar(Morty enemigo, Habilidad habilidad) {
        // Bonus de daño para el Morty atacante
        if (habilidad.getTipo() == TipoHabilidad.ATAQUE) {
            int dañoExtra = (int)(habilidad.getPoder() * 0.2); // 20% extra de daño
            int dañoTotal = habilidad.getPoder() + dañoExtra + (getNivel() * 2);
            if (enemigo.estaEnDefensa()) {
                dañoTotal /= 2;
            }
            enemigo.recibirDaño(dañoTotal);
        } else {
            super.atacar(enemigo, habilidad);
        }
    }
}