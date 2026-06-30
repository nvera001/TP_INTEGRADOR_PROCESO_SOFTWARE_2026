package modelo.fabricas;

import modelo.entidades.GameObject;
import modelo.nucleo.Posicion;

public interface CreadorGameObject {
    GameObject crearGameObject(Posicion posicion);
}
