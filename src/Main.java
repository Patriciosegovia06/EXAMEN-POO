import controlador.BuscaminasControlador;

/**
 * Punto de entrada del juego Buscaminas.
   * Instancia el controlador principal e inicia el flujo de la aplicacion.
   */
public class Main {
      public static void main(String[] args) {
                BuscaminasControlador controlador = new BuscaminasControlador();
                controlador.iniciar();
      }
}
