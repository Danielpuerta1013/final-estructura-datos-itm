package com.itm.excepciones;


public class PokemonDebilitadoException extends Exception {
    private String nombrePokemon;

    public PokemonDebilitadoException(String nombrePokemon) {
        super(nombrePokemon + " esta debilitado y no puede actuar!");
        this.nombrePokemon = nombrePokemon;
    }

    public String getNombrePokemon() { return nombrePokemon; }
}
