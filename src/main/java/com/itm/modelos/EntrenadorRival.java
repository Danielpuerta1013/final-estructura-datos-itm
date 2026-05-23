package com.itm.modelos;

import java.util.Random;

public class EntrenadorRival extends Enemigo{
    private String dialogo;
    private String insignia;
    private boolean esLider;
    private static final Random rng = new Random();

    public EntrenadorRival(String nombre, int nivel, TipoElemento tipo,
                           String dialogo, String insignia, boolean esLider) {
        super(nombre,
                20 + nivel * 12,
                8  + nivel * 4,
                4  + nivel * 3,
                nivel, tipo,
                nivel * 50,
                nivel * 40);
        this.dialogo = dialogo;
        this.insignia = insignia;
        this.esLider = esLider;
    }

    @Override
    public String getDescripcion() {
        return (esLider ? "*** LIDER DE GIMNASIO *** " : "Entrenador ") + getNombre() + "!";
    }

    @Override
    public int atacar(Personaje objetivo) {
        int danioBase = getAtaque() + rng.nextInt(8) + 3;
        objetivo.recibirDanio(danioBase, getTipo());
        return danioBase;
    }

    @Override
    public String habilidadEspecial() {
        return getNombre() + " usa una tecnica de entrenador! " +
                (esLider ? "El lider ataca con toda su fuerza!" : "Ataque coordinado!");
    }

    public String getDialogo() {
        return dialogo;
    }

    public String getInsignia() {
        return insignia;
    }

    public boolean isEsLider() {
        return esLider;
    }

    @Override
    public String toString() {
        return (esLider ? "LIDER    | " : "RIVAL    | ") + super.toString().replace("ENEMIGO | ", "");
    }
}
