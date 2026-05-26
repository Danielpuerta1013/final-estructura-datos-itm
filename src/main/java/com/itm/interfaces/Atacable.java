package com.itm.interfaces;

import com.itm.modelos.Personaje;

public interface Atacable {
    int atacar(Personaje objetivo);
    String habilidadEspecial();
}
