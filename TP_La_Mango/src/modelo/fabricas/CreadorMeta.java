package modelo.fabricas;

import modelo.entidades.GameObject;
import modelo.entidades.Meta;
import modelo.nucleo.Posicion;

public class CreadorMeta implements CreadorGameObject{
    public GameObject crearGameObject(Posicion posicion) {
        return new Meta(posicion);
    }

}
