package view;

import model.Casilla;
import model.Tablero;

/**
 * Vista (MVC): responsable de toda la presentación en consola del juego.
 * No contiene lógica de negocio.
 */
public class Vista {

    private static final String LETRAS = "ABCDEFGHIJ";

    // ──── Tablero ────

    /**
     * Muestra el tablero actual al jugador.
     *
     * @param tablero       El tablero del juego.
     * @param minasRestantes Número de minas sin marcar.
     */
    public void mostrarTablero(Tablero tablero, int minasRestantes) {
        System.out.println("\n  Minas restantes: " + minasRestantes);
        System.out.print("    ");
        for (int c = 1; c <= Tablero.COLUMNAS; c++) {
            System.out.printf("%3d", c);
        }
        System.out.println();
        System.out.print("    ");
        System.out.println("---".repeat(Tablero.COLUMNAS));

        for (int f = 0; f < Tablero.FILAS; f++) {
            System.out.printf(" %c |", LETRAS.charAt(f));
            for (int c = 0; c < Tablero.COLUMNAS; c++) {
                Casilla casilla = tablero.getCasilla(f, c);
                System.out.printf("  %c", casilla.representacionVisible());
            }
            System.out.println();
        }
        System.out.println();
    }

    // ──── Mensajes del juego ────

    public void mostrarBienvenida() {
        System.out.println("╔════════════════════════════╗");
        System.out.println("║      BUSCAMINAS - UPS      ║");
        System.out.println("╚════════════════════════════╝");
    }

    public void mostrarMenuPrincipal(boolean existeGuardado) {
        System.out.println("\n1. Nueva partida");
        if (existeGuardado) System.out.println("2. Continuar partida guardada");
        System.out.println("3. Salir");
        System.out.print("Opción: ");
    }

    public void mostrarMenuAccion() {
        System.out.println("Comandos: [coordenada] para descubrir (ej: A5)");
        System.out.println("          [M coordenada] para marcar/desmarcar (ej: M B3)");
        System.out.println("          [G] para guardar  [S] para salir al menú");
        System.out.print("Tu movimiento: ");
    }

    public void mostrarVictoria(String nombreJugador) {
        System.out.println("\n🎉 ¡Felicitaciones " + nombreJugador + "! ¡Ganaste el Buscaminas!");
    }

    public void mostrarDerrota() {
        System.out.println("\n💥 ¡BOOM! Pisaste una mina. ¡Game over!");
    }

    public void mostrarPartidaGuardada() {
        System.out.println("✔ Partida guardada correctamente.");
    }

    public void mostrarPartidaCargada() {
        System.out.println("✔ Partida cargada correctamente.");
    }

    public void mostrarError(String mensaje) {
        System.out.println("⚠ Error: " + mensaje);
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    public void pedirNombre() {
        System.out.print("Ingresa tu nombre: ");
    }
}
