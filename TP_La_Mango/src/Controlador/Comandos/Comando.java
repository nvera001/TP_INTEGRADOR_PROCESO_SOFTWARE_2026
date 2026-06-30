package Controlador.Comandos;

import Modelo.Nucleo.Matriz;

public interface Comando {
    void deshacer(Matriz matriz);
    boolean esEmpuje();
}