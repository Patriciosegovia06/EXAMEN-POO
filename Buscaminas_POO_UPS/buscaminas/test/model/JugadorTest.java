package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para la clase Jugador (TDD).
 */
class JugadorTest {

    @Test
    void jugadorIniciaConCeroPartidas() {
        Jugador jugador = new Jugador("Ana");
        assertEquals(0, jugador.getPartidasGanadas());
        assertEquals(0, jugador.getPartidasPerdidas());
    }

    @Test
    void registrarVictoriaIncrementa() {
        Jugador jugador = new Jugador("Ana");
        jugador.registrarVictoria();
        assertEquals(1, jugador.getPartidasGanadas());
    }

    @Test
    void registrarDerrotaIncrementa() {
        Jugador jugador = new Jugador("Ana");
        jugador.registrarDerrota();
        assertEquals(1, jugador.getPartidasPerdidas());
    }

    @Test
    void getNombreDevuelveNombreCorrecto() {
        Jugador jugador = new Jugador("Carlos");
        assertEquals("Carlos", jugador.getNombre());
    }
}
