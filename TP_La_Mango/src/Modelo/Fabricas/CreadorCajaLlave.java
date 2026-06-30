package Modelo.Fabricas;

import Modelo.Decoradores.CajaLlave;
import Modelo.Entidades.CajaSimple;
import Modelo.Entidades.GameObject;
import Modelo.Nucleo.Posicion;

public class CreadorCajaLlave implements CreadorGameObject {
    @Override
    public GameObject crearGameObject(Posicion posicion) {
        // La fábrica se encarga de envolver la caja simple con el decorador de llave
        return new CajaLlave(new CajaSimple(posicion));
    }
}