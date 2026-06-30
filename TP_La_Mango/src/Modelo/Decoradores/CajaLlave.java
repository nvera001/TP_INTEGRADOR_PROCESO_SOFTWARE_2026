package Modelo.Decoradores;

import Modelo.Entidades.Caja;
import Modelo.Entidades.GameObject;
import Modelo.Nucleo.Direccion;
import Modelo.Nucleo.Matriz;
import Modelo.Nucleo.Posicion;

public class CajaLlave extends CajaDecorador {
    public CajaLlave(Caja caja) {
        super(caja, 'K');
    }

    public CajaLlave(Caja caja, char simbolo) {
        super(caja, simbolo);
    }


    @Override
    public boolean serEmpujada(Direccion dir, Matriz matriz, GameObject entidadA_Mover) {
        boolean seMovio = super.serEmpujada(dir, matriz, entidadA_Mover);

        if (seMovio) {
            Posicion nuevaPos = entidadA_Mover.getPosicion();
        }
        return seMovio;
    }
    public boolean contieneLlave() {
        return true;
    }

}