package com.itm.juego;

import java.util.Stack;

// COMPOSICION: HistorialAcciones TIENE UN Stack<String> (LIFO)
public class HistorialAcciones {
    private Stack<String> acciones;
    private int maxMostrar;

    public HistorialAcciones() {
        this.acciones = new Stack<>();
        this.maxMostrar = 8;
    }

    public void registrar(String accion) {
        acciones.push(accion);
    }

    public String deshacer() {
        if (acciones.isEmpty()) return "(sin acciones)";
        return acciones.pop();
    }

    public String verUltima() {
        if (acciones.isEmpty()) return "(sin acciones)";
        return acciones.peek();
    }

    public void mostrarUltimas() {
        if (acciones.isEmpty()) {
            System.out.println("  No hay acciones registradas.");
            return;
        }
        System.out.println("  === Ultimas acciones (" + acciones.size() + " total) ===");
        int count = 0;
        for (int i = acciones.size() - 1; i >= 0 && count < maxMostrar; i--, count++) {
            System.out.println("  " + (count + 1) + ". " + acciones.get(i));
        }
    }

    public boolean estaVacio() { return acciones.isEmpty(); }
    public int getCantidad() { return acciones.size(); }
    public Stack<String> getAcciones() { return acciones; }

    @Override
    public String toString() {
        return "Historial[" + acciones.size() + " acciones]";
    }
}
