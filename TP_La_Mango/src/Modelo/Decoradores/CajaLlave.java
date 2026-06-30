package Modelo.Decoradores;

import Modelo.Entidades.Caja;

public class CajaLlave extends CajaDecorador {
    public CajaLlave(Caja caja) {
        super(caja, 'K');
    }

    public CajaLlave(Caja caja, char simbolo) {
        super(caja, simbolo);
    }

    public boolean contieneLlave() {
        return true;
    }
}