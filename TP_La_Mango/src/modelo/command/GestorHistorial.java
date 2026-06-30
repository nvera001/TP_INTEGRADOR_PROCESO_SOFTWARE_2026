package modelo.command;

import controlador.ContadorEstadisticas;
import modelo.nucleo.Matriz;
import java.util.Stack;

public class GestorHistorial {
    private final Stack<Comando> historial = new Stack<>();
    private int usosUndoTotal = 0;
    private int cantUndoNivel = 0;
    private final ContadorEstadisticas estadisticas;

    public GestorHistorial(ContadorEstadisticas estadisticas) {
        this.estadisticas = estadisticas;
    }

    public void guardar(Comando comando) {
        historial.push(comando);
        if (historial.size() > 15) {
            historial.remove(0);
        }
    }

    public boolean puedeDeshacer() {
        return usosUndoTotal < 3 && !historial.isEmpty();
    }

    public void deshacerUltimos5(Matriz matriz) {
        if (!puedeDeshacer()) return;

        int pasosA_Deshacer = Math.min(5, historial.size());

        for (int i = 0; i < pasosA_Deshacer; i++) {
            Comando cmd = historial.pop();
            cmd.deshacer(matriz);

            if (estadisticas != null) {
                estadisticas.decrementarMovimiento();
                if (cmd.esEmpuje()) {
                    estadisticas.decrementarEmpuje();
                }
            }
        }
        usosUndoTotal++;
        cantUndoNivel++;
    }

    public int getCantUndoNivel() { return cantUndoNivel; }
    public int getUsosUndoTotal() { return usosUndoTotal; }
}