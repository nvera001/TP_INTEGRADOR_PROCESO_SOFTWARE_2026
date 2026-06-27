package Modelo.Entidades;

import Modelo.Nucleo.Posicion;
import Modelo.Nucleo.Direccion;
import Modelo.Nucleo.Matriz;

public class CajaSimple extends GameObject implements Caja {
    public CajaSimple(Posicion posicion) {
        super(posicion, '$'); // '$' representa la caja
    }


    public boolean serEmpujada(Direccion dir, Matriz matriz, GameObject entidadA_Mover) {
        // Calculamos la posición usando el objeto real de la grilla
        Posicion proxPosCaja = entidadA_Mover.getPosicion().sumar(dir.getDeltaX(), dir.getDeltaY());
        GameObject destinoDeCaja = matriz.obtenerObjetoEn(proxPosCaja);

        if (destinoDeCaja == null) {
            // Movemos el bloque completo con todos sus decoradores puestos
            matriz.moverObjeto(entidadA_Mover, proxPosCaja);
            return true;
        }
        return false;
    }
}