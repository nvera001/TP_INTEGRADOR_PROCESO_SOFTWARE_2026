package modelo.entidades;

import modelo.nucleo.Posicion;

public class Jugador extends GameObject {
    private static Jugador instancia;

    private Jugador(Posicion pos){
        super(pos, '@');
    }

    public CapaJuego getCapa() {
        return CapaJuego.ELEMENTO;
    }

    public static Jugador getInstancia() {
        if (instancia == null) {
            instancia = new Jugador(new Posicion(0, 0));
        }
        return instancia;
    }
}