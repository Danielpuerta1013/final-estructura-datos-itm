package com.itm.modelos;

import java.util.Random;

public class PokemonSalvaje extends Enemigo{

    private String especie;
    private static final Random rng = new Random();

    public PokemonSalvaje(String especie, int nivel, TipoElemento tipo) {
        super(especie,
                10 + nivel * 8,
                5  + nivel * 3,
                2  + nivel * 2,
                nivel, tipo,
                nivel * 20,
                nivel * 15);
        this.especie = especie;
    }

    @Override
    public String getDescripcion() {
        return "Un " + especie + " salvaje de nivel " + getNivel() + " aparece!";
    }

    @Override
    public int atacar(Personaje objetivo) {
        int danioBase = getAtaque() + rng.nextInt(5);
        objetivo.recibirDanio(danioBase, getTipo());
        return danioBase;
    }

    @Override
    public String habilidadEspecial() {
        int danioExtra = getAtaque() * 2;
        return especie + " usa una habilidad salvaje! (+" + danioExtra + " ATK potencial)";
    }

    public String getEspecie() {
        return especie;
    }

    @Override
    public String toString() {
        return "SALVAJE  | " + super.toString().replace("ENEMIGO | ", "");
    }
}
