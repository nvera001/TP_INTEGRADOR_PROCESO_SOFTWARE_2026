package modelo.fabricas;

import modelo.entidades.GameObject;
import modelo.entidades.ParedSimple;
import modelo.nucleo.Posicion;

public class CreadorParedSimple implements CreadorGameObject{
    @Override
    public GameObject crearGameObject(Posicion posicion) {
        return new ParedSimple(posicion);
    }
}
