package modelo.memento;

import modelo.entidades.GameObject;
import modelo.entidades.Caja;

public class Memento {
    private final GameObject[][] estadoCajas;

    public Memento(GameObject[][] elementos) {
        int f = elementos.length;
        int c = elementos[0].length;
        this.estadoCajas = new GameObject[f][c];

        for (int y = 0; y < f; y++) {
            for (int x = 0; x < c; x++) {
                if (elementos[y][x] instanceof Caja) {
                    this.estadoCajas[y][x] = elementos[y][x];
                }
            }
        }
    }

    public GameObject[][] getEstadoCajas() {
        return this.estadoCajas;
    }
}