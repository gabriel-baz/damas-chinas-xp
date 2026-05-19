import javax.swing.JFrame;

public class VentanaJuego extends JFrame {

    public VentanaJuego() {

        setTitle("Damas Chinas");
        setSize(550, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        add(new PanelTablero());

        setVisible(true);
    }
}
