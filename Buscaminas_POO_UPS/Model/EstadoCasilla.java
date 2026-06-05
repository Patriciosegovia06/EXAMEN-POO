package model;

/**
 * Enum que representa el estado visible de una casilla para el jugador.
 */
public enum EstadoCasilla {
    CUBIERTA,     // No revelada aún
    DESCUBIERTA,  // Revelada por el jugador
    MARCADA       // Marcada como posible mina (bandera)
}
