public class Tablero {

    private Casilla[][] casillas;

    private final boolean[][] formaTablero = {

            {false,false,false,false,false,false,false,false,false,false,false,false,true,false,false,false,false,false,false,false,false,false,false,false,false},
            {false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false},
            {false,false,false,false,false,false,false,false,false,false,false,true,false,true,false,false,false,false,false,false,false,false,false,false,false},
            {false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false},
            {false,false,false,false,false,false,false,false,false,false,true,false,true,false,true,false,false,false,false,false,false,false,false,false,false},
            {false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false},
            {false,false,false,false,false,false,false,false,false,true,false,true,false,true,false,true,false,false, false,false,false,false,false,false,false},
            {false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false},
            {true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true},
            {false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false},
            {false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false},
            {false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false},
            {false,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,false},
            {false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false},
            {false,false,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,false,false},
            {false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false},
            {false,false,false,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,false,false,false},
            {false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false},
            {false,false,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,false,false},
            {false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false},
            {false,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,false},
            {false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false},
            {false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false},
            {false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false},
            {true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true},
            {false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false},
            {false,false,false,false,false,false,false,false,false,true,false,true,false,true,false,true,false,false, false,false,false,false,false,false,false},
            {false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false},
            {false,false,false,false,false,false,false,false,false,false,true,false,true,false,true,false,false,false,false,false,false,false,false,false,false},
            {false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false},
            {false,false,false,false,false,false,false,false,false,false,false,true,false,true,false,false,false,false,false,false,false,false,false,false,false},
            {false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false},
            {false,false,false,false,false,false,false,false,false,false,false,false,true,false,false,false,false,false,false,false,false,false,false,false,false}

    };

    public Tablero() {

        casillas = new Casilla[33][25];

        inicializarCasillas();
    }

    private void inicializarCasillas() {

        for (int fila = 0; fila < 33; fila++) {

            for (int columna = 0; columna < 25; columna++) {

                casillas[fila][columna] = new Casilla(fila, columna, formaTablero[fila][columna]);
            }
        }
    }

    public Casilla[][] obtenerCasillas() {
        return casillas;
    }
}
