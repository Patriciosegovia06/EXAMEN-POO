package modelo;

import java.io.Serializable;

public class Jugador implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nombre;
      private int victorias;
      private int derrotas;

    public Jugador(String nombre) {
              this.nombre    = nombre;
              this.victorias = 0;
              this.derrotas  = 0;
    }

    public String getNombre()  { return nombre; }
      public void setNombre(String nombre) { this.nombre = nombre; }
      public int getVictorias()  { return victorias; }
      public int getDerrotas()   { return derrotas; }

    public void registrarVictoria() { victorias++; }
      public void registrarDerrota()  { derrotas++; }

    @Override
      public String toString() {
                return String.format("Jugador: %s | Victorias: %d | Derrotas: %d",
                                                     nombre, victorias, derrotas);
      }
}
