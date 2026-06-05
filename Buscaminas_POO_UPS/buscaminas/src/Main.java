import controller.Controlador;

/**
 * Punto de entrada del juego Buscaminas.
 * Instancia el Controlador e inicia el flujo de la aplicación.
 */
public class Main {
    public static void main(String[] args) {
        Controlador controlador = new Controlador();
        controlador.iniciar();
    }
}
