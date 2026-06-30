package Modelo.Entidades;

import Modelo.Nucleo.Posicion;

public class Jugador extends GameObject {
    private static Jugador instancia;

    private Jugador(Posicion pos){
        super(pos, '@');
    }

    /**
     * Punto de acceso global PURO.
     * Permite obtener al jugador desde cualquier parte del código sin alterar su estado.
     */
    public static Jugador getInstancia() {
        if (instancia == null) {
            instancia = new Jugador(new Posicion(0, 0));
        }
        return instancia;
    }
}