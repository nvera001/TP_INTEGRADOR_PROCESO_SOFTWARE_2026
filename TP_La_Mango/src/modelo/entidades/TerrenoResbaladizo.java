package modelo.entidades;

import modelo.nucleo.Posicion;

public class TerrenoResbaladizo extends GameObject {
    public TerrenoResbaladizo(Posicion posicion) {
        super(posicion, 'S');
    }
    public CapaJuego getCapa() {
        return CapaJuego.SUELO;
    }

    public boolean esResbaladizo() {
        return true;
    }
}