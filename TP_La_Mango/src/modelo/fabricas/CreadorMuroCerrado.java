package modelo.fabricas;

import modelo.entidades.GameObject;
import modelo.entidades.MuroCerrado;
import modelo.nucleo.Posicion;

public class CreadorMuroCerrado implements CreadorGameObject {
    @Override
    public GameObject crearGameObject(Posicion posicion) {
        return new MuroCerrado(posicion);
    }
}