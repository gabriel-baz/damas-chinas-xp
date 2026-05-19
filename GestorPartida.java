import java.awt.Color;

public class GestorPartida {

    private Tablero tablero;
    private Color turnoActual;

    public GestorPartida() {

        tablero = new Tablero();
        turnoActual = Color.RED;

        colocarFichas();
    }

    private void colocarFichas() {

        Casilla[][] casillas = tablero.obtenerCasillas();

        // Jugador rojo (punta superior izquierda) - 10 fichas
        casillas[0][12].establecerFicha(new Ficha(Color.RED));

        casillas[2][11].establecerFicha(new Ficha(Color.RED));
        casillas[2][13].establecerFicha(new Ficha(Color.RED));

        casillas[4][10].establecerFicha(new Ficha(Color.RED));
        casillas[4][12].establecerFicha(new Ficha(Color.RED));
        casillas[4][14].establecerFicha(new Ficha(Color.RED));

        casillas[6][9].establecerFicha(new Ficha(Color.RED));
        casillas[6][11].establecerFicha(new Ficha(Color.RED));
        casillas[6][13].establecerFicha(new Ficha(Color.RED));
        casillas[6][15].establecerFicha(new Ficha(Color.RED));


        // Jugador azul (punta inferior derecha) - 10 fichas
        casillas[32][12].establecerFicha(new Ficha(Color.BLUE));

        casillas[30][11].establecerFicha(new Ficha(Color.BLUE));
        casillas[30][13].establecerFicha(new Ficha(Color.BLUE));

        casillas[28][10].establecerFicha(new Ficha(Color.BLUE));
        casillas[28][12].establecerFicha(new Ficha(Color.BLUE));
        casillas[28][14].establecerFicha(new Ficha(Color.BLUE));

        casillas[26][9].establecerFicha(new Ficha(Color.BLUE));
        casillas[26][11].establecerFicha(new Ficha(Color.BLUE));
        casillas[26][13].establecerFicha(new Ficha(Color.BLUE));
        casillas[26][15].establecerFicha(new Ficha(Color.BLUE));
    }

    public Tablero obtenerTablero() {
        return tablero;
    }

    public Color obtenerTurnoActual() {
        return turnoActual;
    }

    public boolean intentarMover(Casilla origen, Casilla destino) {
        if (origen == null || destino == null) {
            return false;
        }
        if (!origen.esValida() || !destino.esValida()) {
            return false;
        }

        Ficha ficha = origen.obtenerFicha();
        if (ficha == null) {
            return false;
        }
        if (!ficha.obtenerColor().equals(turnoActual)) {
            return false;
        }
        if (destino.obtenerFicha() != null) {
            return false;
        }

        int dr = destino.obtenerFila() - origen.obtenerFila();
        int dc = destino.obtenerColumna() - origen.obtenerColumna();
        boolean adyacente = (dr == 0 && Math.abs(dc) == 2)
                || (Math.abs(dr) == 2 && Math.abs(dc) == 1);

        if (!adyacente) {
            return false;
        }

        destino.establecerFicha(ficha);
        origen.establecerFicha(null);
        turnoActual = turnoActual.equals(Color.RED) ? Color.BLUE : Color.RED;
        return true;
    }
}
