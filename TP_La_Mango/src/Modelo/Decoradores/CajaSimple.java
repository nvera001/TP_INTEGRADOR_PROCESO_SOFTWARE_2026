package Modelo.Decoradores;

import Modelo.Entidades.Caja;
import Modelo.Entidades.GameObject;
import Modelo.Nucleo.Posicion;
import Modelo.Nucleo.Direccion;
import Modelo.Nucleo.Matriz;

public class CajaSimple extends GameObject implements Caja {
    public CajaSimple(Posicion posicion) {
        super(posicion, '$');
    }


    public boolean serEmpujada(Direccion dir, Matriz matriz, GameObject entidadA_Mover) {
        Posicion proxPosCaja = entidadA_Mover.getPosicion().sumar(dir.getDeltaX(), dir.getDeltaY());
        GameObject destinoDeCaja = matriz.obtenerObjetoEn(proxPosCaja);

        if (destinoDeCaja == null) {
            matriz.moverObjeto(entidadA_Mover, proxPosCaja);
            return true;
        }
        return false;
    }
}