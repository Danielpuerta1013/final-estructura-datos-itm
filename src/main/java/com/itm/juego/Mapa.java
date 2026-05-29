package com.itm.juego;


import com.itm.estrcturas.Arbol;

import java.util.LinkedList;

// COMPOSICION: Mapa TIENE UN Arbol<Zona> (BST ordenado por ID)
public class Mapa {
    private Arbol<Zona> zonas;
    private int zonaActualId;

    public Mapa() {
        this.zonas = new Arbol<>();
        this.zonaActualId = 1;
        inicializarMapa();
    }

    private void inicializarMapa() {
        // Insertamos en orden para crear BST balanceado: raiz=6, luego 3,9,1,4,7,10,2,5,8,11
        zonas.insertar(new Zona(6,  "Cueva Oscura",       "Oscura y peligrosa",          12, false, 7));
        zonas.insertar(new Zona(3,  "Bosque Viridian",    "Bosque lleno de insectos",     6, false, 4));
        zonas.insertar(new Zona(9,  "Ciudad Cerulean",    "Ciudad junto al lago",        20, false, 10));
        zonas.insertar(new Zona(1,  "Pueblo Paleta",      "Tu ciudad natal",              1, false, 2));
        zonas.insertar(new Zona(4,  "Gimnasio Planta",    "Lider experto en plantas",     8, true,  5));
        zonas.insertar(new Zona(7,  "Gimnasio Roca",      "Lider de roca inquebrantable",14, true,  8));
        zonas.insertar(new Zona(10, "Ruta Victoria",      "El camino al campeon",        25, false, 11));
        zonas.insertar(new Zona(2,  "Ruta 1",             "Primera ruta con hierbas",     3, false, 3));
        zonas.insertar(new Zona(5,  "Ruta 2",             "Ruta de tierra y piedras",    10, false, 6));
        zonas.insertar(new Zona(8,  "Lago Cerulean",      "Lagos azules de la region",   17, false, 9));
        zonas.insertar(new Zona(11, "Alto Mando",         "La batalla final te espera",  30, true, -1));
    }

    // Usa BST para buscar zona por ID — O(log n)
    public Zona buscarZona(int id) {
        Zona clave = new Zona(id, "", "", 0, false, 0);
        return zonas.buscar(clave);
    }

    public Zona getZonaActual() {
        return buscarZona(zonaActualId);
    }

    public boolean avanzar() {
        Zona actual = getZonaActual();
        if (actual == null || actual.getSiguienteId() == -1) return false;
        if (!actual.isCompletada()) {
            System.out.println("  Debes completar " + actual.getNombre() + " primero!");
            return false;
        }
        zonaActualId = actual.getSiguienteId();
        return true;
    }

    public void marcarCompletada(int id) {
        Zona zona = buscarZona(id);
        if (zona != null) zona.setCompletada(true);
    }

    // Lista todas las zonas en orden (inorden del BST)
    public LinkedList<Zona> listarTodasLasZonas() {
        LinkedList<Zona> lista = new LinkedList<>();
        zonas.inorden(lista);
        return lista;
    }

    public boolean esUltimaZona() {
        Zona actual = getZonaActual();
        return actual != null && actual.getSiguienteId() == -1;
    }

    public Arbol<Zona> getArbol() { return zonas; }
    public int getZonaActualId() { return zonaActualId; }
    public void setZonaActualId(int id) { this.zonaActualId = id; }

    @Override
    public String toString() {
        return "Mapa[altura=" + zonas.obtenerAltura() + ", zonaActual=" + zonaActualId + "]";
    }
}
