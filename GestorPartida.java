import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class GestorPartida {

    private Tablero tablero;
    private Color turnoActual;
    private Casilla enSaltoMultiple = null;
    private Casilla ultimaSaltada = null;

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

    // HU4: Validar si un salto es válido
    private boolean esAltoValido(Casilla origen, Casilla destino) {
        if (origen == null || destino == null) return false;
        if (!origen.esValida() || !destino.esValida()) return false;
        if (destino.obtenerFicha() != null) return false;

        Casilla intermedia = obtenerCasillaIntermedia(origen, destino);
        return intermedia != null && intermedia.esValida() && intermedia.obtenerFicha() != null;
    }

    // HU5: Obtener lista de casillas donde se puede saltar desde una posición
    // Solo una opción de salto por cada pieza adyacente
    public List<Casilla> obtenerSaltosDisponibles(Casilla origen) {
        return obtenerSaltosDisponibles(origen, null);
    }

    public List<Casilla> obtenerSaltosDisponibles(Casilla origen, Casilla excluirIntermedia) {
        List<Casilla> saltos = new ArrayList<>();
        if (origen == null || origen.obtenerFicha() == null) return saltos;

        Casilla[][] casillas = tablero.obtenerCasillas();
        int fila = origen.obtenerFila();
        int col = origen.obtenerColumna();

        // Direcciones adyacentes: buscar fichas al lado (paso de 1)
        int[][] adyacentes = {{2, 1}, {2, -1}, {-2, 1}, {-2, -1}, {0, 2}, {0, -2}};

        for (int[] ady : adyacentes) {
            int fichaFila = fila + ady[0];
            int fichaCol = col + ady[1];

            if (fichaFila >= 0 && fichaFila < casillas.length && fichaCol >= 0 && fichaCol < casillas[0].length) {
                Casilla fichaAdyacente = casillas[fichaFila][fichaCol];
                if (fichaAdyacente.obtenerFicha() != null && fichaAdyacente != excluirIntermedia) {
                    // Calcular el destino al otro lado de esta ficha
                    int destFila = fila + ady[0] * 2;
                    int destCol = col + ady[1] * 2;

                    if (destFila >= 0 && destFila < casillas.length && destCol >= 0 && destCol < casillas[0].length) {
                        Casilla destino = casillas[destFila][destCol];
                        if (destino.esValida() && destino.obtenerFicha() == null) {
                            saltos.add(destino);
                        }
                    }
                }
            }
        }
        return saltos;
    }

    // HU8: Verificar si hay ganador
    public Color verificarGanador() {
        Casilla[][] casillas = tablero.obtenerCasillas();
        int rojoEnDestino = 0, azulEnDestino = 0;

        // Triángulo destino para ROJO: fila >= 26
        for (int f = 26; f < 33; f++) {
            for (int c = 0; c < 25; c++) {
                if (casillas[f][c] != null && casillas[f][c].esValida() && casillas[f][c].obtenerFicha() != null
                        && casillas[f][c].obtenerFicha().obtenerColor().equals(Color.RED)) {
                    rojoEnDestino++;
                }
            }
        }

        // Triángulo destino para AZUL: fila <= 6
        for (int f = 0; f <= 6; f++) {
            for (int c = 0; c < 25; c++) {
                if (casillas[f][c] != null && casillas[f][c].esValida() && casillas[f][c].obtenerFicha() != null
                        && casillas[f][c].obtenerFicha().obtenerColor().equals(Color.BLUE)) {
                    azulEnDestino++;
                }
            }
        }

        if (rojoEnDestino == 10) return Color.RED;
        if (azulEnDestino == 10) return Color.BLUE;
        return null;
    }

    public Casilla obtenerEnSaltoMultiple() {
        return enSaltoMultiple;
    }

    public void limpiarSaltoMultiple() {
        enSaltoMultiple = null;
    }

    // Terminar voluntariamente el salto múltiple y cambiar turno
    public void terminarSaltoMultiple() {
        enSaltoMultiple = null;
        ultimaSaltada = null;
        cambiarTurno();
    }

    public boolean intentarMover(Casilla origen, Casilla destino) {
        if (origen == null || destino == null) return false;
        if (!origen.esValida() || !destino.esValida()) return false;

        Ficha ficha = origen.obtenerFicha();
        if (ficha == null) return false;
        if (!ficha.obtenerColor().equals(turnoActual)) return false;
        if (destino.obtenerFicha() != null) return false;

        int dr = destino.obtenerFila() - origen.obtenerFila();
        int dc = destino.obtenerColumna() - origen.obtenerColumna();

        // Verificar paso (movimiento adyacente)
        boolean esPaso = (dr == 0 && Math.abs(dc) == 2) || (Math.abs(dr) == 2 && Math.abs(dc) == 1);
        
        // Verificar salto (HU4)
        boolean esSalto = esAltoValido(origen, destino);

        // HU5: Si estamos en salto múltiple, SOLO se permiten saltos
        if (enSaltoMultiple != null && esPaso) {
            return false; // No permitir pasos durante salto múltiple
        }

        if (esPaso) {
            // Movimiento simple: paso adyacente
            destino.establecerFicha(ficha);
            origen.establecerFicha(null);
            enSaltoMultiple = null;
            ultimaSaltada = null;
            cambiarTurno();
            return true;
        } else if (esSalto) {
            // Salto: mover sin eliminar la ficha intermedia
            Casilla intermedia = obtenerCasillaIntermedia(origen, destino);
            if (intermedia != null && intermedia.obtenerFicha() != null) {
                destino.establecerFicha(ficha);
                origen.establecerFicha(null);

                // HU5: Verificar si hay más saltos disponibles (excluyendo la ficha recién saltada)
                List<Casilla> masaltos = obtenerSaltosDisponibles(destino, intermedia);
                if (!masaltos.isEmpty()) {
                    enSaltoMultiple = destino; // Mantener selección
                    ultimaSaltada = intermedia;
                    return true; // No cambiar turno
                } else {
                    enSaltoMultiple = null;
                    ultimaSaltada = null;
                    cambiarTurno();
                    return true;
                }
            }
        }
        return false;
    }

    private Casilla obtenerCasillaIntermedia(Casilla origen, Casilla destino) {
        int dr = destino.obtenerFila() - origen.obtenerFila();
        int dc = destino.obtenerColumna() - origen.obtenerColumna();
        Casilla[][] casillas = tablero.obtenerCasillas();

        int imf;
        int imc;
        if (dr == 0 && Math.abs(dc) == 4) {
            imf = origen.obtenerFila();
            imc = origen.obtenerColumna() + (dc / 2);
        } else if (Math.abs(dr) == 4 && Math.abs(dc) == 2) {
            imf = origen.obtenerFila() + (dr / 2);
            imc = origen.obtenerColumna() + (dc / 2);
        } else {
            return null;
        }

        if (imf >= 0 && imf < casillas.length && imc >= 0 && imc < casillas[0].length) {
            return casillas[imf][imc];
        }
        return null;
    }

    private void cambiarTurno() {
        turnoActual = turnoActual.equals(Color.RED) ? Color.BLUE : Color.RED;
    }
}
