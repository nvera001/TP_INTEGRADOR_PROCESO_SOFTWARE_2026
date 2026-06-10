package Modelo.Fabricas;

import Modelo.Entidades.GameObject;
import Modelo.Entidades.Meta;
import Modelo.Nucleo.Posicion;

public class CreadorMeta implements CreadorGameObject{
    public GameObject crearGameObject(Posicion posicion) {
        return new Meta(posicion);
    }

}
