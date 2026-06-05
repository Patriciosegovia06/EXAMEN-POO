package modelo;

import java.io.Serializable;

/**
 * Representa una casilla del tablero del Buscaminas.
 * Encapsula si tiene mina, cuántas minas adyacentes tiene y su estado actual.
 */
public class Casilla implements Serializable {

    private static final long serialVersionUID = 1L;

    private boolean tieneMina;
    private int minasAdyacentes;
    private EstadoCasilla estado;

    // Constructor
    public Casilla() {
        this.tieneMina = false;
        this.minasAdyacentes = 0;
        this.estado = EstadoCasilla.CUBIERTA;
    }

    // ──── Getters y Setters ────

    public boolean isTieneMina() {
        return tieneMina;
    }

    public void setTieneMina(boolean tieneMina) {
        this.tieneMina = tieneMina;
    }

    public int getMinasAdyacentes() {
        return minasAdyacentes;
    }

    public void setMinasAdyacentes(int minasAdyacentes) {
        this.minasAdyacentes = minasAdyacentes;
    }

    public EstadoCasilla getEstado() {
        return estado;
    }

    public void setEstado(EstadoCasilla estado) {
        this.estado = estado;
    }

    // ──── Métodos de consulta ────

    public boolean estaDescubierta() {
        return estado == EstadoCasilla.DESCUBIERTA;
    }

    public boolean estaMarcada() {
        return estado == EstadoCasilla.MARCADA;
    }

    public boolean estaCubierta() {
        return estado == EstadoCasilla.CUBIERTA;
    }

    /**
     * Devuelve el carácter que representa la casilla para mostrar al jugador.
     */
    public char representacionVisible() {
        switch (estado) {
            case CUBIERTA:    return '·';
            case MARCADA:     return 'F';
            case DESCUBIERTA:
                if (tieneMina) return 'X';
                if (minasAdyacentes == 0) return 'V';
                return (char) ('0' + minasAdyacentes);
            default:          return '?';
        }
    }
}
