package Modelo.Decoradores;

import Modelo.Entidades.GameObject;
import Modelo.Nucleo.Direccion;
import Modelo.Nucleo.Matriz;

public class CajaFragil extends CajaDecorador {
    private int resistencia;

    public CajaFragil(GameObject caja, int resistencia) {
        super(caja, 'F');
        this.resistencia = resistencia;
    }

    @Override
    public boolean serEmpujada(Direccion dir, Matriz matriz, GameObject entidadA_Mover) {
        boolean seMovio = super.serEmpujada(dir, matriz, entidadA_Mover);

        if (seMovio) {
            this.resistencia--;
            System.out.println("Caja frágil empujada. Resistencia restante: " + resistencia);

            if (this.resistencia <= 0) {
                // Borra el bloque entero de la pantalla
                matriz.eliminarObjeto(entidadA_Mover.getPosicion());
                System.out.println("¡La caja frágil se rompió!");
            }
        }
        return seMovio;
    }
    public void incrementarResistencia() {
        this.resistencia++;
    }
}