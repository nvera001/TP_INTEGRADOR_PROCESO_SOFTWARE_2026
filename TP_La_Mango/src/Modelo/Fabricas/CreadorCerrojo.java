package Modelo.Fabricas;

import Modelo.Entidades.Cerrojo;
import Modelo.Entidades.GameObject;
import Modelo.Nucleo.Posicion;

public class CreadorCerrojo implements CreadorGameObject {
    @Override
    public GameObject crearGameObject(Posicion posicion) {
        return new Cerrojo(posicion);
    }
}