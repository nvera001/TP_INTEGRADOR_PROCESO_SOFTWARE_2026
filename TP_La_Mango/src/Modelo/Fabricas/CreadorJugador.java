package Modelo.Fabricas;

import Modelo.Entidades.GameObject;
import Modelo.Entidades.Jugador;
import Modelo.Nucleo.Posicion;

public class CreadorJugador implements CreadorGameObject{
    public GameObject crearGameObject(Posicion posicion) {
        return new Jugador(posicion);
    }
}
