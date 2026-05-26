package com.itm.modelos;

import com.itm.interfaces.Atacable;
import com.itm.interfaces.Curable;
import com.itm.juego.HistorialAcciones;
import com.itm.juego.Inventario;

import java.util.Random;

public class Jugador extends Personaje implements Atacable, Curable {
    private int dinero;
    private int experiencia;
    private int insignias;
    private Inventario inventario;          // composicion HAS-A
    private HistorialAcciones historial;    // composicion HAS-A
    private static final String VERSION = "Pokemon-ED v1.0";
    private static final Random rng = new Random();

    public Jugador(String nombre, TipoElemento tipoStarter) {
        super(nombre, calcularHpInicial(tipoStarter), calcularAtaqueInicial(tipoStarter),
                calcularDefensaInicial(tipoStarter), 5, tipoStarter);
        this.dinero = 3000;
        this.experiencia = 0;
        this.insignias = 0;
        this.inventario = new Inventario(20);
        this.historial = new HistorialAcciones();
    }

    // metodos static utilitarios para calcular stats segun tipo
    public static int calcularHpInicial(TipoElemento tipo) {
        switch (tipo) {
            case FUEGO: return 45;
            case AGUA:  return 50;
            case PLANTA: return 48;
            default: return 45;
        }
    }

    public static int calcularAtaqueInicial(TipoElemento tipo) {
        switch (tipo) {
            case FUEGO: return 15;
            case AGUA:  return 12;
            case PLANTA: return 13;
            default: return 13;
        }
    }

    public static int calcularDefensaInicial(TipoElemento tipo) {
        switch (tipo) {
            case FUEGO: return 8;
            case AGUA:  return 12;
            case PLANTA: return 10;
            default: return 10;
        }
    }

    @Override
    public int atacar(Personaje objetivo) {
        int danioBase = getAtaque() + rng.nextInt(6);
        objetivo.recibirDanio(danioBase, getTipo());
        return danioBase;
    }

    @Override
    public String habilidadEspecial() {
        int danioEspecial = getAtaque() * 2 + rng.nextInt(10);
        String nombreHabilidad;
        switch (getTipo()) {
            case FUEGO:   nombreHabilidad = "Llamarada"; break;
            case AGUA:    nombreHabilidad = "Surf";       break;
            case PLANTA:  nombreHabilidad = "Rayo Solar"; break;
            default:      nombreHabilidad = "Golpe";      break;
        }
        return nombreHabilidad + " (" + danioEspecial + " de danio)";
    }

    // SOBRECARGA — curar con cantidad especifica
    @Override
    public void curar(int cantidad) {
        int hpAntes = getHp();
        setHp(getHp() + cantidad);
        int curado = getHp() - hpAntes;
        System.out.println("  " + getNombre() + " recupero " + curado + " HP! (" + getHp() + "/" + getHpMax() + ")");
    }

    // SOBRECARGA — curar completamente
    public void curar() {
        int hpAntes = getHp();
        setHp(getHpMax());
        System.out.println("  " + getNombre() + " recupero todos sus HP! (+" + (getHp() - hpAntes) + ")");
    }

    @Override
    public boolean estaVivo() {
        return getHp() > 0;
    }

    public void ganarExperiencia(int exp) {
        experiencia += exp;
        int nivelNuevo = 5 + experiencia / 200;
        if (nivelNuevo > getNivel()) {
            setNivel(nivelNuevo);
            int hpGanado = nivelNuevo * 3;
            setHpMax(getHpMax() + hpGanado);
            setHp(getHpMax());
            setAtaque(getAtaque() + 2);
            setDefensa(getDefensa() + 1);
            System.out.println("  *** SUBISTE AL NIVEL " + getNivel() + "! HP+" + hpGanado + " ATK+2 DEF+1 ***");
        }
    }

    public static String getVersion() { return VERSION; }

    public int getDinero() { return dinero; }
    public void setDinero(int dinero) { this.dinero = dinero; }
    public int getExperiencia() { return experiencia; }
    public int getInsignias() { return insignias; }
    public void setInsignias(int insignias) { this.insignias = insignias; }
    public Inventario getInventario() { return inventario; }
    public HistorialAcciones getHistorial() { return historial; }

    @Override
    public String toString() {
        return String.format("JUGADOR  | %s | $%-5d | Exp:%-4d | Insignias:%d",
                super.toString(), dinero, experiencia, insignias);
    }
}