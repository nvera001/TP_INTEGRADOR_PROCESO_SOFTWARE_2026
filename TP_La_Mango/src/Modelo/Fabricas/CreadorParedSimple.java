package Modelo.Fabricas;

import Modelo.Entidades.GameObject;
import Modelo.Entidades.ParedSimple;

public class CreadorParedSimple implements CreadorGameObject{
    @Override
    public GameObject crearGameObject() {
        return new ParedSimple();
    }
}
