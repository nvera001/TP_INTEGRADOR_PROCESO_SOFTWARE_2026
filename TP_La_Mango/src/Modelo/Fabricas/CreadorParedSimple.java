package Modelo.Fabricas;

import Modelo.Entidades.GameObject;
import Modelo.Entidades.ParedSimple;
import Modelo.Nucleo.Posicion;

public class CreadorParedSimple implements CreadorGameObject{
    @Override
    public GameObject crearGameObject(Posicion posicion) {
        return new ParedSimple(posicion);
    }
}
