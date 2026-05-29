package com.itm.juego;


import com.itm.modelos.Jugador;

public class Pokeball extends Item {
    private int bonusDinero;

    public Pokeball(String nombre, int bonusDinero, int precio) {
        super(nombre, "Otorga $" + bonusDinero + " de recompensa extra", precio);
        this.bonusDinero = bonusDinero;
    }

    public Pokeball() {
        this("Pokeball", 100, 200);
    }

    @Override
    public void usar(Jugador jugador) {
        jugador.setDinero(jugador.getDinero() + bonusDinero);
        System.out.println("  Usaste " + getNombre() + "! Ganaste $" + bonusDinero + " extra.");
    }

    public int getBonusDinero() { return bonusDinero; }

    @Override
    public String toString() {
        return super.toString();
    }
}
