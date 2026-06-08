package Modelo.Fabricas;

import Modelo.Entidades.GameObject;
import Modelo.Nucleo.Posicion;

public interface CreadorGameObject {
    GameObject crearGameObject(Posicion posicion);
}
