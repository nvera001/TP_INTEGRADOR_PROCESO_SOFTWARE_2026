package controlador.comandos;

import modelo.nucleo.Matriz;

public interface Comando {
    void deshacer(Matriz matriz);
    boolean esEmpuje();
}