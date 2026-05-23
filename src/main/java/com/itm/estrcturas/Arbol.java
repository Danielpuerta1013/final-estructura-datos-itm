package com.itm.estrcturas;

import java.util.LinkedList;

public class Arbol <T extends Comparable<T>>{
    private NodoArbol<T> raiz;

    public Arbol() {
        this.raiz = null;
    }

    // Insercion recursiva
    public void insertar(T dato) {
        raiz = insertarRecursivo(raiz, dato);
    }

    // Baja por izquierda si el dato es menor, por derecha si es mayor
    private NodoArbol<T> insertarRecursivo(NodoArbol<T> nodo, T dato) {
        if (nodo == null) return new NodoArbol<>(dato);
        int comparacion = dato.compareTo(nodo.getDato());
        if (comparacion < 0) nodo.setIzquierdo(insertarRecursivo(nodo.getIzquierdo(), dato));
        else if (comparacion > 0) nodo.setDerecho(insertarRecursivo(nodo.getDerecho(), dato));
        return nodo;
    }

    // Busca un elemento en el BST
    public T buscar(T dato) {
        return buscarRecursivo(raiz, dato);
    }

    // Compara y baja izquierda o derecha.
    private T buscarRecursivo(NodoArbol<T> nodo, T dato) {
        if (nodo == null) return null;
        int cmp = dato.compareTo(nodo.getDato());
        if (cmp == 0) return nodo.getDato();
        if (cmp < 0) return buscarRecursivo(nodo.getIzquierdo(), dato);
        return buscarRecursivo(nodo.getDerecho(), dato);
    }

    // Llena la lista con todos los elementos ordenados de menor a mayor
    public void inorden(LinkedList<T> resultado) {
        inordenRecursivo(raiz, resultado);
    }

    // Recorre izquierda, agrega el nodo, recorre derecha
    private void inordenRecursivo(NodoArbol<T> nodo, LinkedList<T> resultado) {
        if (nodo == null) return;
        inordenRecursivo(nodo.getIzquierdo(), resultado);
        resultado.add(nodo.getDato());
        inordenRecursivo(nodo.getDerecho(), resultado);
    }

    // Llena la lista con los elementos en orden
    public void preorden(LinkedList<T> resultado) {
        preordenRecursivo(raiz, resultado);
    }

    // Agrega el nodo primero, luego recorre izquierda y derecha
    private void preordenRecursivo(NodoArbol<T> nodo, LinkedList<T> resultado) {
        if (nodo == null) return;
        resultado.add(nodo.getDato());
        preordenRecursivo(nodo.getIzquierdo(), resultado);
        preordenRecursivo(nodo.getDerecho(), resultado);
    }

    // Retorna la cantidad de niveles del arbol
    public int obtenerAltura() {
        return alturaRecursiva(raiz);
    }

    // Calcula el maximo entre la altura del subarbol izquierdo y derecho
    private int alturaRecursiva(NodoArbol<T> nodo) {
        if (nodo == null) return 0;
        int altIzq = alturaRecursiva(nodo.getIzquierdo());
        int altDer = alturaRecursiva(nodo.getDerecho());
        return 1 + Math.max(altIzq, altDer);
    }

    public NodoArbol<T> getRaiz() {
        return raiz;
    }

    public boolean estaVacio() {
        return raiz == null;
    }

    @Override
    public String toString() {
        LinkedList<T> elementos = new LinkedList<>();
        inorden(elementos);
        return "Arbol(inorden)" + elementos.toString();
    }
}
