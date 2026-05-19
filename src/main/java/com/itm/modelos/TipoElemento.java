package com.itm.modelos;

public enum TipoElemento {
    FUEGO, AGUA, PLANTA, ELECTRICO, NORMAL;

    public double efectividadContra(TipoElemento defensor) {
        if (this == FUEGO  && defensor == PLANTA)    return 2.0;
        if (this == AGUA   && defensor == FUEGO)     return 2.0;
        if (this == PLANTA && defensor == AGUA)      return 2.0;
        if (this == FUEGO  && defensor == AGUA)      return 0.5;
        if (this == AGUA   && defensor == PLANTA)    return 0.5;
        if (this == PLANTA && defensor == FUEGO)     return 0.5;
        if (this == ELECTRICO && defensor == AGUA)   return 2.0;
        return 1.0;
    }

    public String getEmoji() {
        switch (this) {
            case FUEGO:     return "[FUEGO]";
            case AGUA:      return "[AGUA]";
            case PLANTA:    return "[PLANTA]";
            case ELECTRICO: return "[ELEC]";
            default:        return "[NORMAL]";
        }
    }
}
