package Modelo.Decoradores;

import Modelo.Entidades.Caja;
import Modelo.Entidades.GameObject;
import Modelo.Nucleo.Direccion;
import Modelo.Nucleo.Matriz;

public class CajaFragil extends CajaDecorador {
    private int resistencia;

    public CajaFragil(Caja caja, int resistencia) {
        super(caja, 'F');
        this.resistencia = resistencia;
    }

    public CajaFragil(Caja caja, int resistencia, char simbolo) {
        super(caja, simbolo);
        this.resistencia = resistencia;
    }


    @Override
    public boolean serEmpujada(Direccion dir, Matriz matriz, GameObject entidadA_Mover) {
        boolean seMovio = super.serEmpujada(dir, matriz, entidadA_Mover);

        if (seMovio) {
            this.resistencia--;

            if (this.resistencia <= 0) {
                matriz.eliminarObjeto(entidadA_Mover.getPosicion());
            }
        }
        return seMovio;
    }
    public void incrementarResistencia() {
        this.resistencia++;
    }
}