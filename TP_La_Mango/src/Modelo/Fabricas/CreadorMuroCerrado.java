package Modelo.Fabricas;

import Modelo.Entidades.GameObject;
import Modelo.Entidades.MuroCerrado;
import Modelo.Nucleo.Posicion;

public class CreadorMuroCerrado implements CreadorGameObject {
    @Override
    public GameObject crearGameObject(Posicion posicion) {
        return new MuroCerrado(posicion);
    }
}