package controlador;

import modelo.command.Comando;
import modelo.nucleo.Matriz;
import java.util.Stack;

public class GestorHistorial {
    private final Stack<Comando> historial = new Stack<>();
    private int usosUndoTotal = 0;
    private int cantUndoNivel = 0;

    public void registrarComando(Comando cmd) {
        historial.push(cmd);
        if (historial.size() > 15) {
            historial.remove(0);
        }
    }

    public boolean puedeDeshacer() {
        return usosUndoTotal < 3 && !historial.isEmpty();
    }

    public void deshacerBloque(Matriz matriz, ContadorEstadisticas estadisticas) {
        if (!puedeDeshacer()) return;

        int pasosA_Deshacer = Math.min(5, historial.size());

        for (int i = 0; i < pasosA_Deshacer; i++) {
            Comando cmd = historial.pop();
            cmd.deshacer(matriz);

            estadisticas.decrementarMovimiento();
            if (cmd.esEmpuje()) {
                estadisticas.decrementarEmpuje();
            }
        }

        usosUndoTotal++;
        cantUndoNivel++;
    }

    public void reset() {
        historial.clear();
        usosUndoTotal = 0;
        cantUndoNivel = 0;
    }

    public int getCantUndoNivel() { return cantUndoNivel; }
    public int getUsosUndoTotal() { return usosUndoTotal; }
}