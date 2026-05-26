package com.itm.juego;


import com.itm.modelos.Jugador;

public abstract class Item {
    private String nombre;
    private String descripcion;
    private int precio;
    private static int totalItemsCreados = 0;

    public Item(String nombre, String descripcion, int precio) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        totalItemsCreados++;
    }

    public abstract void usar(Jugador jugador);

    public static int getTotalItemsCreados() { return totalItemsCreados; }

    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public int getPrecio() { return precio; }

    @Override
    public String toString() {
        return String.format("%-15s | %s ($%d)", nombre, descripcion, precio);
    }
}
