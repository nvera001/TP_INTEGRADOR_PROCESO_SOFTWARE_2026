package modelo.fabricas;

import modelo.entidades.GameObject;
import modelo.entidades.Jugador;
import modelo.nucleo.Posicion;

public class CreadorJugador implements CreadorGameObject{
    public GameObject crearGameObject(Posicion posicion) {
        Jugador jugador = Jugador.getInstancia();
        jugador.setPosicion(posicion);
        return jugador;
    }
}
