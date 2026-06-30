package Modelo.Decoradores;

import Modelo.Entidades.GameObject;
import Modelo.Nucleo.Direccion;
import Modelo.Nucleo.Matriz;
import Modelo.Nucleo.Posicion;

public class CajaLlave extends CajaDecorador {
    public CajaLlave(GameObject caja) {
        super(caja, 'K');
    }

    @Override
    public boolean serEmpujada(Direccion dir, Matriz matriz, GameObject entidadA_Mover) {
        boolean seMovio = super.serEmpujada(dir, matriz, entidadA_Mover);

        if (seMovio) {
            Posicion nuevaPos = entidadA_Mover.getPosicion();
            if (matriz.esCerrojo(nuevaPos)) {
                matriz.abrirMurosCorrespondientes();
            }
        }
        return seMovio;
    }
}