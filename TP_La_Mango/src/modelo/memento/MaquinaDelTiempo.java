package modelo.memento;

import modelo.nucleo.Matriz;


public class MaquinaDelTiempo {
    private final Matriz matriz;
    private Memento fotoCajas;

    public MaquinaDelTiempo(Matriz matriz) {
        this.matriz = matriz;
        this.fotoCajas = null;
    }

    public void registrarFoto() {
        this.fotoCajas = new Memento(matriz.getCapaElementos());
    }

    public void restaurarCajas() {
        if (fotoCajas != null) {
            matriz.restaurarCajas(fotoCajas);
        } else {
            System.out.println("No se ha tomado ninguna foto en este nivel todavía.");
        }
    }
}