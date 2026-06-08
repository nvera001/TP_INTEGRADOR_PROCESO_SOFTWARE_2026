package Modelo.Entidades;

import Modelo.Nucleo.Posicion;

public class CajaSimple extends GameObject {
    public CajaSimple(Posicion posicion) {
        super(posicion, '$'); // '$' representa la caja
    }
}
