import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;

public class PanelTablero extends JPanel {

    private GestorPartida gestor;

    public PanelTablero() {

        gestor = new GestorPartida();
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
                }
            }
        }
    }
}
