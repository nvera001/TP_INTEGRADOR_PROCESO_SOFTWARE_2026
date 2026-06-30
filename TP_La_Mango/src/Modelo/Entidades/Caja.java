package Modelo.Entidades;

import Modelo.Nucleo.Direccion;
import Modelo.Nucleo.Matriz;
import Modelo.Nucleo.Posicion;

public interface Caja {
    boolean serEmpujada(Direccion dir, Matriz matriz, GameObject entidadA_Mover);
    Posicion getPosicion();
    void setPosicion(Posicion posicion);
    boolean contieneLlave();
}