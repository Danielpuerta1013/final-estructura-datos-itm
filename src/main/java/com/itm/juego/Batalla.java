package com.itm.juego;

import com.itm.excepciones.PokemonDebilitadoException;
import com.itm.model.Enemigo;
import com.itm.model.EntrenadorRival;
import com.itm.model.Jugador;
import com.itm.model.Personaje;

import java.util.Scanner;
import java.util.Random;

public class Batalla {
    private GestorTurnos gestorTurnos;
    private static final Random rng = new Random();

    public Batalla() {
        this.gestorTurnos = new GestorTurnos();
    }

    // Retorna true si el jugador gano, false si perdio o huyo
    public boolean iniciar(Jugador jugador, Enemigo enemigo, Scanner scanner) {
        System.out.println("\n" + "=".repeat(55));
        System.out.println("  " + enemigo.getDescripcion());
        if (enemigo instanceof EntrenadorRival) {
            System.out.println("  \"" + ((EntrenadorRival) enemigo).getDialogo() + "\"");
        }
        System.out.println("=".repeat(55));

        gestorTurnos.iniciarTurnos(jugador, enemigo);

        int turnoNum = 1;
        boolean huyo = false;

        while (jugador.estaVivo() && enemigo.estaVivo() && !huyo) {
            System.out.println("\n--- Turno " + turnoNum + " ---");
            mostrarEstado(jugador, enemigo);

            Personaje actual = gestorTurnos.siguienteTurno();
            if (actual == null) break;

            if (actual == jugador) {
                try {
                    huyo = turnoJugador(jugador, enemigo, scanner);
                } catch (PokemonDebilitadoException e) {
                    System.out.println("  ERROR: " + e.getMessage());
                    break;
                }
            } else {
                turnoEnemigo((Enemigo) actual, jugador);
            }

            if (actual.estaVivo() && !huyo) {
                gestorTurnos.reencolar(actual);
            }
            turnoNum++;
        }

        gestorTurnos.limpiar();
        return !huyo && jugador.estaVivo();
    }

    private boolean turnoJugador(Jugador jugador, Enemigo enemigo, Scanner scanner)
            throws PokemonDebilitadoException {
        if (!jugador.estaVivo()) {
            throw new PokemonDebilitadoException(jugador.getNombre());
        }

        System.out.println("\n  Que hara " + jugador.getNombre() + "?");
        System.out.println("  [1] Atacar  [2] Habilidad especial  [3] Usar item  [4] Huir");
        System.out.print("  > ");

        int opcion = leerOpcion(scanner, 1, 4);

        switch (opcion) {
            case 1:
                int danio = jugador.atacar(enemigo);
                String efectividad = Personaje.calcularEfectividad(jugador.getTipo(), enemigo.getTipo());
                System.out.println("  " + jugador.getNombre() + " ataca por " + danio + " de danio! " + efectividad);
                jugador.getHistorial().registrar("[T" + System.currentTimeMillis() % 10000 +
                        "] " + jugador.getNombre() + " ataco a " + enemigo.getNombre() + " (-" + danio + "HP)");
                break;

            case 2:
                String habilidad = jugador.habilidadEspecial();
                System.out.println("  " + jugador.getNombre() + " usa: " + habilidad);
                // extraer el numero del string de habilidad y aplicar danio
                int danioHab = jugador.getAtaque() * 2 + rng.nextInt(10);
                enemigo.recibirDanio(danioHab, jugador.getTipo());
                jugador.getHistorial().registrar("[Habilidad] " + jugador.getNombre() + " uso habilidad especial (-" + danioHab + "HP)");
                break;

            case 3:
                if (jugador.getInventario().estaVacio()) {
                    System.out.println("  No tienes items!");
                } else {
                    System.out.println("  === INVENTARIO ===");
                    jugador.getInventario().mostrar();
                    System.out.print("  Elige item (numero) o -1 para cancelar: ");
                    int idx = leerOpcionLibre(scanner);
                    if (idx >= 0 && idx < jugador.getInventario().getCantidad()) {
                        Item item = jugador.getInventario().usarItem(idx);
                        item.usar(jugador);
                        jugador.getHistorial().registrar("[Item] " + jugador.getNombre() + " uso " + item.getNombre());
                    } else {
                        System.out.println("  Opcion cancelada.");
                    }
                }
                break;

            case 4:
                if (rng.nextInt(3) == 0) {
                    System.out.println("  No pudiste huir!");
                } else {
                    System.out.println("  Huiste de la batalla!");
                    jugador.getHistorial().registrar("[Huida] " + jugador.getNombre() + " huyo de la batalla");
                    return true;
                }
                break;
        }
        return false;
    }

    private void turnoEnemigo(Enemigo enemigo, Jugador jugador) {
        // Enemigos usan habilidad especial al azar
        boolean usarHabilidad = rng.nextInt(4) == 0;
        if (usarHabilidad) {
            System.out.println("  " + enemigo.habilidadEspecial());
            int danio = enemigo.getAtaque() * 2;
            jugador.recibirDanio(danio, enemigo.getTipo());
            System.out.println("  " + jugador.getNombre() + " recibio " + danio + " de danio!");
        } else {
            int danio = enemigo.atacar(jugador);
            String efectividad = Personaje.calcularEfectividad(enemigo.getTipo(), jugador.getTipo());
            System.out.println("  " + enemigo.getNombre() + " ataca por " + danio + " de danio! " + efectividad);
        }
        jugador.getHistorial().registrar("[Enemigo] " + enemigo.getNombre() +
                " ataco a " + jugador.getNombre() + " (HP restante: " + jugador.getHp() + ")");
    }

    private void mostrarEstado(Jugador jugador, Enemigo enemigo) {
        System.out.println("  TU:     " + jugador);
        System.out.println("  RIVAL:  " + enemigo);
    }

    private int leerOpcion(Scanner scanner, int min, int max) {
        while (true) {
            try {
                String linea = scanner.nextLine().trim();
                int val = Integer.parseInt(linea);
                if (val >= min && val <= max) return val;
                System.out.print("  Opcion invalida (" + min + "-" + max + "): ");
            } catch (NumberFormatException e) {
                System.out.print("  Ingresa un numero: ");
            }
        }
    }

    private int leerOpcionLibre(Scanner scanner) {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    @Override
    public String toString() {
        return "Batalla[" + gestorTurnos + "]";
    }
}
