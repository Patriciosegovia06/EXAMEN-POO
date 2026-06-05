package exceptions;

/**
 * Excepción base para errores específicos del juego Buscaminas.
 */
public class BuscaminasException extends Exception {

    public BuscaminasException(String mensaje) {
        super(mensaje);
    }
}
