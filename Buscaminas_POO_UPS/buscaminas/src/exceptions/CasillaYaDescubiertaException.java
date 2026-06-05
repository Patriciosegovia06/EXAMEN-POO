package exceptions;

/**
 * Se lanza cuando el jugador intenta descubrir una casilla que ya fue revelada.
 */
public class CasillaYaDescubiertaException extends BuscaminasException {

    public CasillaYaDescubiertaException(String coordenada) {
        super("La casilla " + coordenada + " ya fue descubierta.");
    }
}
