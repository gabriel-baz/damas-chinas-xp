public class Casilla {

    private int fila;
    private int columna;
    private boolean valida;
    private Ficha ficha;

    public Casilla(int fila, int columna, boolean valida) {
        this.fila = fila;
        this.columna = columna;
        this.valida = valida;
    }

    public int obtenerFila() {
        return fila;
    }

    public int obtenerColumna() {
        return columna;
    }

    public boolean esValida() {
        return valida;
    }

    public Ficha obtenerFicha() {
        return ficha;
    }

    public void establecerFicha(Ficha ficha) {
        this.ficha = ficha;
    }
}
