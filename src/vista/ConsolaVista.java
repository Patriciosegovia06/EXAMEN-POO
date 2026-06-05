package vista;

import modelo.Casilla;
import modelo.Tablero;

import java.util.Scanner;

/**
 * Vista (MVC): gestiona SOLO entrada/salida en consola.
   * No contiene logica de negocio ni de flujo.
   */
public class ConsolaVista {

    private final Scanner scanner;

    public ConsolaVista() {
              scanner = new Scanner(System.in);
    }

    // ---- Menus ----

    public void mostrarBienvenida() {
              System.out.println("==========================================");
              System.out.println("       B U S C A M I N A S - POO");
              System.out.println("==========================================");
    }

    public void mostrarMenuPrincipal(boolean hayPartidaGuardada) {
              System.out.println("\n============== MENU ==============");
              System.out.println("  1. Nueva partida");
              System.out.println("  2. Cargar partida" +
                                                 (hayPartidaGuardada ? "" : " (no disponible)"));
              System.out.println("  3. Salir");
              System.out.print("Opcion: ");
    }

    public void mostrarMenuAcciones() {
              System.out.println("\n  [A5]    -> Descubrir casilla");
              System.out.println("  [M A5]  -> Marcar/desmarcar bandera");
              System.out.println("  [G]     -> Guardar partida");
              System.out.println("  [S]     -> Salir al menu principal");
              System.out.print("  Ingrese accion: ");
    }

    // ---- Tablero ----

    /**
     * Muestra el tablero con encabezado de columnas (1-10) y filas (A-J).
       * Formato exacto requerido:
     *     1  2  3  4  5  6  7  8  9 10
       * A   .  .  .  .  .  .  .  .  .  .
       */
    public void mostrarTablero(Tablero tablero, int minasRestantes) {
              System.out.println("\nMinas restantes: " + minasRestantes);

          // Encabezado de columnas
          System.out.print("    ");
              for (int c = 1; c <= Tablero.TAMANIO; c++) {
                            System.out.printf("%3d", c);
              }
              System.out.println();
              System.out.println("   " + "---".repeat(Tablero.TAMANIO + 1));

          // Filas
          for (int f = 0; f < Tablero.TAMANIO; f++) {
                        System.out.printf("%2c  ", (char) ('A' + f));
                        for (int c = 0; c < Tablero.TAMANIO; c++) {
                                          System.out.printf("%2s ", tablero.getCasilla(f, c).toString());
                        }
                        System.out.println();
          }
    }

    // ---- Entrada del usuario ----

    /** Solicita y devuelve el nombre ingresado por el usuario. */
    public String pedirNombre() {
              System.out.print("Ingresa tu nombre: ");
              return scanner.nextLine().trim();
    }

    /** Lee una linea de texto del usuario (accion de juego). */
    public String leerEntrada() {
              return scanner.nextLine().trim().toUpperCase();
    }

    /** Lee una opcion del menu principal. */
    public String leerOpcionMenu() {
              return scanner.nextLine().trim();
    }

    // ---- Mensajes ----

    public void mostrarMensaje(String mensaje) {
              System.out.println(mensaje);
    }

    public void mostrarError(String error) {
              System.out.println("[!] " + error);
    }

    public void mostrarVictoria(String nombre) {
              System.out.println("\n*** FELICIDADES, " + nombre + "! GANASTE! ***");
    }

    public void mostrarDerrota(String nombre) {
              System.out.println("\n*** BOOM! " + nombre + ", pisaste una mina. PERDISTE! ***");
    }

    public void mostrarPartidaGuardada() {
              System.out.println("[OK] Partida guardada correctamente.");
    }

    public void mostrarPartidaCargada() {
              System.out.println("[OK] Partida cargada correctamente.");
    }

    public void mostrarAyuda() {
              System.out.println("\n--- INSTRUCCIONES ---");
              System.out.println("  Descubrir casilla : A5");
              System.out.println("  Marcar/desmarcar  : M A5");
              System.out.println("  Guardar partida   : G");
              System.out.println("  Salir al menu     : S");
              System.out.println("---------------------");
    }

    public void cerrar() {
              scanner.close();
    }
}
