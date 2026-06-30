package modelo.fabricas;

import modelo.entidades.CajaSimple;
import modelo.entidades.GameObject;
import modelo.nucleo.Posicion;

public class CreadorCajaSimple implements CreadorGameObject {
    public GameObject crearGameObject(Posicion posicion) {
        return new CajaSimple(posicion);
    }
}
