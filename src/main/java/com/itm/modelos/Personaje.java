package com.itm.modelos;

public abstract class Personaje {
    private String nombre;
    private int hp;
    private int hpMax;
    private int ataque;
    private int defensa;
    private int nivel;
    private TipoElemento tipo;
    private static int totalPersonajes = 0; //contador de instancias

    public Personaje(String nombre, int hp, int ataque, int defensa, int nivel, TipoElemento tipo) {
        this.nombre = nombre;
        this.hp = hp;
        this.hpMax = hp;
        this.ataque = ataque;
        this.defensa = defensa;
        this.nivel = nivel;
        this.tipo = tipo;
        totalPersonajes++;
    }

    //toda sublaclse debera heredar estos metodos
    public abstract int atacar(Personaje objetivo);
    public abstract String habilidadEspecial();

    //validamos el daño hacemos que el minimo sea 1
    //sobrecarga
    public void recibirDanio(int danio) {
        int danioReal = Math.max(1, danio - defensa / 2);
        setHp(hp - danioReal);
    }
    public void recibirDanio(int danio, TipoElemento tipoAtaque) {
        double efectividad = tipoAtaque.efectividadContra(this.tipo);
        int danioConEfectividad = (int)(danio * efectividad);
        recibirDanio(danioConEfectividad);
    }


    public static String calcularEfectividad(TipoElemento atacante, TipoElemento defensor) {
        double e = atacante.efectividadContra(defensor);
        if (e > 1.0) return "Es muy efectivo!";
        if (e < 1.0) return "No es muy efectivo...";
        return "";
    }

    public static int getTotalPersonajes() { return totalPersonajes; }
    public static void resetContador() { totalPersonajes = 0; }

    // getters y setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getHp() { return hp; }
    public void setHp(int hp) { this.hp = Math.max(0, Math.min(hp, hpMax)); }

    public int getHpMax() { return hpMax; }
    public void setHpMax(int hpMax) { this.hpMax = hpMax; }

    public int getAtaque() { return ataque; }
    public void setAtaque(int ataque) { this.ataque = ataque; }

    public int getDefensa() { return defensa; }
    public void setDefensa(int defensa) { this.defensa = defensa; }

    public int getNivel() { return nivel; }
    public void setNivel(int nivel) { this.nivel = nivel; }

    public TipoElemento getTipo() { return tipo; }
    public void setTipo(TipoElemento tipo) { this.tipo = tipo; }

    public boolean estaVivo() { return hp > 0; }

    public String getBarraHp() {
        int llenos = (int)((double) hp / hpMax * 10);
        StringBuilder barra = new StringBuilder("[");
        for (int i = 0; i < 10; i++) barra.append(i < llenos ? "=" : " ");
        barra.append("]");
        return barra.toString();
    }

    @Override
    public String toString() {
        return String.format("%-15s %s HP:%3d/%-3d Nv:%-2d %s",
                nombre, tipo.getEmoji(), hp, hpMax, nivel, getBarraHp());
    }
}
