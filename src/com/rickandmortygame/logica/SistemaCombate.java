package com.rickandmortygame.logica;

import com.rickandmortygame.personajes.Morty;
import com.rickandmortygame.habilidades.Habilidad;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.util.Random;

public class SistemaCombate {
    private Morty mortyJugador;
    private Morty mortyEnemigo;
    private boolean turnoJugador;
    private StringProperty mensajeCombate;
    private boolean combateTerminado;
    private Random random;

    public SistemaCombate(Morty mortyJugador, Morty mortyEnemigo) {
        this.mortyJugador = mortyJugador;
        this.mortyEnemigo = mortyEnemigo;
        this.turnoJugador = true; // El jugador siempre empieza
        this.mensajeCombate = new SimpleStringProperty("");
        this.combateTerminado = false;
        this.random = new Random();
    }

    public boolean ejecutarTurnoJugador(Habilidad habilidad) {
        if (combateTerminado || !turnoJugador) return false;

        // Ejecutar la habilidad seleccionada por el jugador
        switch (habilidad.getTipo()) {
            case ATAQUE:
                mortyJugador.atacar(mortyEnemigo, habilidad);
                mensajeCombate.set(String.format(
                    "%s usa %s y causa %d de daño!",
                    mortyJugador.getNombre(),
                    habilidad.getNombre(),
                    habilidad.getPoder()
                ));
                break;
            case CURACION:
                mortyJugador.curar(habilidad.getPoder());
                mensajeCombate.set(String.format(
                    "%s usa %s y recupera %d de salud!",
                    mortyJugador.getNombre(),
                    habilidad.getNombre(),
                    habilidad.getPoder()
                ));
                break;
            case DEFENSA:
                mortyJugador.defender();
                mensajeCombate.set(String.format(
                    "%s usa %s y aumenta su defensa!",
                    mortyJugador.getNombre(),
                    habilidad.getNombre()
                ));
                break;
        }

        // Verificar si el combate ha terminado
        if (!mortyEnemigo.estaVivo()) {
            terminarCombate();
            return true;
        }

        // Cambiar turno
        turnoJugador = false;
        return true;
    }

    public void ejecutarTurnoEnemigo() {
        if (combateTerminado || turnoJugador) return;

        // IA básica para el enemigo
        Habilidad habilidadElegida = elegirHabilidadEnemigo();
        
        switch (habilidadElegida.getTipo()) {
            case ATAQUE:
                mortyEnemigo.atacar(mortyJugador, habilidadElegida);
                mensajeCombate.set(String.format(
                    "Morty enemigo usa %s y causa %d de daño!",
                    habilidadElegida.getNombre(),
                    habilidadElegida.getPoder()
                ));
                break;
            case CURACION:
                if (mortyEnemigo.getSaludActual() < mortyEnemigo.getSaludMaxima() * 0.5) {
                    mortyEnemigo.curar(habilidadElegida.getPoder());
                    mensajeCombate.set(String.format(
                        "Morty enemigo usa %s y recupera %d de salud!",
                        habilidadElegida.getNombre(),
                        habilidadElegida.getPoder()
                    ));
                }
                break;
            case DEFENSA:
                mortyEnemigo.defender();
                mensajeCombate.set(String.format(
                    "Morty enemigo usa %s y aumenta su defensa!",
                    habilidadElegida.getNombre()
                ));
                break;
        }

        // Verificar si el combate ha terminado
        if (!mortyJugador.estaVivo()) {
            terminarCombate();
            return;
        }

        // Cambiar turno
        turnoJugador = true;
    }

    private Habilidad elegirHabilidadEnemigo() {
        // Lógica simple de IA para elegir habilidad
        var habilidades = mortyEnemigo.getHabilidades();
        
        // Si la salud es baja, priorizar curación
        if (mortyEnemigo.getSaludActual() < mortyEnemigo.getSaludMaxima() * 0.3) {
            for (Habilidad h : habilidades) {
                if (h.getTipo() == Habilidad.TipoHabilidad.CURACION) {
                    return h;
                }
            }
        }

        // Si el jugador tiene mucha salud, priorizar ataque
        if (mortyJugador.getSaludActual() > mortyJugador.getSaludMaxima() * 0.7) {
            for (Habilidad h : habilidades) {
                if (h.getTipo() == Habilidad.TipoHabilidad.ATAQUE) {
                    return h;
                }
            }
        }

        // En otro caso, elegir una habilidad al azar
        return habilidades.get(random.nextInt(habilidades.size()));
    }

    private void terminarCombate() {
        combateTerminado = true;
        if (!mortyJugador.estaVivo()) {
            mensajeCombate.set("¡Has perdido el combate!");
        } else {
            mensajeCombate.set("¡Has ganado el combate!");
        }
    }

    // Getters y setters necesarios
    public StringProperty mensajeCombateProperty() {
        return mensajeCombate;
    }

    public boolean isCombateTerminado() {
        return combateTerminado;
    }

    public boolean isTurnoJugador() {
        return turnoJugador;
    }

    public Morty getMortyJugador() {
        return mortyJugador;
    }

    public Morty getMortyEnemigo() {
        return mortyEnemigo;
    }
}