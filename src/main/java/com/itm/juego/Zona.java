package com.itm.juego;

import com.itm.model.Enemigo;
import com.itm.model.EntrenadorRival;
import com.itm.model.PokemonSalvaje;
import com.itm.model.TipoElemento;

import java.util.Random;

public class Zona implements Comparable<Zona> {
    private int id;
    private String nombre;
    private String descripcion;
    private int nivelRecomendado;
    private boolean completada;
    private boolean esGimnasio;
    private int siguienteId;
    private static final Random rng = new Random();

    public Zona(int id, String nombre, String descripcion, int nivelRecomendado,
                boolean esGimnasio, int siguienteId) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.nivelRecomendado = nivelRecomendado;
        this.esGimnasio = esGimnasio;
        this.siguienteId = siguienteId;
        this.completada = false;
    }

    public Enemigo generarEnemigo() {
        if (esGimnasio) return generarLider();
        return generarSalvaje();
    }

    private Enemigo generarSalvaje() {
        String[][] pokemones = {
            {"Rattata", "NORMAL"}, {"Pidgey", "NORMAL"}, {"Geodude", "NORMAL"},
            {"Caterpie", "PLANTA"}, {"Oddish", "PLANTA"}, {"Bellsprout", "PLANTA"},
            {"Psyduck", "AGUA"},   {"Horsea", "AGUA"},   {"Krabby", "AGUA"},
            {"Growlithe","FUEGO"}, {"Vulpix", "FUEGO"},  {"Magmar", "FUEGO"},
            {"Voltorb","ELECTRICO"},{"Pikachu","ELECTRICO"}
        };
        String[] sel = pokemones[rng.nextInt(pokemones.length)];
        TipoElemento tipo = TipoElemento.valueOf(sel[1]);
        int nivel = Math.max(1, nivelRecomendado + rng.nextInt(3) - 1);
        return new PokemonSalvaje(sel[0], nivel, tipo);
    }

    private Enemigo generarLider() {
        switch (id) {
            case 4:  return new EntrenadorRival("Lider Planta", nivelRecomendado,
                         TipoElemento.PLANTA, "Las plantas te venceran!", "Insignia Hierba", true);
            case 7:  return new EntrenadorRival("Lider Roca", nivelRecomendado,
                         TipoElemento.NORMAL, "Mis rocas son inquebrantables!", "Insignia Piedra", true);
            case 9:  return new EntrenadorRival("Lider Agua", nivelRecomendado,
                         TipoElemento.AGUA, "El agua todo lo supera!", "Insignia Cascade", true);
            case 11: return new EntrenadorRival("Campeon Gary", nivelRecomendado,
                         TipoElemento.NORMAL, "Hueles a perdedor!", "Trofeo Campeon", true);
            default: return new EntrenadorRival("Entrenador Rival", nivelRecomendado,
                         TipoElemento.NORMAL, "Te dare una leccion!", "", false);
        }
    }

    @Override
    public int compareTo(Zona otra) {
        return Integer.compare(this.id, otra.id);
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public int getNivelRecomendado() { return nivelRecomendado; }
    public boolean isCompletada() { return completada; }
    public void setCompletada(boolean completada) { this.completada = completada; }
    public boolean isEsGimnasio() { return esGimnasio; }
    public int getSiguienteId() { return siguienteId; }

    @Override
    public String toString() {
        String estado = completada ? "[OK]" : (esGimnasio ? "[GIM]" : "[  ]");
        return String.format("%s Zona %2d: %-22s | Nv.%2d | %s",
                estado, id, nombre, nivelRecomendado, descripcion);
    }
}
