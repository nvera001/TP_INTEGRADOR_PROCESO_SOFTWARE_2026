package Vista;

import javax.swing.*;
import java.awt.*;

public class DialogoVictoria extends JDialog {

    public interface AccionVictoria {
        void avanzarSiguienteNivel();
        void volverAlMenu();
    }

    public DialogoVictoria(Window padre, int nivel, int movs, int empujes, String tiempo, AccionVictoria accion) {
        super(padre, "¡Nivel Completado!", ModalityType.APPLICATION_MODAL);
        setUndecorated(true);
        setSize(nivel == 3 ? 500 : 450, 400);
        setLocationRelativeTo(padre);

        String nivelFormateado = String.format("%02d", nivel);
        String movsFormateados = String.format("%04d", movs);
        String empujesFormateados = String.format("%04d", empujes);

        JPanel panel = new JPanel(new BorderLayout(10, 10)) {
            private final CargadorRecursos cargador = new CargadorRecursos();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (nivel == 3) {
                    Image imgFondo = cargador.getImagen("fondo");
                    if (imgFondo != null) {
                        int anchoFondo = imgFondo.getWidth(null);
                        int altoFondo = imgFondo.getHeight(null);
                        for (int x = 0; x < getWidth(); x += anchoFondo) {
                            for (int y = 0; y < getHeight(); y += altoFondo) {
                                g.drawImage(imgFondo, x, y, this);
                            }
                        }
                    }
                }
            }
        };

        if (nivel != 3) {
            panel.setBackground(new Color(25, 25, 25));
        }
        panel.setBorder(BorderFactory.createLineBorder(new Color(243, 156, 18), 3)); // Borde naranja

        JLabel txtTitulo = new JLabel(nivel == 3 ? "¡JUEGO COMPLETADO!" : "¡NIVEL COMPLETADO!", SwingConstants.CENTER);
        txtTitulo.setFont(new Font("Impact", Font.PLAIN, 36));
        txtTitulo.setForeground(new Color(46, 204, 113)); // Verde victoria
        txtTitulo.setBorder(BorderFactory.createEmptyBorder(25, 10, 10, 10));
        panel.add(txtTitulo, BorderLayout.NORTH);

        String htmlEstadisticas = "<html>"
                + "<body style='color: white; font-family: \"Segoe UI\", sans-serif; text-align: center;'>"
                + "  <p style='font-size: 14px; color: #BDC3C7; font-weight: bold; margin-bottom: 15px;'>ESTADÍSTICAS</p>"
                + "  <table align='center' cellpadding='6' style='font-size: 13px; color: #FFFFFF;'>"
                + "    <tr><td align='left'>Nivel actual:</td><td align='left' style='color: #F1C40F; font-weight: bold; font-family: monospace; font-size: 15px;'>&nbsp;" + nivelFormateado + "</td></tr>"
                + "    <tr><td align='left'>Movimientos:</td><td align='left' style='color: #F1C40F; font-weight: bold; font-family: monospace; font-size: 15px;'>&nbsp;" + movsFormateados + "</td></tr>"
                + "    <tr><td align='left'>Empujes:</td><td align='left' style='color: #F1C40F; font-weight: bold; font-family: monospace; font-size: 15px;'>&nbsp;" + empujesFormateados + "</td></tr>"
                + "    <tr><td align='left'>Tiempo:</td><td align='left' style='color: #F1C40F; font-weight: bold; font-family: monospace; font-size: 15px;'>&nbsp;" + tiempo + "</td></tr>"
                + "  </table>"
                + "</body>"
                + "</html>";

        JLabel txtStats = new JLabel(htmlEstadisticas, SwingConstants.CENTER);
        panel.add(txtStats, BorderLayout.CENTER);

        JPanel panelBoton = new JPanel(new BorderLayout());
        panelBoton.setOpaque(false);
        panelBoton.setBorder(BorderFactory.createEmptyBorder(10, 40, 30, 40));

        if (nivel == 3) {
            JPanel panelDosBotones = new JPanel(new GridLayout(1, 2, 15, 0));
            panelDosBotones.setOpaque(false);

            JButton btnMenu = new JButton("VOLVER AL MENÚ");
            btnMenu.setFont(new Font("Segoe UI", Font.BOLD, 13));
            btnMenu.setForeground(Color.WHITE);
            btnMenu.setBackground(new Color(52, 152, 219)); // Azul
            btnMenu.setFocusPainted(false);
            btnMenu.setBorder(BorderFactory.createEmptyBorder(12, 10, 12, 10));
            btnMenu.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btnMenu.addActionListener(e -> {
                dispose();
                accion.volverAlMenu();
            });

            JButton btnSalir = new JButton("SALIR DEL JUEGO");
            btnSalir.setFont(new Font("Segoe UI", Font.BOLD, 13));
            btnSalir.setForeground(Color.WHITE);
            btnSalir.setBackground(new Color(231, 76, 60)); // Rojo
            btnSalir.setFocusPainted(false);
            btnSalir.setBorder(BorderFactory.createEmptyBorder(12, 10, 12, 10));
            btnSalir.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btnSalir.addActionListener(e -> System.exit(0));

            panelDosBotones.add(btnMenu);
            panelDosBotones.add(btnSalir);
            panelBoton.add(panelDosBotones, BorderLayout.CENTER);
        } else {
            JButton btnSiguiente = new JButton("CONTINUAR AL SIGUIENTE NIVEL");
            btnSiguiente.setFont(new Font("Segoe UI", Font.BOLD, 13));
            btnSiguiente.setForeground(Color.WHITE);
            btnSiguiente.setBackground(new Color(46, 204, 113));
            btnSiguiente.setFocusPainted(false);
            btnSiguiente.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
            btnSiguiente.setCursor(new Cursor(Cursor.HAND_CURSOR));

            btnSiguiente.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) { btnSiguiente.setBackground(new Color(39, 174, 96)); }
                public void mouseExited(java.awt.event.MouseEvent evt) { btnSiguiente.setBackground(new Color(46, 204, 113)); }
            });

            btnSiguiente.addActionListener(e -> {
                dispose();
                accion.avanzarSiguienteNivel();
            });

            panelBoton.add(btnSiguiente, BorderLayout.CENTER);
        }

        panel.add(panelBoton, BorderLayout.SOUTH);
        setContentPane(panel);
    }
}