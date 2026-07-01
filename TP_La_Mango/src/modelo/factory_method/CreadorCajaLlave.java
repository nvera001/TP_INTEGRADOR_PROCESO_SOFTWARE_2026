package modelo.factory_method;

import modelo.decorator.CajaLlave;
import modelo.entidades.CajaSimple;
import modelo.entidades.GameObject;
import modelo.nucleo.Posicion;

public class CreadorCajaLlave implements CreadorGameObject {
    @Override
    public GameObject crearGameObject(Posicion posicion) {
        return new CajaLlave(new CajaSimple(posicion));
    }
}