package Modelo.Entidades;

import Modelo.Nucleo.Posicion;

public class Jugador extends GameObject {
    private static Jugador instancia;

    private Jugador(Posicion pos){
        super(pos, '@');
    }

    public static Jugador getInstancia(Posicion posicion) {
        if (instancia == null) {
            instancia = new Jugador(posicion);
        } else {
            instancia.setPosicion(posicion);
        }
        return instancia;
    }
}