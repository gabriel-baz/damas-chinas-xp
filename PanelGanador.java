import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.Font;

public class PanelGanador extends JPanel {

    private Color colorGanador;
    private JButton btnReiniciar;
    private JButton btnSalir;

    public PanelGanador(Color colorGanador, ActionListener reiniciarListener) {
        this.colorGanador = colorGanador;
        
        setLayout(null);
        
        btnReiniciar = new JButton("Reiniciar");
        btnReiniciar.setBounds(150, 350, 100, 40);
        btnReiniciar.addActionListener(reiniciarListener);
        add(btnReiniciar);
        
        btnSalir = new JButton("Salir");
        btnSalir.setBounds(300, 350, 100, 40);
        btnSalir.addActionListener(e -> System.exit(0));
        add(btnSalir);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        String nombreColor = colorGanador.equals(Color.RED) ? "ROJO" : "AZUL";
        
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());
        
        g.setColor(colorGanador);
        Font fuente = new Font("Arial", Font.BOLD, 48);
        g.setFont(fuente);
        g.drawString("¡GANADOR!", 120, 150);
        
        g.setColor(Color.BLACK);
        g.drawString("Jugador " + nombreColor, 130, 250);
    }
}

