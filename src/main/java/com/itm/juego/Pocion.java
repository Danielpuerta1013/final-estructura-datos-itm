package com.itm.juego;

import com.itm.model.Jugador;

public class Pocion extends Item {
    private int cantidadCuracion;

    // SOBRECARGA — constructor completo
    public Pocion(String nombre, int cantidadCuracion, int precio) {
        super(nombre, "Restaura " + cantidadCuracion + " HP", precio);
        this.cantidadCuracion = cantidadCuracion;
    }

    // SOBRECARGA — Pocion basica con valores por defecto
    public Pocion() {
        this("Pocion", 20, 300);
    }

    @Override
    public void usar(Jugador jugador) {
        jugador.curar(cantidadCuracion);
    }

    public int getCantidadCuracion() { return cantidadCuracion; }

    @Override
    public String toString() {
        return super.toString();
    }
}
