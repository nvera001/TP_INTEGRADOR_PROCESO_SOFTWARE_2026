package modelo.entidades;

import modelo.nucleo.Posicion;

public class MuroCerrado extends GameObject {
    public MuroCerrado(Posicion posicion) {
        super(posicion, 'M');
    }
    public CapaJuego getCapa() {
        return CapaJuego.ELEMENTO;
    }
    public boolean esMuroCerrado() {
        return true;
    }
}