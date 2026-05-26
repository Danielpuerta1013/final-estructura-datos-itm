# Pokemon ED — Proyecto Final Estructura de Datos
**ITM — Instituto Tecnológico Metropolitano**
Asignatura: Estructura de Datos | Java JDK 11+ | Fecha de entrega: 1 de junio de 2026

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

---

## Estructura del proyecto

```
final-estrctura/
  src/
    java/com/itm/
      estructuras/
        NodoArbol.java         <- Nodo de arbol binario <T>
        Arbol.java             <- BST con insercion/busqueda/recorridos recursivos
      model/
        TipoElemento.java      <- Enum: FUEGO, AGUA, PLANTA, ELECTRICO, NORMAL
        Personaje.java         <- Clase abstracta base (2 metodos abstract)
        Enemigo.java           <- Clase abstracta, extends Personaje
        PokemonSalvaje.java    <- extends Enemigo (tipo enemigo 1)
        EntrenadorRival.java   <- extends Enemigo (tipo enemigo 2 / lider)
    Main.java                  <- Punto de entrada
  README.md
```

---
