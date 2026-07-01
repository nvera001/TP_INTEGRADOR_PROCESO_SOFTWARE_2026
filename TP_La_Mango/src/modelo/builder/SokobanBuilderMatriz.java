package modelo.builder;

import modelo.archivos.TraductorTXT;
import modelo.nucleo.Matriz;
import modelo.nucleo.Posicion;

public class SokobanBuilderMatriz implements MatrizBuilder {
    private Matriz matriz;
    private final TraductorTXT traductor;

    public SokobanBuilderMatriz() {
        this.traductor = new TraductorTXT();
    }

     public void reset(int filas, int columnas) {
        this.matriz = new Matriz(filas, columnas);
    }

     public void construirPared(Posicion pos) {
        matriz.colocarObjeto(traductor.instanciarDesdeCaracter('#', pos));
    }

     public void construirJugador(Posicion pos) {
        matriz.colocarObjeto(traductor.instanciarDesdeCaracter('P', pos));
    }

     public void construirMeta(Posicion pos) {
        matriz.colocarObjeto(traductor.instanciarDesdeCaracter('X', pos));
    }

     public void construirTerrenoResbaladizo(Posicion pos) {
        matriz.colocarObjeto(traductor.instanciarDesdeCaracter('S', pos));
    }

     public void construirCerrojo(Posicion pos) {
        matriz.colocarObjeto(traductor.instanciarDesdeCaracter('L', pos));
    }

     public void construirMuroCerrado(Posicion pos) {
        matriz.colocarObjeto(traductor.instanciarDesdeCaracter('M', pos));
    }

     public void construirMonedaFoto(Posicion pos) {
        matriz.marcarComoMonedaFoto(pos);
    }

     public void construirCaja(char tipoCaja, Posicion pos) {
        matriz.colocarObjeto(traductor.instanciarDesdeCaracter(tipoCaja, pos));
    }

     public Matriz getResultado() {
        return this.matriz;
    }
}