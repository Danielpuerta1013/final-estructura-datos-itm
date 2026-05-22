package com.itm.modelos;

public abstract class Enemigo extends Personaje {

    private int recompensaExp;
    private int recompensaDinero;

    public Enemigo(String nombre, int hp, int ataque, int defensa, int nivel,
                   TipoElemento tipo, int recompensaExp, int recompensaDinero) {
        super(nombre, hp, ataque, defensa, nivel, tipo);
        this.recompensaExp = recompensaExp;
        this.recompensaDinero = recompensaDinero;
    }

    public abstract String getDescripcion();

    public int getRecompensaExp() {
        return recompensaExp;
    }

    public int getRecompensaDinero() {
        return recompensaDinero;
    }

    @Override
    public String toString() {
        return "ENEMIGO | " + super.toString();
    }
}
