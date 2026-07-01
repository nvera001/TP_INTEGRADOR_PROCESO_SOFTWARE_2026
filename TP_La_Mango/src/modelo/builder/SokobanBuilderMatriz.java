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

    @Override
    public void reset(int filas, int columnas) {
        this.matriz = new Matriz(filas, columnas);
    }

    @Override
    public void construirPared(Posicion pos) {
        matriz.colocarObjeto(traductor.instanciarDesdeCaracter('#', pos));
    }

    @Override
    public void construirJugador(Posicion pos) {
        matriz.colocarObjeto(traductor.instanciarDesdeCaracter('P', pos));
    }

    @Override
    public void construirMeta(Posicion pos) {
        matriz.colocarObjeto(traductor.instanciarDesdeCaracter('X', pos));
    }

    @Override
    public void construirTerrenoResbaladizo(Posicion pos) {
        matriz.colocarObjeto(traductor.instanciarDesdeCaracter('S', pos));
    }

    @Override
    public void construirCerrojo(Posicion pos) {
        matriz.colocarObjeto(traductor.instanciarDesdeCaracter('L', pos));
    }

    @Override
    public void construirMuroCerrado(Posicion pos) {
        matriz.colocarObjeto(traductor.instanciarDesdeCaracter('M', pos));
    }

    @Override
    public void construirMonedaFoto(Posicion pos) {
        matriz.marcarComoMonedaFoto(pos);
    }

    @Override
    public void construirCaja(char tipoCaja, Posicion pos) {
        // Envia 'C', 'F', 'K' o 'Z' al traductor para que el decorador actúe
        matriz.colocarObjeto(traductor.instanciarDesdeCaracter(tipoCaja, pos));
    }

    @Override
    public Matriz getResultado() {
        return this.matriz;
    }
}