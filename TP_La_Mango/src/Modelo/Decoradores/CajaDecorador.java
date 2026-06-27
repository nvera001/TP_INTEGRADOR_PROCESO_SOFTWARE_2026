package Modelo.Decoradores;

import Modelo.Entidades.GameObject;
import Modelo.Entidades.Caja;
import Modelo.Nucleo.Posicion;
import Modelo.Nucleo.Direccion;
import Modelo.Nucleo.Matriz;

public abstract class CajaDecorador extends GameObject implements Caja {
    protected GameObject cajaEnvoltorio;

    public CajaDecorador(GameObject caja, char simbolo) {
        super(caja.getPosicion(), simbolo);
        this.cajaEnvoltorio = caja;
    }

    @Override
    public void setPosicion(Posicion posicion) {
        super.setPosicion(posicion);
        cajaEnvoltorio.setPosicion(posicion); // Mantiene sincronizada la caja de adentro
    }

    @Override
    public boolean serEmpujada(Direccion dir, Matriz matriz, GameObject entidadA_Mover) {
        if (cajaEnvoltorio instanceof Caja) {
            return ((Caja) cajaEnvoltorio).serEmpujada(dir, matriz, entidadA_Mover);
        }
        return false;
    }
}