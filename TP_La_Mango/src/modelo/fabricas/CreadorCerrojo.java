package modelo.fabricas;

import modelo.entidades.Cerrojo;
import modelo.entidades.GameObject;
import modelo.nucleo.Posicion;

public class CreadorCerrojo implements CreadorGameObject {
    @Override
    public GameObject crearGameObject(Posicion posicion) {
        return new Cerrojo(posicion);
    }
}