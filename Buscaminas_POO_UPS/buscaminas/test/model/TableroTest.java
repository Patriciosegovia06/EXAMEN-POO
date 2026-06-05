package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para la clase Tablero (TDD).
 */
class TableroTest {

    private Tablero tablero;

    @BeforeEach
    void setUp() {
        tablero = new Tablero();
    }

    @Test
    void tableroTieneDiezMinas() {
        int contador = 0;
        for (int f = 0; f < Tablero.FILAS; f++) {
            for (int c = 0; c < Tablero.COLUMNAS; c++) {
                if (tablero.getCasilla(f, c).isTieneMina()) contador++;
            }
        }
        assertEquals(Tablero.TOTAL_MINAS, contador);
    }

    @Test
    void tableroEsDiezPorDiez() {
        // Verificamos que no lanza excepción en los límites
        assertNotNull(tablero.getCasilla(0, 0));
        assertNotNull(tablero.getCasilla(9, 9));
    }

    @Test
    void todasLasCasillasInicianCubiertas() {
        for (int f = 0; f < Tablero.FILAS; f++) {
            for (int c = 0; c < Tablero.COLUMNAS; c++) {
                assertTrue(tablero.getCasilla(f, c).estaCubierta());
            }
        }
    }

    @Test
    void descubrirCasillaLaDescubre() {
        // Encontrar primera casilla sin mina
        for (int f = 0; f < Tablero.FILAS; f++) {
            for (int c = 0; c < Tablero.COLUMNAS; c++) {
                if (!tablero.getCasilla(f, c).isTieneMina()) {
                    tablero.descubrir(f, c);
                    assertTrue(tablero.getCasilla(f, c).estaDescubierta());
                    return;
                }
            }
        }
    }

    @Test
    void descubrirMinaRetornaTrue() {
        for (int f = 0; f < Tablero.FILAS; f++) {
            for (int c = 0; c < Tablero.COLUMNAS; c++) {
                if (tablero.getCasilla(f, c).isTieneMina()) {
                    boolean resultado = tablero.descubrir(f, c);
                    assertTrue(resultado);
                    return;
                }
            }
        }
    }

    @Test
    void descubrirCasillaSeguraRetornaFalse() {
        for (int f = 0; f < Tablero.FILAS; f++) {
            for (int c = 0; c < Tablero.COLUMNAS; c++) {
                if (!tablero.getCasilla(f, c).isTieneMina()) {
                    boolean resultado = tablero.descubrir(f, c);
                    assertFalse(resultado);
                    return;
                }
            }
        }
    }

    @Test
    void alternarMarcaFunciona() {
        // Marcar casilla sin mina
        for (int f = 0; f < Tablero.FILAS; f++) {
            for (int c = 0; c < Tablero.COLUMNAS; c++) {
                if (!tablero.getCasilla(f, c).isTieneMina()) {
                    tablero.alternarMarca(f, c);
                    assertTrue(tablero.getCasilla(f, c).estaMarcada());
                    tablero.alternarMarca(f, c);
                    assertTrue(tablero.getCasilla(f, c).estaCubierta());
                    return;
                }
            }
        }
    }

    @Test
    void convertirLetraAFila() {
        assertEquals(0, Tablero.letraAFila('A'));
        assertEquals(9, Tablero.letraAFila('J'));
        assertEquals(-1, Tablero.letraAFila('Z'));
    }

    @Test
    void convertirNumeroAColumna() {
        assertEquals(0, Tablero.numeroAColumna(1));
        assertEquals(9, Tablero.numeroAColumna(10));
    }

    @Test
    void minasRestantesIniciaEnDiez() {
        assertEquals(Tablero.TOTAL_MINAS, tablero.getMinasRestantes());
    }

    @Test
    void marcarReduceMinasRestantes() {
        tablero.alternarMarca(0, 0);
        assertEquals(Tablero.TOTAL_MINAS - 1, tablero.getMinasRestantes());
    }
}
