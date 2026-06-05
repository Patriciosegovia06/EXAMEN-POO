package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para la clase Casilla (TDD).
 */
class CasillaTest {

    private Casilla casilla;

    @BeforeEach
    void setUp() {
        casilla = new Casilla();
    }

    @Test
    void casillaInicialEstaCubierta() {
        assertEquals(EstadoCasilla.CUBIERTA, casilla.getEstado());
        assertTrue(casilla.estaCubierta());
    }

    @Test
    void casillaInicialNoTieneMina() {
        assertFalse(casilla.isTieneMina());
    }

    @Test
    void casillaInicialTieneCeroMinasAdyacentes() {
        assertEquals(0, casilla.getMinasAdyacentes());
    }

    @Test
    void representacionCubiertaEsPunto() {
        assertEquals('·', casilla.representacionVisible());
    }

    @Test
    void representacionMarcadaEsF() {
        casilla.setEstado(EstadoCasilla.MARCADA);
        assertEquals('F', casilla.representacionVisible());
    }

    @Test
    void representacionDescubiertaVaciaEsV() {
        casilla.setEstado(EstadoCasilla.DESCUBIERTA);
        casilla.setMinasAdyacentes(0);
        assertEquals('V', casilla.representacionVisible());
    }

    @Test
    void representacionDescubiertaConMinaEsX() {
        casilla.setTieneMina(true);
        casilla.setEstado(EstadoCasilla.DESCUBIERTA);
        assertEquals('X', casilla.representacionVisible());
    }

    @Test
    void representacionDescubiertaMuestraNumeroAdyacentes() {
        casilla.setEstado(EstadoCasilla.DESCUBIERTA);
        casilla.setMinasAdyacentes(3);
        assertEquals('3', casilla.representacionVisible());
    }

    @Test
    void alternarEstadoDescubierto() {
        casilla.setEstado(EstadoCasilla.DESCUBIERTA);
        assertTrue(casilla.estaDescubierta());
        assertFalse(casilla.estaCubierta());
    }

    @Test
    void alternarEstadoMarcado() {
        casilla.setEstado(EstadoCasilla.MARCADA);
        assertTrue(casilla.estaMarcada());
    }
}
