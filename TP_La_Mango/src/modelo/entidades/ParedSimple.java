package modelo.entidades;

import modelo.nucleo.Posicion;

public class ParedSimple extends GameObject {
    public ParedSimple (Posicion pos){
        super(pos, '#');
    }

    public CapaJuego getCapa() {
        return CapaJuego.ELEMENTO;
    }
}
