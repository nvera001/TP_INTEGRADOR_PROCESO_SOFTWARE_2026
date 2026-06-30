package modelo.factory_method;

import modelo.entidades.GameObject;
import modelo.entidades.TerrenoResbaladizo;
import modelo.nucleo.Posicion;

public class CreadorTerrenoResbaladizo implements CreadorGameObject {
    @Override
    public GameObject crearGameObject(Posicion posicion) {
        return new TerrenoResbaladizo(posicion);
    }
}