package controlador;

import excepciones.CasillaYaDescubiertaException;
import excepciones.CoordenadaInvalidaException;
import modelo.Juego;
import modelo.Tablero;
import util.PersistenciaArchivo;
import vista.ConsolaVista;

import java.io.IOException;
import java.util.InputMismatchException;

/**
 * Controlador (MVC): orquesta la interaccion entre Vista y Modelo.
   * Es el unico punto que conoce tanto el Modelo como la Vista.
   */
public class BuscaminasControlador {

    private Juego        juego;
      private ConsolaVista vista;
      private boolean      primeraJugada;

    public BuscaminasControlador() {
              this.vista = new ConsolaVista();
    }

    // ---- Flujo principal ----

    /** Punto de entrada: muestra el menu principal y gestiona la sesion. */
    public void iniciar() {
              vista.mostrarBienvenida();
              String nombre = vista.pedirNombre();
              if (nombre.isEmpty()) nombre = "Jugador";

          boolean continuar = true;
              while (continuar) {
                            vista.mostrarMenuPrincipal(PersistenciaArchivo.existePartidaGuardada());
                            String opcion = vista.leerOpcionMenu();

                  switch (opcion) {
                    case "1":
                                          nuevaPartida(nombre);
                                          break;
                    case "2":
                                          cargarPartida();
                                          break;
                    case "3":
                                          continuar = false;
                                          vista.mostrarMensaje("Hasta luego, " + nombre + "!");
                                          break;
                    default:
                                          vista.mostrarError("Opcion invalida. Elige 1, 2 o 3.");
                  }
              }
              vista.cerrar();
    }

    // ---- Gestion de partidas ----

    private void nuevaPartida(String nombre) {
              juego         = new Juego(nombre);
              primeraJugada = true;
              bucleDeJuego();
    }

    private void cargarPartida() {
              if (!PersistenciaArchivo.existePartidaGuardada()) {
                            vista.mostrarError("No hay partida guardada.");
                            return;
              }
              try {
                            juego         = PersistenciaArchivo.cargarJuego();
                            primeraJugada = false;
                            vista.mostrarPartidaCargada();
                            bucleDeJuego();
              } catch (IOException | ClassNotFoundException e) {
                            vista.mostrarError("No se pudo cargar la partida: " + e.getMessage());
              }
    }

    // ---- Bucle de juego ----

    /**
     * Ciclo principal: muestra el tablero, solicita accion, la procesa.
       * Termina cuando el juego detecta victoria o derrota.
       */
    private void bucleDeJuego() {
              Tablero tablero = juego.getTablero();

          while (!tablero.isJuegoTerminado()) {
                        vista.mostrarTablero(tablero, contarMinasRestantes(tablero));
                        vista.mostrarMenuAcciones();

                  String entrada = vista.leerEntrada();

                  try {
                                    boolean salir = procesarEntrada(entrada);
                                    if (salir) return;
                  } catch (CasillaYaDescubiertaException | CoordenadaInvalidaException e) {
                                    vista.mostrarError(e.getMessage());
                  } catch (InputMismatchException e) {
                                    vista.mostrarError("Formato invalido. Usa LetraNumero (ej: A5).");
                  } catch (ArrayIndexOutOfBoundsException e) {
                                    vista.mostrarError("Coordenada fuera del tablero.");
                  }
          }

          mostrarResultadoFinal();
    }

    // ---- Procesamiento de entrada ----

    /**
     * Interpreta el comando e invoca la accion correspondiente en el modelo.
       * @return true si el usuario eligio salir al menu.
       */
    private boolean procesarEntrada(String entrada)
              throws CasillaYaDescubiertaException, CoordenadaInvalidaException {

          if (entrada.equals("G")) {
                        guardarPartida();
                        return false;
          }
                        if (entrada.equals("S")) {
                                      vista.mostrarMensaje("Regresando al menu principal...");
                                      return true;
                        }
                        if (entrada.equals("?") || entrada.equals("H")) {
                                      vista.mostrarAyuda();
                                      return false;
                        }

          boolean esBandera = entrada.startsWith("M ");
                        String  coordStr  = esBandera ? entrada.substring(2).trim() : entrada;

          int[] coord = parsearCoordenada(coordStr);
                        int fila = coord[0], col = coord[1];

          if (esBandera) {
                        juego.getTablero().marcarCasilla(fila, col);
          } else {
                        if (primeraJugada) {
                                          juego.getTablero().inicializarConMinas(fila, col);
                                          primeraJugada = false;
                        }
                        juego.getTablero().descubrirCasilla(fila, col);
          }
                        return false;
              }

    // ---- Resultado final ----

    private void mostrarResultadoFinal() {
              Tablero tablero = juego.getTablero();
              if (tablero.isVictoria()) {
                            vista.mostrarTablero(tablero, 0);
                            vista.mostrarVictoria(juego.getJugador().getNombre());
                            juego.getJugador().registrarVictoria();
              } else {
                            tablero.revelarTodasLasMinas();
                            vista.mostrarTablero(tablero, 0);
                            vista.mostrarDerrota(juego.getJugador().getNombre());
                            juego.getJugador().registrarDerrota();
              }
              vista.mostrarMensaje(juego.getJugador().toString());
    }

    // ---- Guardar partida ----

    private void guardarPartida() {
              try {
                            PersistenciaArchivo.guardarJuego(juego);
                            vista.mostrarPartidaGuardada();
              } catch (IOException e) {
                            vista.mostrarError("No se pudo guardar: " + e.getMessage());
              }
    }

    // ---- Utilidades ----

    /**
     * Convierte "A5" en {0, 4}. Lanza excepcion si el formato o rango es invalido.
       */
      private int[] parsearCoordenada(String entrada) throws CoordenadaInvalidaException {
          if (entrada == null || entrada.length() < 2)
                        throw new CoordenadaInvalidaException(entrada);

        char letraChar = entrada.charAt(0);
          if (letraChar < 'A' || letraChar > 'Z')
                        throw new CoordenadaInvalidaException(entrada);

        int fila = letraChar - 'A';
          int col;
          try {
                        col = Integer.parseInt(entrada.substring(1)) - 1;
          } catch (NumberFormatException e) {
                        throw new CoordenadaInvalidaException(entrada);
          }

        if (fila < 0 || fila >= Tablero.TAMANIO || col < 0 || col >= Tablero.TAMANIO)
                      throw new CoordenadaInvalidaException(entrada);

        return new int[]{fila, col};
}

    /** Cuenta cuantas celdas sin minar estan marcadas para mostrar minas restantes. */
          private int contarMinasRestantes(Tablero tablero) {
              int marcadas = 0;
              for (int i = 0; i < Tablero.TAMANIO; i++)
                            for (int j = 0; j < Tablero.TAMANIO; j++)
                                              if (tablero.getCasilla(i, j).isMarcada()) marcadas++;
              return Tablero.NUM_MINAS - marcadas;
    }
}
