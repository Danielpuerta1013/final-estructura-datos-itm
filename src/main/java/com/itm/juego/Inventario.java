package com.itm.juego;

import java.util.LinkedList;

// COMPOSICION: Inventario TIENE UNA LinkedList<Item>
public class Inventario {
    private LinkedList<Item> items;
    private int capacidadMaxima;

    public Inventario(int capacidadMaxima) {
        this.items = new LinkedList<>();
        this.capacidadMaxima = capacidadMaxima;
    }

    public boolean agregar(Item item) {
        if (items.size() >= capacidadMaxima) {
            System.out.println("  Inventario lleno! Capacidad maxima: " + capacidadMaxima);
            return false;
        }
        items.add(item);
        return true;
    }

    public Item obtener(int posicion) {
        return items.get(posicion);
    }

    public Item usarItem(int posicion) {
        return items.remove(posicion);
    }

    // RECURSIVIDAD #2 — busqueda recursiva de item por nombre (contexto: inventario)
    public Item buscarPorNombre(String nombre) {
        return buscarRecursivo(0, nombre);
    }

    private Item buscarRecursivo(int index, String nombre) {
        if (index >= items.size()) return null;                                    // caso base: fin de lista
        if (items.get(index).getNombre().equalsIgnoreCase(nombre)) return items.get(index); // caso base: encontrado
        return buscarRecursivo(index + 1, nombre);                                 // caso recursivo
    }

    public boolean estaVacio() { return items.isEmpty(); }
    public int getCantidad() { return items.size(); }
    public LinkedList<Item> getItems() { return items; }

    public void mostrar() {
        if (items.isEmpty()) {
            System.out.println("  El inventario esta vacio.");
            return;
        }
        int i = 0;
        for (Item item : items) {
            System.out.println("  [" + i++ + "] " + item);
        }
    }

    @Override
    public String toString() {
        return "Inventario[" + items.size() + "/" + capacidadMaxima + "] " + items.toString();
    }
}
