package Vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import Controlador.GestorJuego;
import Modelo.Nucleo.Direccion;
import Modelo.Nucleo.Matriz;


public class VentanaPrincipal extends JFrame {
    private final GestorJuego gestor;
    private boolean juegoIniciado = false;
    private JLabel txtHUD;

    private JButton btnUndo;
    private JButton btnVolumen;

    private boolean undoHabilitadoPorNivel = true;

    public VentanaPrincipal(GestorJuego gestor) {
        this.gestor = gestor;
        setTitle("Sokoban ");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setUndecorated(true);

        Dimension tamanioPantalla = Toolkit.getDefaultToolkit().getScreenSize();

        setBounds(0, 0, tamanioPantalla.width, tamanioPantalla.height);

        setMinimumSize(new Dimension(800, 600));

        mostrarMenuPrincipal();

        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (!juegoIniciado) return;

                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP -> gestor.intentarMover(Direccion.ARRIBA);
                    case KeyEvent.VK_DOWN -> gestor.intentarMover(Direccion.ABAJO);
                    case KeyEvent.VK_LEFT -> gestor.intentarMover(Direccion.IZQUIERDA);
                    case KeyEvent.VK_RIGHT -> gestor.intentarMover(Direccion.DERECHA);
                    case KeyEvent.VK_R -> gestor.reiniciarNivel();
                    case KeyEvent.VK_M -> gestor.volverAlMenu();
                    case KeyEvent.VK_Z -> {
                        if (undoHabilitadoPorNivel) {
                            gestor.deshacerCincoMovimientos();
                        }
                    }
                    case KeyEvent.VK_S -> alternarSonido();
                }
            }
        });
        setVisible(true);
    }

    public void mostrarMenuPrincipal() {
        juegoIniciado = false;
        PanelMenu menu = new PanelMenu(() -> gestor.iniciarJuego());
        setContentPane(menu);
        revalidate();
        repaint();
    }

    public void mostrarPantallaJuego(Matriz matriz) {
        getContentPane().removeAll();

        JPanel panelContenedorJuego = new JPanel(new BorderLayout());
        panelContenedorJuego.setBackground(Color.BLACK);

        JPanel hudSuperior = new JPanel(new FlowLayout(FlowLayout.CENTER, 35, 12));
        hudSuperior.setBackground(new Color(40, 40, 40));

        txtHUD = new JLabel();
        txtHUD.setFont(new Font("Monospaced", Font.BOLD, 20));
        txtHUD.setForeground(Color.WHITE);

        hudSuperior.add(txtHUD);
        actualizarHUD();

        btnVolumen = new JButton(GestorAudio.isSonidoActivo() ? "Sonido: ON [S]" : "Sonido: OFF [S]");
        btnVolumen.setBackground(GestorAudio.isSonidoActivo() ? new Color(52, 73, 94) : new Color(127, 140, 141));
        btnVolumen.setForeground(Color.WHITE);
        btnVolumen.setFocusable(false);

        btnVolumen.addActionListener(e -> alternarSonido());
        hudSuperior.add(btnVolumen);

        btnUndo = new JButton("Deshacer [Z]");
        btnUndo.setBackground(new Color(155, 89, 182));
        btnUndo.setForeground(Color.WHITE);
        btnUndo.setFocusable(false);
        btnUndo.setContentAreaFilled(true);
        btnUndo.setOpaque(true);
        btnUndo.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnUndo.addActionListener(e -> {
            if (undoHabilitadoPorNivel) {
                gestor.deshacerCincoMovimientos();
            }
        });
        hudSuperior.add(btnUndo);

        JButton btnReiniciar = new JButton("Reiniciar [R]");
        btnReiniciar.setBackground(new Color(230, 126, 34));
        btnReiniciar.setForeground(Color.WHITE);
        btnReiniciar.setFocusable(false);
        btnReiniciar.addActionListener(e -> gestor.reiniciarNivel());
        hudSuperior.add(btnReiniciar);

        JButton btnMenu = new JButton("Menú principal [M]");
        btnMenu.setBackground(new Color(149, 165, 166));
        btnMenu.setForeground(Color.WHITE);
        btnMenu.setFocusable(false);
        btnMenu.addActionListener(e -> gestor.volverAlMenu());
        hudSuperior.add(btnMenu);

        panelContenedorJuego.add(hudSuperior, BorderLayout.NORTH);

        PanelJuego panelJuego = new PanelJuego(matriz);
        panelContenedorJuego.add(panelJuego, BorderLayout.CENTER);

        setContentPane(panelContenedorJuego);
        juegoIniciado = true;

        revalidate();
        repaint();
        requestFocusInWindow();
    }

    public void setUndoVisible(boolean habilitado) {
        this.undoHabilitadoPorNivel = habilitado;

        if (btnUndo != null) {
            if (habilitado) {
                btnUndo.setBackground(new Color(155, 89, 182));
                btnUndo.setCursor(new Cursor(Cursor.HAND_CURSOR));
            } else {
                btnUndo.setBackground(new Color(85, 75, 90));
                btnUndo.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        }
    }

    public void actualizarPantalla() {
        repaint();
    }

    public void actualizarHUD() {
        if (txtHUD == null || gestor == null) return;

        String nivel = String.format("%02d", gestor.getNivelActual());
        String moves = String.format("%04d", gestor.getMovimientos());
        String pushes = String.format("%04d", gestor.getEmpujes());
        String time = gestor.getTiempoFormateado();

        txtHUD.setText(nivel + " | Movimientos: " + moves + "   Empujes: " + pushes + "   Tiempo: " + time);
    }

    private void alternarSonido() {
        GestorAudio.toggleSonido();

        if (btnVolumen != null) {
            btnVolumen.setText(GestorAudio.isSonidoActivo() ? "Sonido: ON [S]" : "Sonido: OFF [S]");
            btnVolumen.setBackground(GestorAudio.isSonidoActivo() ? new Color(52, 73, 94) : new Color(127, 140, 141));
        }
    }
}
