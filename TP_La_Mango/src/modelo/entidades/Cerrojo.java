package modelo.entidades;

import modelo.nucleo.Posicion;

public class Cerrojo extends GameObject {
    public Cerrojo(Posicion posicion) {
        super(posicion, 'L');
    }
    public CapaJuego getCapa() {
        return CapaJuego.SUELO;
    }

    public boolean esCerrojo() {
        return true;
    }

}