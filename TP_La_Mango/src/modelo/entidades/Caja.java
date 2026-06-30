package modelo.entidades;

import modelo.nucleo.Direccion;
import modelo.nucleo.Matriz;
import modelo.nucleo.Posicion;

public interface Caja {
    boolean serEmpujada(Direccion dir, Matriz matriz, GameObject entidadA_Mover);
    Posicion getPosicion();
    void setPosicion(Posicion posicion);
    boolean contieneLlave();
}