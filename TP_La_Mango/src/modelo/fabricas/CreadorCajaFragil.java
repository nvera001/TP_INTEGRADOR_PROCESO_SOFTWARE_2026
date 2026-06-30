package modelo.fabricas;

import modelo.decoradores.CajaFragil;
import modelo.entidades.CajaSimple;
import modelo.entidades.GameObject;
import modelo.nucleo.Posicion;

public class CreadorCajaFragil implements CreadorGameObject {
    @Override
    public GameObject crearGameObject(Posicion posicion) {
        return new CajaFragil(new CajaSimple(posicion), 18);
    }
}