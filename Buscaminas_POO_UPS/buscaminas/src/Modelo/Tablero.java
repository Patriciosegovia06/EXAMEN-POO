package model;

import java.io.Serializable;
import java.util.Random;

/**
 * Modelo del tablero 10x10 del Buscaminas.
 * Gestiona la inicialización, colocación de minas y lógica de revelado.
 */
public class Tablero implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final int FILAS = 10;
    public static final int COLUMNAS = 10;
    public static final int TOTAL_MINAS = 10;

    // Las filas usan letras A-J
    private static final String LETRAS = "ABCDEFGHIJ";

    private Casilla[][] casillas;
    private int minasRestantes;
    private int casillasSegurasPendientes;

    public Tablero() {
        casillas = new Casilla[FILAS][COLUMNAS];
        inicializarCasillas();
        colocarMinasAleatoriamente();
        calcularMinasAdyacentes();
        minasRestantes = TOTAL_MINAS;
        casillasSegurasPendientes = (FILAS * COLUMNAS) - TOTAL_MINAS;
    }

    // ──── Inicialización ────

    private void inicializarCasillas() {
        for (int f = 0; f < FILAS; f++) {
            for (int c = 0; c < COLUMNAS; c++) {
                casillas[f][c] = new Casilla();
            }
        }
    }

    private void colocarMinasAleatoriamente() {
        Random random = new Random();
        int minasColocadas = 0;
        while (minasColocadas < TOTAL_MINAS) {
            int f = random.nextInt(FILAS);
            int c = random.nextInt(COLUMNAS);
            if (!casillas[f][c].isTieneMina()) {
                casillas[f][c].setTieneMina(true);
                minasColocadas++;
            }
        }
    }

    private void calcularMinasAdyacentes() {
        for (int f = 0; f < FILAS; f++) {
            for (int c = 0; c < COLUMNAS; c++) {
                if (!casillas[f][c].isTieneMina()) {
                    casillas[f][c].setMinasAdyacentes(contarMinasAlrededor(f, c));
                }
            }
        }
    }

    private int contarMinasAlrededor(int fila, int col) {
        int count = 0;
        for (int df = -1; df <= 1; df++) {
            for (int dc = -1; dc <= 1; dc++) {
                int nf = fila + df;
                int nc = col + dc;
                if (nf >= 0 && nf < FILAS && nc >= 0 && nc < COLUMNAS) {
                    if (casillas[nf][nc].isTieneMina()) count++;
                }
            }
        }
        return count;
    }

    // ──── Acciones del juego ────

    /**
     * Descubre una casilla. Si está vacía (0 minas adyacentes),
     * revela en cascada todas las casillas vacías conectadas.
     * @return true si había una mina (derrota), false si es segura.
     */
    public boolean descubrir(int fila, int col) {
        Casilla casilla = casillas[fila][col];
        if (casilla.estaDescubierta()) return false;
        casilla.setEstado(EstadoCasilla.DESCUBIERTA);
        if (casilla.isTieneMina()) return true; // derrota

        casillasSegurasPendientes--;
        if (casilla.getMinasAdyacentes() == 0) {
            revelarCascada(fila, col);
        }
        return false;
    }

    /**
     * Revela en cascada las casillas vacías adyacentes (BFS).
     */
    private void revelarCascada(int fila, int col) {
        for (int df = -1; df <= 1; df++) {
            for (int dc = -1; dc <= 1; dc++) {
                int nf = fila + df;
                int nc = col + dc;
                if (nf >= 0 && nf < FILAS && nc >= 0 && nc < COLUMNAS) {
                    Casilla vecina = casillas[nf][nc];
                    if (vecina.estaCubierta() && !vecina.isTieneMina()) {
                        vecina.setEstado(EstadoCasilla.DESCUBIERTA);
                        casillasSegurasPendientes--;
                        if (vecina.getMinasAdyacentes() == 0) {
                            revelarCascada(nf, nc);
                        }
                    }
                }
            }
        }
    }

    /**
     * Alterna la marca (bandera) sobre una casilla cubierta.
     */
    public void alternarMarca(int fila, int col) {
        Casilla casilla = casillas[fila][col];
        if (casilla.estaDescubierta()) return;
        if (casilla.estaMarcada()) {
            casilla.setEstado(EstadoCasilla.CUBIERTA);
            minasRestantes++;
        } else {
            casilla.setEstado(EstadoCasilla.MARCADA);
            minasRestantes--;
        }
    }

    /**
     * Revela todas las minas (al perder).
     */
    public void revelarTodasLasMinas() {
        for (int f = 0; f < FILAS; f++) {
            for (int c = 0; c < COLUMNAS; c++) {
                if (casillas[f][c].isTieneMina()) {
                    casillas[f][c].setEstado(EstadoCasilla.DESCUBIERTA);
                }
            }
        }
    }

    // ──── Condición de victoria ────

    public boolean estaCompleto() {
        return casillasSegurasPendientes == 0;
    }

    // ──── Conversión de coordenadas ────

    /**
     * Convierte una letra (A-J) al índice de fila (0-9).
     */
    public static int letraAFila(char letra) {
        return LETRAS.indexOf(Character.toUpperCase(letra));
    }

    /**
     * Convierte un número de columna (1-10) al índice (0-9).
     */
    public static int numeroAColumna(int numero) {
        return numero - 1;
    }

    // ──── Getters ────

    public Casilla getCasilla(int fila, int col) {
        return casillas[fila][col];
    }

    public int getMinasRestantes() {
        return minasRestantes;
    }

    public int getCasillasSegurasPendientes() {
        return casillasSegurasPendientes;
    }
}
