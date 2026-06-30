package Modelo.Fabricas;

import Modelo.Decoradores.CajaFragil;
import Modelo.Entidades.CajaSimple;
import Modelo.Entidades.GameObject;
import Modelo.Nucleo.Posicion;

public class CreadorCajaFragil implements CreadorGameObject {
    @Override
    public GameObject crearGameObject(Posicion posicion) {
        return new CajaFragil(new CajaSimple(posicion), 18);
    }
}