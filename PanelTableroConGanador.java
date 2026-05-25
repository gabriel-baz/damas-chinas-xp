import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PanelTableroConGanador extends JPanel {

    private GestorPartida gestor;
    private Casilla seleccionada;
    private VentanaJuego ventana;
    private java.util.List<Casilla> posibles = new java.util.ArrayList<>();

    public PanelTableroConGanador(VentanaJuego ventana) {
        this.ventana = ventana;
        gestor = new GestorPartida();
        setLayout(null);

        JButton btnReiniciar = new JButton("Reiniciar");
        btnReiniciar.setBounds(400, 10, 120, 30);
        btnReiniciar.addActionListener(e -> ventana.reiniciarPartida());
        add(btnReiniciar);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                manejarClick(e);
            }
        });
    }

    private void manejarClick(MouseEvent e) {
        // Limpiar selección si ya no es válida (cambio de turno o ficha movida)
        if (seleccionada != null && (seleccionada.obtenerFicha() == null || !seleccionada.obtenerFicha().obtenerColor().equals(gestor.obtenerTurnoActual()))) {
            seleccionada = null;
            posibles.clear();
        }

        if (gestor.obtenerEnSaltoMultiple() != null) {
            // Mostrar posibles saltos desde la ficha actual
            posibles = gestor.obtenerSaltosDisponibles(gestor.obtenerEnSaltoMultiple());

            Casilla clic = obtenerCasillaDesdePixel(e.getX(), e.getY());
            if (clic != null && clic.esValida()) {
                // Si hace clic en la ficha actual, termina voluntariamente el turno (deja de saltar)
                if (clic == gestor.obtenerEnSaltoMultiple()) {
                    gestor.terminarSaltoMultiple();
                    posibles.clear();
                    repaint();
                    return;
                }
                
                boolean movio = gestor.intentarMover(gestor.obtenerEnSaltoMultiple(), clic);
                if (movio) {
                    verificarGanador();
                    posibles = gestor.obtenerSaltosDisponibles(gestor.obtenerEnSaltoMultiple());
                }
            }
            
            // Si aún estamos en salto múltiple pero NO hay saltos disponibles, terminar turno
            if (gestor.obtenerEnSaltoMultiple() != null) {
                java.util.List<Casilla> saltosDisponibles = gestor.obtenerSaltosDisponibles(gestor.obtenerEnSaltoMultiple());
                if (saltosDisponibles.isEmpty()) {
                    gestor.limpiarSaltoMultiple();
                    posibles.clear();
                }
            }
            
            repaint();
            return;
        }

        Casilla clic = obtenerCasillaDesdePixel(e.getX(), e.getY());
        if (clic == null || !clic.esValida()) {
            seleccionada = null;
            posibles.clear();
            repaint();
            return;
        }

        if (seleccionada == null) {
            if (clic.obtenerFicha() != null && clic.obtenerFicha().obtenerColor().equals(gestor.obtenerTurnoActual())) {
                seleccionada = clic;
                posibles = gestor.obtenerMovimientosDisponibles(seleccionada);
            }
        } else if (clic == seleccionada) {
            seleccionada = null;
            posibles.clear();
        } else {
            boolean movio = gestor.intentarMover(seleccionada, clic);
            if (!movio && clic.obtenerFicha() != null && clic.obtenerFicha().obtenerColor().equals(gestor.obtenerTurnoActual())) {
                seleccionada = clic;
                posibles = gestor.obtenerMovimientosDisponibles(seleccionada);
            } else if (movio) {
                verificarGanador();
                if (gestor.obtenerEnSaltoMultiple() != null) {
                    seleccionada = gestor.obtenerEnSaltoMultiple();
                    posibles = gestor.obtenerSaltosDisponibles(seleccionada);
                } else {
                    posibles.clear();
                    seleccionada = null;
                }
            }
            if (gestor.obtenerEnSaltoMultiple() == null) {
                seleccionada = null;
            }
        }
        repaint();
    }

    private void verificarGanador() {
        Color ganador = gestor.verificarGanador();
        if (ganador != null) {
            ventana.mostrarGanador(ganador);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Casilla[][] casillas = gestor.obtenerTablero().obtenerCasillas();
        int tamano = 20;

        for (int fila = 0; fila < casillas.length; fila++) {
            for (int columna = 0; columna < casillas[fila].length; columna++) {
                Casilla casilla = casillas[fila][columna];
                if (casilla != null && casilla.esValida()) {
                    int x = columna * tamano + 15;
                    int y = fila * tamano + 15;

                    g.setColor(Color.LIGHT_GRAY);
                    g.fillOval(x, y, 32, 32);
                    g.setColor(Color.BLACK);
                    g.drawOval(x, y, 32, 32);

                    if (casilla.obtenerFicha() != null) {
                        g.setColor(casilla.obtenerFicha().obtenerColor());
                        g.fillOval(x + 4, y + 4, 24, 24);
                        g.setColor(Color.BLACK);
                        g.drawOval(x + 4, y + 4, 24, 24);
                    }

                    if (esPosible(casilla)) {
                        g.setColor(new Color(0, 200, 0, 120));
                        g.fillOval(x - 1, y - 1, 34, 34);
                        g.setColor(Color.GREEN);
                        g.drawOval(x - 2, y - 2, 36, 36);
                        g.drawOval(x - 4, y - 4, 40, 40);
                    }

                    if (casilla == seleccionada) {
                        g.setColor(Color.ORANGE);
                        g.drawOval(x - 3, y - 3, 38, 38);
                        g.drawOval(x - 5, y - 5, 42, 42);
                    }
                }
            }
        }

        Color colorTurno = gestor.obtenerTurnoActual();
        String nombreColor = colorTurno.equals(Color.RED) ? "ROJO" : "AZUL";
        g.setColor(colorTurno);
        g.fillRect(10, getHeight() - 40, 30, 30);
        g.setColor(Color.BLACK);
        g.drawRect(10, getHeight() - 40, 30, 30);
        g.drawString("Turno: " + nombreColor, 50, getHeight() - 15);

        if (gestor.obtenerEnSaltoMultiple() != null) {
            g.setColor(Color.BLUE);
            g.drawString("Haz clic en tu ficha actual para terminar el turno", 10, getHeight() - 65);
            
            g.setColor(Color.GREEN);
            g.drawString("¡Puedes saltar nuevamente!", 10, getHeight() - 50);
        }
    }

    private boolean esPosible(Casilla casilla) {
        return posibles != null && posibles.contains(casilla);
    }

    private Casilla obtenerCasillaDesdePixel(int x, int y) {
        int tamano = 20;
        Casilla[][] casillas = gestor.obtenerTablero().obtenerCasillas();

        for (int fila = 0; fila < casillas.length; fila++) {
            for (int columna = 0; columna < casillas[fila].length; columna++) {
                Casilla casilla = casillas[fila][columna];
                if (casilla != null && casilla.esValida()) {
                    int ox = columna * tamano + 15;
                    int oy = fila * tamano + 15;
                    int cx = ox + 16;
                    int cy = oy + 16;
                    int dx = x - cx;
                    int dy = y - cy;
                    if (dx * dx + dy * dy <= 16 * 16) {
                        return casilla;
                    }
                }
            }
        }
        return null;
    }
}

