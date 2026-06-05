package model;

import java.io.Serializable;

/**
 * Contenedor serializable para guardar/cargar el estado completo de una partida.
 */
public class PartidaGuardada implements Serializable {

    private static final long serialVersionUID = 1L;

    private Tablero tablero;
    private Jugador jugador;
    private EstadoJuego estadoJuego;

    public PartidaGuardada(Tablero tablero, Jugador jugador, EstadoJuego estadoJuego) {
        this.tablero = tablero;
        this.jugador = jugador;
        this.estadoJuego = estadoJuego;
    }

    public Tablero getTablero()        { return tablero; }
    public Jugador getJugador()        { return jugador; }
    public EstadoJuego getEstadoJuego(){ return estadoJuego; }
}
