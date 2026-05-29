package com.itm.juego;

import com.itm.model.Jugador;
import java.util.LinkedList;
import com.itm.model.Enemigo;
import com.itm.model.EntrenadorRival;
import com.itm.model.TipoElemento;
import com.itm.model.Personaje;
import java.util.Scanner;

public class Motor {
    private Jugador jugador;
    private Mapa mapa;
    private Batalla batalla;
    private Scanner scanner;
    private boolean juegoActivo;

    public Motor() {
        this.mapa = new Mapa();
        this.batalla = new Batalla();
        this.scanner = new Scanner(System.in);
        this.juegoActivo = false;
    }

    public void iniciar() {
        mostrarTitulo();
        configurarJugador();
        juegoActivo = true;
        bucleJuego();
    }

    private void mostrarTitulo() {
        System.out.println("\n" + "=".repeat(55));
        System.out.println("         ____  ____  _  __ _____ __  __  ___  _   _");
        System.out.println("        |  _ \\/ __ \\| |/ /| ____|  \\/  |/ _ \\| \\ | |");
        System.out.println("        | |_) | |  | | ' / |  _| | |\\/| | | | |  \\| |");
        System.out.println("        |  __/| |__| | . \\ | |___| |  | | |_| | |\\  |");
        System.out.println("        |_|    \\____/|_|\\_\\|_____|_|  |_|\\___/|_| \\_|");
        System.out.println("\n           Estructura de Datos — Proyecto Final ITM");
        System.out.println("                  " + Jugador.getVersion());
        System.out.println("=".repeat(55));
    }

    private void configurarJugador() {
        System.out.print("\n  Cual es tu nombre, entrenador? > ");
        String nombre = scanner.nextLine().trim();
        if (nombre.isEmpty()) nombre = "Ash";

        System.out.println("\n  Elige tu Pokemon inicial:");
        System.out.println("  [1] Charmander (FUEGO) - Alto ataque, menor defensa");
        System.out.println("  [2] Squirtle   (AGUA)  - Alto HP, alta defensa");
        System.out.println("  [3] Bulbasaur  (PLANTA) - Equilibrado");
        System.out.print("  > ");

        TipoElemento tipo = TipoElemento.FUEGO;
        String starter = "Charmander";
        try {
            int op = Integer.parseInt(scanner.nextLine().trim());
            switch (op) {
                case 2: tipo = TipoElemento.AGUA;   starter = "Squirtle";  break;
                case 3: tipo = TipoElemento.PLANTA; starter = "Bulbasaur"; break;
                default: break;
            }
        } catch (NumberFormatException e) {
            System.out.println("  Opcion invalida, elegiste Charmander!");
        }

        jugador = new Jugador(nombre, tipo);

        // Dar items iniciales
        jugador.getInventario().agregar(new Pocion("Pocion", 20, 300));
        jugador.getInventario().agregar(new Pocion("Pocion", 20, 300));
        jugador.getInventario().agregar(new Pocion("Super Pocion", 50, 700));
        jugador.getInventario().agregar(new Pokeball());
        jugador.getInventario().agregar(new Pokeball());

        System.out.println("\n  " + nombre + " eligio a " + starter + "!");
        System.out.println("  Recibes: 2x Pocion, 1x Super Pocion, 2x Pokeball");
        System.out.println("\n  " + jugador);
        System.out.println("  Tu aventura comienza en Pueblo Paleta...\n");
        pausar();
    }

    private void bucleJuego() {
        while (juegoActivo && jugador.estaVivo()) {
            Zona zonaActual = mapa.getZonaActual();
            System.out.println("\n" + "=".repeat(55));
            System.out.println("  ZONA: " + zonaActual.getNombre() + " | " + zonaActual.getDescripcion());
            System.out.println("  " + jugador);
            System.out.println("  Insignias: " + jugador.getInsignias() + "/4 | Dinero: $" + jugador.getDinero());
            System.out.println("=".repeat(55));
            System.out.println("  [1] Explorar zona       [2] Ver mapa completo");
            System.out.println("  [3] Ver inventario      [4] Ver historial");
            System.out.println("  [5] Avanzar a siguiente zona");
            System.out.println("  [0] Salir del juego");
            System.out.print("  > ");

            int opcion = leerOpcion(0, 5);
            switch (opcion) {
                case 1: explorarZona(zonaActual); break;
                case 2: verMapa();                break;
                case 3: verInventario();          break;
                case 4: verHistorial();           break;
                case 5: avanzarZona();            break;
                case 0: confirmarSalida();        break;
            }

            if (!jugador.estaVivo()) {
                mostrarGameOver();
                juegoActivo = false;
            }
        }
    }

    private void explorarZona(Zona zona) {
        if (zona.isCompletada()) {
            System.out.println("\n  " + zona.getNombre() + " ya fue completada. No hay nada nuevo aqui.");
            return;
        }
        System.out.println("\n  Explorando " + zona.getNombre() + "...");
        System.out.println("  (Nivel recomendado: " + zona.getNivelRecomendado() + ")");

        Enemigo enemigo = zona.generarEnemigo();
        boolean victorioso = batalla.iniciar(jugador, enemigo, scanner);

        if (victorioso) {
            int exp = enemigo.getRecompensaExp();
            int dinero = enemigo.getRecompensaDinero();
            System.out.println("\n  Victoria! Ganaste " + exp + " EXP y $" + dinero + "!");
            jugador.ganarExperiencia(exp);
            jugador.setDinero(jugador.getDinero() + dinero);

            if (enemigo instanceof EntrenadorRival) {
                EntrenadorRival rival = (EntrenadorRival) enemigo;
                if (rival.isEsLider() && !rival.getInsignia().isEmpty()) {
                    jugador.setInsignias(jugador.getInsignias() + 1);
                    System.out.println("  *** OBTUVISTE: " + rival.getInsignia() + " ***");
                    zona.setCompletada(true);
                    if (mapa.esUltimaZona()) {
                        mostrarVictoria();
                        juegoActivo = false;
                    }
                }
            } else {
                zona.setCompletada(true);
            }

            // Tienda rapida tras batalla
            ofrecerTienda();
        } else {
            System.out.println("\n  Perdiste la batalla...");
            if (jugador.getHp() == 0) {
                System.out.println("  " + jugador.getNombre() + " ha sido debilitado!");
            } else {
                System.out.println("  Pudiste escapar a tiempo.");
            }
        }
        pausar();
    }

    private void ofrecerTienda() {
        System.out.println("\n  -- Centro Pokemon rapido --");
        System.out.println("  [1] Comprar Pocion ($300)   [2] Comprar Super Pocion ($700)");
        System.out.println("  [3] Curar completamente ($500) [4] No comprar nada");
        System.out.print("  > ");
        int op = leerOpcion(1, 4);
        switch (op) {
            case 1:
                if (jugador.getDinero() >= 300) {
                    jugador.setDinero(jugador.getDinero() - 300);
                    jugador.getInventario().agregar(new Pocion("Pocion", 20, 300));
                    System.out.println("  Compraste una Pocion!");
                } else System.out.println("  No tienes suficiente dinero.");
                break;
            case 2:
                if (jugador.getDinero() >= 700) {
                    jugador.setDinero(jugador.getDinero() - 700);
                    jugador.getInventario().agregar(new Pocion("Super Pocion", 50, 700));
                    System.out.println("  Compraste una Super Pocion!");
                } else System.out.println("  No tienes suficiente dinero.");
                break;
            case 3:
                if (jugador.getDinero() >= 500) {
                    jugador.setDinero(jugador.getDinero() - 500);
                    jugador.curar();
                } else System.out.println("  No tienes suficiente dinero.");
                break;
            default: break;
        }
    }

    private void verMapa() {
        System.out.println("\n  === MAPA DEL MUNDO (BST inorden) ===");
        System.out.println("  Altura del arbol: " + mapa.getArbol().obtenerAltura());
        LinkedList<Zona> zonas = mapa.listarTodasLasZonas();
        for (Zona zona : zonas) {
            String marcador = zona.getId() == mapa.getZonaActualId() ? " << AQUI" : "";
            System.out.println("  " + zona + marcador);
        }
    }

    private void verInventario() {
        System.out.println("\n  === INVENTARIO ===");
        jugador.getInventario().mostrar();
        System.out.println("  [U] Usar item  [B] Buscar por nombre  [Enter] Volver");
        System.out.print("  > ");
        String op = scanner.nextLine().trim().toUpperCase();
        if (op.equals("U") && !jugador.getInventario().estaVacio()) {
            System.out.print("  Numero de item a usar: ");
            int idx = leerOpcionLibre();
            if (idx >= 0 && idx < jugador.getInventario().getCantidad()) {
                Item item = jugador.getInventario().usarItem(idx);
                item.usar(jugador);
            }
        } else if (op.equals("B")) {
            System.out.print("  Nombre a buscar: ");
            String nombre = scanner.nextLine().trim();
            Item encontrado = jugador.getInventario().buscarPorNombre(nombre);
            System.out.println(encontrado != null ? "  Encontrado: " + encontrado : "  No encontrado.");
        }
    }

    private void verHistorial() {
        System.out.println("\n  === HISTORIAL DE ACCIONES ===");
        jugador.getHistorial().mostrarUltimas();
        System.out.println("  Total de acciones: " + jugador.getHistorial().getCantidad());
        System.out.println("  [D] Deshacer ultima accion  [Enter] Volver");
        System.out.print("  > ");
        String op = scanner.nextLine().trim().toUpperCase();
        if (op.equals("D") && !jugador.getHistorial().estaVacio()) {
            System.out.println("  Deshecha: " + jugador.getHistorial().deshacer());
        }
    }

    private void avanzarZona() {
        boolean avanzado = mapa.avanzar();
        if (avanzado) {
            Zona nueva = mapa.getZonaActual();
            System.out.println("\n  Avanzaste a: " + nueva.getNombre());
            System.out.println("  " + nueva.getDescripcion());
            if (nueva.isEsGimnasio()) {
                System.out.println("  *** ATENCION: Este es un Gimnasio Pokemon! ***");
            }
        }
    }

    private void confirmarSalida() {
        System.out.print("\n  Seguro que quieres salir? (s/n): ");
        String r = scanner.nextLine().trim().toLowerCase();
        if (r.equals("s")) {
            System.out.println("  Hasta pronto, " + jugador.getNombre() + "!");
            System.out.println("  " + jugador.getHistorial());
            juegoActivo = false;
        }
    }

    private void mostrarGameOver() {
        System.out.println("\n" + "=".repeat(55));
        System.out.println("              GAME OVER");
        System.out.println("=".repeat(55));
        System.out.println("  " + jugador.getNombre() + " fue debilitado.");
        System.out.println("  Insignias obtenidas: " + jugador.getInsignias() + "/4");
        System.out.println("  Dinero final: $" + jugador.getDinero());
        System.out.println("  Acciones totales: " + jugador.getHistorial().getCantidad());
        System.out.println("  Total personajes creados: " + Personaje.getTotalPersonajes());
        System.out.println("=".repeat(55));
    }

    private void mostrarVictoria() {
        System.out.println("\n" + "=".repeat(55));
        System.out.println("         *** FELICITACIONES " + jugador.getNombre().toUpperCase() + " ***");
        System.out.println("         ERES EL NUEVO CAMPEON POKEMON!");
        System.out.println("=".repeat(55));
        System.out.println("  Nivel final:    " + jugador.getNivel());
        System.out.println("  Insignias:      " + jugador.getInsignias() + "/4");
        System.out.println("  Dinero total:   $" + jugador.getDinero());
        System.out.println("  Exp acumulada:  " + jugador.getExperiencia());
        System.out.println("  Acciones total: " + jugador.getHistorial().getCantidad());
        System.out.println("  Personajes cre: " + Personaje.getTotalPersonajes());
        System.out.println("  Mapa altura:    " + mapa.getArbol().obtenerAltura());
        System.out.println("=".repeat(55));
    }

    private void pausar() {
        System.out.print("\n  Presiona Enter para continuar...");
        scanner.nextLine();
    }

    private int leerOpcion(int min, int max) {
        while (true) {
            try {
                int val = Integer.parseInt(scanner.nextLine().trim());
                if (val >= min && val <= max) return val;
                System.out.print("  Opcion invalida (" + min + "-" + max + "): ");
            } catch (NumberFormatException e) {
                System.out.print("  Ingresa un numero valido: ");
            }
        }
    }

    private int leerOpcionLibre() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    @Override
    public String toString() {
        return "Motor[jugador=" + (jugador != null ? jugador.getNombre() : "null") +
               ", zona=" + mapa.getZonaActualId() + "]";
    }
}
