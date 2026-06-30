package Controlador.Comandos;

import Modelo.Nucleo.Matriz;

public interface Comando {
    void deshacer(Matriz matriz);
    boolean esEmpuje(); // Nos va a servir para restar las estadísticas del HUD al volver atrás
}