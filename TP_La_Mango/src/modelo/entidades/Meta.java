package modelo.entidades;

import modelo.nucleo.Posicion;

public class Meta extends GameObject{
    public Meta(Posicion posicion) {
        super(posicion, 'X');
    }
    public CapaJuego getCapa() {
        return CapaJuego.SUELO;
    }

    public boolean esMeta() {
        return true;
    }
}
