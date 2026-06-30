package Modelo.Fabricas;

import Modelo.Decoradores.CajaFragil;
import Modelo.Decoradores.CajaLlave;
import Modelo.Entidades.CajaSimple;
import Modelo.Entidades.GameObject;
import Modelo.Nucleo.Posicion;

public class CreadorCajaFragilLlave implements CreadorGameObject {
    @Override
    public GameObject crearGameObject(Posicion posicion) {
        CajaSimple base = new CajaSimple(posicion);
        CajaLlave conLlave = new CajaLlave(base);
        return new CajaFragil(conLlave, 5, 'Z');
    }
}