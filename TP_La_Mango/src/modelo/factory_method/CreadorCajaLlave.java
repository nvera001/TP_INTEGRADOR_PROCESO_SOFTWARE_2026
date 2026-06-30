package modelo.factory_method;

import modelo.decorator.CajaLlave;
import modelo.entidades.CajaSimple;
import modelo.entidades.GameObject;
import modelo.nucleo.Posicion;

public class CreadorCajaLlave implements CreadorGameObject {
    @Override
    public GameObject crearGameObject(Posicion posicion) {
        // La fábrica se encarga de envolver la caja simple con el decorador de llave
        return new CajaLlave(new CajaSimple(posicion));
    }
}