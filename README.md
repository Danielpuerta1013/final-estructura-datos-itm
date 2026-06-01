# Pokemon ED — Proyecto Final Estructura de Datos
**ITM — Instituto Tecnológico Metropolitano**
Asignatura: Estructura de Datos | Java JDK 17 | Fecha de entrega: 1 de junio de 2026

---

## Descripción del juego

Juego de rol por consola inspirado en Pokemon. El jugador elige un Pokemon inicial (Charmander, Squirtle o Bulbasaur) y recorre un mapa de **11 zonas** enfrentando Pokemon salvajes y Líderes de Gimnasio en **batallas por turnos**. El objetivo es derrotar a los 4 Líderes de Gimnasio y al Campeón Final (Gary). Si el HP del jugador llega a 0, el juego termina (Game Over). Derrotar al Campeón en el Alto Mando es la condición de victoria.

**Mecánicas principales:**
- Tipos con ventaja/desventaja: FUEGO > PLANTA > AGUA > FUEGO; ELECTRICO > AGUA
- Subida de nivel automática al acumular experiencia (HP, ATK y DEF aumentan)
- Gestión de inventario: pociones y pokeballs con capacidad máxima de 20 items
- Historial de acciones en pila: cada acción queda registrada y se puede "deshacer"
- Mapa con 11 zonas organizadas en árbol BST, desbloqueadas secuencialmente
- Centro Pokemon rápido entre batallas para comprar items o curarse
- Sonidos con `javax.sound.sampled`: música de batalla en bucle, efectos de ataque, victoria y captura

---

## Requisitos

- Java JDK 17 o superior
- Maven 3.6+
- Archivos de audio `.wav` en `src/main/resources/sounds/` (ver sección de sonidos)

---

## Cómo ejecutar

**Con Maven:**
```bash
mvn compile
mvn exec:java -Dexec.mainClass="com.itm.Main"
```

**Con IntelliJ IDEA:**
1. Abrir el proyecto como proyecto Maven
2. Ejecutar `Main.java` directamente

---

## Estructura del proyecto

```
final-estructura-datos-itm/
├── src/
│   ├── main/
│   │   ├── java/com/itm/
│   │   │   ├── Main.java                    <- Punto de entrada
│   │   │   ├── SoundManager.java            <- Reproductor de audio (WAV)
│   │   │   ├── estrcturas/
│   │   │   │   ├── Arbol.java               <- BST generico con recorridos recursivos
│   │   │   │   └── NodoArbol.java           <- Nodo del arbol binario <T>
│   │   │   ├── interfaces/
│   │   │   │   ├── Atacable.java            <- Interfaz: atacar(Personaje)
│   │   │   │   └── Curable.java             <- Interfaz: curar(int)
│   │   │   ├── modelos/
│   │   │   │   ├── Personaje.java           <- Clase abstracta base
│   │   │   │   ├── Jugador.java             <- Jugador (implements Atacable, Curable)
│   │   │   │   ├── Enemigo.java             <- Clase abstracta, extends Personaje
│   │   │   │   ├── PokemonSalvaje.java      <- extends Enemigo
│   │   │   │   ├── EntrenadorRival.java     <- extends Enemigo (puede ser lider)
│   │   │   │   └── TipoElemento.java        <- Enum con efectividad entre tipos
│   │   │   ├── juego/
│   │   │   │   ├── Motor.java               <- Bucle principal del juego
│   │   │   │   ├── Batalla.java             <- Logica de combate por turnos
│   │   │   │   ├── GestorTurnos.java        <- Cola FIFO para orden de turnos
│   │   │   │   ├── HistorialAcciones.java   <- Pila LIFO de acciones del jugador
│   │   │   │   ├── Inventario.java          <- Lista de items con busqueda recursiva
│   │   │   │   ├── Mapa.java                <- Mapa usando BST de zonas
│   │   │   │   ├── Zona.java                <- Nodo del mapa (zona del mundo)
│   │   │   │   ├── Item.java                <- Clase abstracta base de items
│   │   │   │   ├── Pocion.java              <- Item: restaura HP
│   │   │   │   └── Pokeball.java            <- Item: captura Pokemon salvajes
│   │   │   └── excepciones/
│   │   │       └── PokemonDebilitadoException.java
│   │   └── resources/
│   │       └── sounds/
│   │           ├── batalla.wav              <- Musica de fondo en batalla (loop)
│   │           ├── ataque.wav               <- Efecto al atacar
│   │           ├── victoria.wav             <- Efecto al ganar
│   │           └── captura.wav              <- Efecto al capturar un Pokemon
├── pom.xml
└── README.md
```

---

## Mapa del mundo

Las 11 zonas están almacenadas en un árbol BST ordenadas por ID. Se recorren secuencialmente y cada una debe completarse antes de avanzar a la siguiente.

| ID | Zona             | Nivel rec. | Tipo       |
|----|------------------|-----------|------------|
| 1  | Pueblo Paleta    | 1         | Inicio     |
| 2  | Ruta 1           | 3         | Ruta       |
| 3  | Bosque Viridian  | 4         | Ruta       |
| 4  | Gimnasio Planta  | 5         | Gimnasio   |
| 5  | Ruta 2           | 6         | Ruta       |
| 6  | Cueva Oscura     | 7         | Ruta       |
| 7  | Gimnasio Roca    | 8         | Gimnasio   |
| 8  | Lago Cerulean    | 9         | Ruta       |
| 9  | Ciudad Cerulean  | 10        | Ruta       |
| 10 | Ruta Victoria    | 11        | Ruta       |
| 11 | Alto Mando       | 12        | Jefe final |

---

## Estructuras de datos utilizadas

| Estructura | Clase | Uso en el juego |
|---|---|---|
| Árbol BST | `Arbol<T>` | Almacena y busca zonas del mapa en O(log n) |
| Cola (Queue) | `GestorTurnos` | Orden de turnos en batalla (FIFO por nivel) |
| Pila (Stack) | `HistorialAcciones` | Registro de acciones del jugador con deshacer (LIFO) |
| Lista enlazada | `Inventario` | Items del jugador con búsqueda recursiva por nombre |

---

## Conceptos de POO aplicados

| Concepto | Dónde se aplica |
|---|---|
| Herencia | `Jugador` y `Enemigo` extienden `Personaje`; `PokemonSalvaje` y `EntrenadorRival` extienden `Enemigo` |
| Abstracción | `Personaje` y `Enemigo` son clases abstractas con métodos `atacar()` y `habilidadEspecial()` |
| Polimorfismo | `GestorTurnos` trabaja con `Personaje` sin importar si es jugador o enemigo |
| Encapsulamiento | Todos los atributos son `private`, acceso solo por getters/setters con validación |
| Interfaces | `Atacable` y `Curable` implementadas por `Jugador` |
| Composición | `Jugador` tiene-un `Inventario` y un `HistorialAcciones`; `Mapa` tiene-un `Arbol<Zona>` |
| Sobrecarga | `curar()` / `curar(int)` y `recibirDanio(int)` / `recibirDanio(int, TipoElemento)` |
| Variable estática | `Personaje.totalPersonajes` cuenta todas las instancias creadas en la partida |
| Recursividad | 5 métodos en `Arbol` y búsqueda por nombre en `Inventario` |

---

## Sonidos

Los archivos de audio deben estar en formato `.wav` dentro de `src/main/resources/sounds/`. Se precargan al iniciar el juego usando `javax.sound.sampled` (sin dependencias externas).

| Archivo | Momento de reproducción |
|---|---|
| `batalla.wav` | Al iniciar cada batalla (bucle continuo) |
| `ataque.wav` | Al atacar o usar habilidad especial |
| `victoria.wav` | Al ganar una batalla |
| `captura.wav` | Al usar una Pokeball contra un Pokemon salvaje |

Si un archivo no se encuentra, el juego continúa normalmente sin errores.

---

## Integrantes

| Nombre | GitHub |
|---|---|
| Alexander Galvis | alexander.galvis |
| Daniel Puerta | Danielpuerta1013 |

---

*Instituto Tecnológico Metropolitano — Ingeniería de Sistemas — 2026*
