package com.rickandmortygame.personajes;

import com.rickandmortygame.habilidades.Habilidad;
import com.rickandmortygame.habilidades.Habilidad.TipoHabilidad;

public class MortyCurador extends Morty {
    private int bonusCuracion;

    public MortyCurador(String nombre, int nivel) {
        super(nombre, nivel);
        this.bonusCuracion = 15;
        inicializarHabilidades();
    }

    private void inicializarHabilidades() {
        // Habilidades de curación potentes
        habilidades.add(new Habilidad("Mega Curación", TipoHabilidad.CURACION, 45));
        habilidades.add(new Habilidad("Regeneración Portal", TipoHabilidad.CURACION, 35));
        // Ataque débil
        habilidades.add(new Habilidad("Golpe Médico", TipoHabilidad.ATAQUE, 15));
        // Defensa moderada
        habilidades.add(new Habilidad("Escudo Curativo", TipoHabilidad.DEFENSA, 25));
    }

    @Override
    public void curar(int cantidad) {
        // Bonus de curación
        int curacionTotal = cantidad + bonusCuracion + (getNivel() * 2);
        super.curar(curacionTotal);
    }

    // Método especial para curar a otro Morty
    public void curarAliado(Morty aliado, int cantidad) {
        int curacionTotal = cantidad + (bonusCuracion / 2);
        aliado.curar(curacionTotal);
    }
}