import javax.swing.JFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaJuego extends JFrame {

    public VentanaJuego() {

        setTitle("Damas Chinas");
        setSize(550, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        add(new PanelMenu(e -> iniciarPartida()));

        setVisible(true);
    }

    private void iniciarPartida() {
        getContentPane().removeAll();
        add(new PanelTablero());
        revalidate();
        repaint();
    }
}
