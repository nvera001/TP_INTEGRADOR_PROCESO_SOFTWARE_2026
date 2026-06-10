package Modelo.Entidades;

import Modelo.Nucleo.Posicion;

public class Meta extends GameObject{
    public Meta(Posicion posicion) {
        // Le pasamos la posición a la clase padre y definimos su carácter representativo (por ejemplo, 'X')
        super(posicion, 'X');
    }
}
