package exceptions;

/**
 * Se lanza cuando el jugador ingresa coordenadas fuera del rango del tablero.
 */
public class CoordenadaInvalidaException extends BuscaminasException {

    public CoordenadaInvalidaException(String coordenada) {
        super("Coordenada inválida: '" + coordenada + "'. Use formato LetraNumero (ej: A5).");
    }
}
