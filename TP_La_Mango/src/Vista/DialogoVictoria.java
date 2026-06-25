package Vista;

import javax.swing.*;
import java.awt.*;

public class DialogoVictoria extends JDialog {

    public interface AccionVictoria {
        void avanzarSiguienteNivel();
    }

    public DialogoVictoria(Window padre, int nivel, int movs, int empujes, String tiempo, AccionVictoria accion) {
        super(padre, "¡Nivel Completado!", ModalityType.APPLICATION_MODAL);
        setUndecorated(true);
        setSize(500, 420); // Achicamos un pelín el alto ya que no lleva puntaje
        setLocationRelativeTo(padre);

        // --- FORMATEO ARCADE (Igual a tu foto) ---
        String nivelFormateado = String.format("%02d", nivel);
        String movsFormateados = String.format("%04d", movs);
        String empujesFormateados = String.format("%04d", empujes);

        // Contenedor principal (Gris oscuro traslúcido con borde naranja)
        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBackground(new Color(20, 20, 20, 245));
        panel.setBorder(BorderFactory.createLineBorder(new Color(243, 156, 18), 3));

        // Título de la ventana
        JLabel txtTitulo = new JLabel("¡NIVEL COMPLETADO!", SwingConstants.CENTER);
        txtTitulo.setFont(new Font("Impact", Font.PLAIN, 36));
        txtTitulo.setForeground(new Color(46, 204, 113)); // Verde victoria
        txtTitulo.setBorder(BorderFactory.createEmptyBorder(25, 10, 10, 10));
        panel.add(txtTitulo, BorderLayout.NORTH);

        // Tabla con la estética limpia que pediste
        String htmlEstadisticas = "<html><body style='color: white; font-family: \"Segoe UI\"; font-size: 15px;'>"
                + "<div style='text-align: center; margin-bottom: 25px;'>"
                + "  <span style='font-size: 18px; color: #BDC3C7; font-weight: bold;'>ESTADÍSTICAS DEL ALMACÉN</span>"
                + "</div>"
                + "<table style='width: 100%; padding-left: 60px; line-height: 2.2;'>"
                + "  <tr><td>🆔 Nivel actual:</td><td style='color: #F1C40F; font-weight: bold; font-family: monospace; font-size: 16px;'>" + nivelFormateado + "</td></tr>"
                + "  <tr><td>🏃‍♂️ Movimientos de jugador (moves):</td><td style='color: #F1C40F; font-weight: bold; font-family: monospace; font-size: 16px;'>" + movsFormateados + "</td></tr>"
                + "  <tr><td>📦 Movimientos con caja (pushes):</td><td style='color: #F1C40F; font-weight: bold; font-family: monospace; font-size: 16px;'>" + empujesFormateados + "</td></tr>"
                + "  <tr><td>⏱️ Tiempo transcurrido (time):</td><td style='color: #F1C40F; font-weight: bold; font-family: monospace; font-size: 16px;'>" + tiempo + "</td></tr>"
                + "</table>"
                + "</body></html>";

        JLabel txtStats = new JLabel(htmlEstadisticas, SwingConstants.CENTER);
        panel.add(txtStats, BorderLayout.CENTER);

        // Botón Continuar
        JButton btnSiguiente = new JButton("CONTINUAR AL SIGUIENTE NIVEL");
        btnSiguiente.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnSiguiente.setForeground(Color.WHITE);
        btnSiguiente.setBackground(new Color(46, 204, 113));
        btnSiguiente.setFocusPainted(false);
        btnSiguiente.setBorder(BorderFactory.createEmptyBorder(12, 0, 12, 0));
        btnSiguiente.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnSiguiente.addActionListener(e -> {
            dispose();
            accion.avanzarSiguienteNivel();
        });

        JPanel panelBoton = new JPanel(new BorderLayout());
        panelBoton.setBackground(new Color(20, 20, 20, 0));
        panelBoton.setBorder(BorderFactory.createEmptyBorder(10, 40, 30, 40));
        panelBoton.add(btnSiguiente, BorderLayout.CENTER);
        panel.add(panelBoton, BorderLayout.SOUTH);

        setContentPane(panel);
    }
}