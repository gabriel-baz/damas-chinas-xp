import java.awt.Color;

public class GestorPartida {

    private Tablero tablero;

    public GestorPartida() {

        tablero = new Tablero();

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
}
