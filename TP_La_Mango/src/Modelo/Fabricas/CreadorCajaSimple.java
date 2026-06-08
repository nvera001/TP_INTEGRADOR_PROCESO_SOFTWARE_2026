package Modelo.Fabricas;

import Modelo.Entidades.CajaSimple;
import Modelo.Entidades.GameObject;
import Modelo.Nucleo.Posicion;

public class CreadorCajaSimple implements CreadorGameObject {
    public GameObject crearGameObject(Posicion posicion) {
        return new CajaSimple(posicion);
    }
}
