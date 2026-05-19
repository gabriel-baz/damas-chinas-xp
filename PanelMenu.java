import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;

public class PanelMenu extends JPanel {

    public PanelMenu(ActionListener iniciarListener) {
        JButton btnIniciar = new JButton("Iniciar Partida");
        btnIniciar.addActionListener(iniciarListener);
        add(btnIniciar);
    }
}
