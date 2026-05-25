import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PanelTablero extends JPanel {

    private GestorPartida gestor;
    private Casilla seleccionada;

    public PanelTablero() {

        gestor = new GestorPartida();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // HU5: Si estamos en salto múltiple, solo permitir saltos desde esa casilla
                if (gestor.obtenerEnSaltoMultiple() != null) {
                    Casilla clic = obtenerCasillaDesdePixel(e.getX(), e.getY());
                    if (clic == null || !clic.esValida()) {
                        repaint();
                        return;
                    }

                    boolean movio = gestor.intentarMover(gestor.obtenerEnSaltoMultiple(), clic);
                    if (movio) {
                        seleccionada = null;
                    }
                    repaint();
                    return;
                }

                Casilla clic = obtenerCasillaDesdePixel(e.getX(), e.getY());
                if (clic == null || !clic.esValida()) {
                    seleccionada = null;
                    repaint();
                    return;
                }

                if (seleccionada == null) {
                    if (clic.obtenerFicha() != null
                            && clic.obtenerFicha().obtenerColor().equals(gestor.obtenerTurnoActual())) {
                        seleccionada = clic;
                    }
                    repaint();
                    return;
                }

                if (clic == seleccionada) {
                    seleccionada = null;
                    repaint();
                    return;
                }

                boolean movio = gestor.intentarMover(seleccionada, clic);
                if (!movio && clic.obtenerFicha() != null
                        && clic.obtenerFicha().obtenerColor().equals(gestor.obtenerTurnoActual())) {
                    seleccionada = clic;
                } else {
                    seleccionada = null;
                }
                repaint();
            }
        });
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

                    // Casilla
                    g.setColor(Color.LIGHT_GRAY);
                    g.fillOval(x, y, 32, 32);

                    g.setColor(Color.BLACK);
                    g.drawOval(x, y, 32, 32);

                    // Ficha
                    if (casilla.obtenerFicha() != null) {

                        g.setColor(casilla.obtenerFicha().obtenerColor());

                        g.fillOval(x + 4, y + 4, 24, 24);

                        g.setColor(Color.BLACK);
                        g.drawOval(x + 4, y + 4, 24, 24);
                    }

                    if (casilla == seleccionada) {
                        g.setColor(Color.ORANGE);
                        g.drawOval(x - 2, y - 2, 36, 36);
                    }
                }
            }
        }

        // Mostrar turno actual
        Color colorTurno = gestor.obtenerTurnoActual();
        String nombreColor = colorTurno.equals(Color.RED) ? "ROJO" : "AZUL";
        
        g.setColor(colorTurno);
        g.fillRect(10, getHeight() - 40, 30, 30);
        
        g.setColor(Color.BLACK);
        g.drawRect(10, getHeight() - 40, 30, 30);
        g.drawString("Turno: " + nombreColor, 50, getHeight() - 15);

        // HU5: Mostrar indicador de saltos múltiples
        if (gestor.obtenerEnSaltoMultiple() != null) {
            g.setColor(Color.GREEN);
            g.drawString("¡Puedes saltar nuevamente!", 10, getHeight() - 50);
        }
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
