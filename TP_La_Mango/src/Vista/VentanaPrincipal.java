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

        JButton btnVolumen = new JButton(GestorAudio.isSonidoActivo() ? "Sonido: ON" : "Sonido: OFF");
        btnVolumen.setBackground(GestorAudio.isSonidoActivo() ? new Color(52, 73, 94) : new Color(127, 140, 141));
        btnVolumen.setForeground(Color.WHITE);
        btnVolumen.setFocusable(false); // Crucial para que las flechas del teclado sigan moviendo al jugador
        btnVolumen.addActionListener(e -> {
            GestorAudio.toggleSonido();
            btnVolumen.setText(GestorAudio.isSonidoActivo() ? "Sonido: ON" : "Sonido: OFF");
            btnVolumen.setBackground(GestorAudio.isSonidoActivo() ? new Color(52, 73, 94) : new Color(127, 140, 141));
        });
        hudSuperior.add(btnVolumen);

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
}
