package Modelo.Fabricas;

import Modelo.Entidades.CajaSimple;
import Modelo.Entidades.GameObject;

public class CreadorCajaSimple implements CreadorGameObject {
    public GameObject crearGameObject() {
        return new CajaSimple();
    }
}
