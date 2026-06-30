package modelo.command;

import modelo.nucleo.Matriz;

public interface Comando {
    void deshacer(Matriz matriz);
    boolean esEmpuje();
}