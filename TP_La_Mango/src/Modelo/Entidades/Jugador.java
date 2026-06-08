package Modelo.Entidades;

import Modelo.Nucleo.Posicion;

public class Jugador extends GameObject {
    private Jugador instancia;

    public Jugador (Posicion pos){
        super (pos,'@');
    }

    public Jugador getInstancia(Posicion posicion) {
        if  (instancia == null) {
            instancia = new Jugador (posicion);
        }
        return instancia;
    }
}
