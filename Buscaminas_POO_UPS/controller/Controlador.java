package controller;

import exceptions.BuscaminasException;
import exceptions.CasillaYaDescubiertaException;
import exceptions.CoordenadaInvalidaException;
import model.*;
import persistence.GestorArchivos;
import view.Vista;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Controlador (MVC): coordina la interacción entre Vista y Modelo.
 * Maneja el flujo del juego, entradas del usuario y excepciones.
 */
public class Controlador {

    private Tablero tablero;
    private Jugador jugador;
    private Vista vista;
    private GestorArchivos gestorArchivos;
    private EstadoJuego estadoJuego;
    private Scanner scanner;

    public Controlador() {
        this.vista = new Vista();
        this.gestorArchivos = new GestorArchivos();
        this.scanner = new Scanner(System.in);
    }

    // ──── Flujo principal ────

    /**
     * Inicia el bucle del menú principal.
     */
    public void iniciar() {
        vista.mostrarBienvenida();
        vista.pedirNombre();
        String nombre = scanner.nextLine().trim();
        jugador = new Jugador(nombre.isEmpty() ? "Jugador" : nombre);

        boolean continuar = true;
        while (continuar) {
            vista.mostrarMenuPrincipal(gestorArchivos.existeGuardado());
            String opcion = scanner.nextLine().trim();
            switch (opcion) {
                case "1":
                    nuevaPartida();
                    break;
                case "2":
                    if (gestorArchivos.existeGuardado()) cargarPartida();
                    else vista.mostrarError("No hay partida guardada.");
                    break;
                case "3":
                    continuar = false;
                    vista.mostrarMensaje("¡Hasta luego, " + jugador.getNombre() + "!");
                    break;
                default:
                    vista.mostrarError("Opción inválida.");
            }
        }
        scanner.close();
    }

    // ──── Gestión de partida ────

    private void nuevaPartida() {
        tablero = new Tablero();
        estadoJuego = EstadoJuego.EN_CURSO;
        gestorArchivos.eliminarGuardado();
        bucleDeJuego();
    }

    private void cargarPartida() {
        try {
            PartidaGuardada guardada = gestorArchivos.cargarPartida();
            if (guardada != null) {
                tablero = guardada.getTablero();
                jugador = guardada.getJugador();
                estadoJuego = guardada.getEstadoJuego();
                vista.mostrarPartidaCargada();
                bucleDeJuego();
            }
        } catch (IOException | ClassNotFoundException e) {
            vista.mostrarError("No se pudo cargar la partida: " + e.getMessage());
        }
    }

    // ──── Bucle de juego ────

    /**
     * Bucle principal: muestra tablero, pide entrada, procesa acción.
     */
    private void bucleDeJuego() {
        while (estadoJuego == EstadoJuego.EN_CURSO) {
            vista.mostrarTablero(tablero, tablero.getMinasRestantes());
            vista.mostrarMenuAccion();
            String entrada = scanner.nextLine().trim().toUpperCase();

            try {
                procesarEntrada(entrada);
            } catch (BuscaminasException e) {
                vista.mostrarError(e.getMessage());
            }
        }

        // Mostrar resultado final
        vista.mostrarTablero(tablero, tablero.getMinasRestantes());
        if (estadoJuego == EstadoJuego.VICTORIA) {
            vista.mostrarVictoria(jugador.getNombre());
            jugador.registrarVictoria();
        } else {
            tablero.revelarTodasLasMinas();
            vista.mostrarTablero(tablero, 0);
            vista.mostrarDerrota();
            jugador.registrarDerrota();
        }
        vista.mostrarMensaje(jugador.toString());
        gestorArchivos.eliminarGuardado();
    }

    // ──── Procesamiento de entrada ────

    /**
     * Analiza y ejecuta el comando del usuario.
     *
     * @throws BuscaminasException Si la coordenada es inválida o la casilla ya fue descubierta.
     */
    private void procesarEntrada(String entrada) throws BuscaminasException {
        if (entrada.equals("G")) {
            guardarPartida();
            return;
        }
        if (entrada.equals("S")) {
            estadoJuego = EstadoJuego.DERROTA; // Salir sin guardar automáticamente
            vista.mostrarMensaje("Saliendo al menú...");
            return;
        }

        boolean esMarca = entrada.startsWith("M ");
        String coord = esMarca ? entrada.substring(2).trim() : entrada;

        int[] pos = parsearCoordenada(coord);
        int fila = pos[0];
        int col  = pos[1];

        if (esMarca) {
            tablero.alternarMarca(fila, col);
        } else {
            if (tablero.getCasilla(fila, col).estaDescubierta()) {
                throw new CasillaYaDescubiertaException(coord);
            }
            boolean mina = tablero.descubrir(fila, col);
            if (mina) {
                estadoJuego = EstadoJuego.DERROTA;
            } else if (tablero.estaCompleto()) {
                estadoJuego = EstadoJuego.VICTORIA;
            }
        }
    }

    /**
     * Convierte una cadena de coordenada (ej: "A5") en índices [fila, col].
     *
     * @throws CoordenadaInvalidaException Si el formato o rango son inválidos.
     */
    private int[] parsearCoordenada(String coord) throws CoordenadaInvalidaException {
        if (coord == null || coord.length() < 2) {
            throw new CoordenadaInvalidaException(coord);
        }
        char letraChar = coord.charAt(0);
        int fila = Tablero.letraAFila(letraChar);
        if (fila < 0) throw new CoordenadaInvalidaException(coord);

        try {
            int numero = Integer.parseInt(coord.substring(1));
            int col = Tablero.numeroAColumna(numero);
            if (col < 0 || col >= Tablero.COLUMNAS) throw new CoordenadaInvalidaException(coord);
            return new int[]{fila, col};
        } catch (NumberFormatException e) {
            throw new CoordenadaInvalidaException(coord);
        }
    }

    // ──── Persistencia ────

    private void guardarPartida() {
        try {
            PartidaGuardada guardada = new PartidaGuardada(tablero, jugador, estadoJuego);
            gestorArchivos.guardarPartida(guardada);
            vista.mostrarPartidaGuardada();
        } catch (IOException e) {
            vista.mostrarError("No se pudo guardar: " + e.getMessage());
        }
    }
}
