package com.itm.juego;

import com.itm.model.Personaje;
import java.util.LinkedList;
import java.util.Queue;

// COMPOSICION: GestorTurnos TIENE UNA Queue<Personaje> (FIFO)
public class GestorTurnos {
    private Queue<Personaje> turnos;

    public GestorTurnos() {
        this.turnos = new LinkedList<>();
    }

    public void iniciarTurnos(Personaje primero, Personaje segundo) {
        turnos.clear();
        // quien tenga mayor nivel ataca primero
        if (primero.getNivel() >= segundo.getNivel()) {
            turnos.offer(primero);
            turnos.offer(segundo);
        } else {
            turnos.offer(segundo);
            turnos.offer(primero);
        }
    }

    public Personaje siguienteTurno() {
        if (turnos.isEmpty()) return null;
        return turnos.poll();
    }

    public void reencolar(Personaje personaje) {
        turnos.offer(personaje);
    }

    public boolean haySiguiente() { return !turnos.isEmpty(); }
    public Queue<Personaje> getTurnos() { return turnos; }
    public void limpiar() { turnos.clear(); }

    @Override
    public String toString() {
        return "GestorTurnos" + turnos.toString();
    }
}
