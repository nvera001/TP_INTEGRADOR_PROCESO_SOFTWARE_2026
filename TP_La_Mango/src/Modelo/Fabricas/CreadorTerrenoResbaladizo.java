package Modelo.Fabricas;

import Modelo.Entidades.GameObject;
import Modelo.Entidades.TerrenoResbaladizo;
import Modelo.Nucleo.Posicion;

public class CreadorTerrenoResbaladizo implements CreadorGameObject {
    @Override
    public GameObject crearGameObject(Posicion posicion) {
        return new TerrenoResbaladizo(posicion);
    }
}