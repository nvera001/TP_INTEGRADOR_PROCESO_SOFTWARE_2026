package controlador;

public class ContadorEstadisticas {
    private int movimientos = 0;
    private int empujes = 0;
    private int segundosTranscurridos = 0;

    public void registrarMovimiento() {
        movimientos++;
    }
    public void registrarEmpuje() {
        empujes++;
    }
    public void incrementarSegundo() {
        segundosTranscurridos++;
    }

    public void decrementarMovimiento() {
        if (movimientos > 0) movimientos--;
    }

    public void decrementarEmpuje() {
        if (empujes > 0) empujes--;
    }

    public void reset() {
        movimientos = 0;
        empujes = 0;
        segundosTranscurridos = 0;
    }

    public int calcularPuntaje(int cantUndoNivel) {
        int base = 5000;
        int penalizacion = (movimientos * 10) + (empujes * 20) + (cantUndoNivel * 100);
        return Math.max(100, base - penalizacion);
    }

    public String getTiempoFormateado() {
        int horas = segundosTranscurridos / 3600;
        int minutos = (segundosTranscurridos % 3600) / 60;
        int segundos = segundosTranscurridos % 60;
        return String.format("%d:%02d:%02d", horas, minutos, segundos);
    }

    public int getMovimientos() { return movimientos; }
    public int getEmpujes() { return empujes; }
}