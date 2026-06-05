package persistence;

import model.PartidaGuardada;

import java.io.*;

/**
 * Gestiona la persistencia del estado del juego mediante serialización binaria.
 * Aplica principio DRY: un único punto de lectura/escritura de archivos.
 */
public class GestorArchivos {

    private static final String ARCHIVO_GUARDADO = "partida_guardada.dat";

    /**
     * Guarda el estado de la partida en un archivo binario.
     *
     * @param partida El objeto con el estado completo a guardar.
     * @throws IOException Si ocurre un error de escritura.
     */
    public void guardarPartida(PartidaGuardada partida) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(ARCHIVO_GUARDADO))) {
            oos.writeObject(partida);
        }
    }

    /**
     * Carga el estado de la partida desde el archivo binario.
     *
     * @return La partida guardada, o null si no existe archivo.
     * @throws IOException            Si ocurre un error de lectura.
     * @throws ClassNotFoundException Si el archivo no corresponde al tipo esperado.
     */
    public PartidaGuardada cargarPartida() throws IOException, ClassNotFoundException {
        File archivo = new File(ARCHIVO_GUARDADO);
        if (!archivo.exists()) return null;

        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(archivo))) {
            return (PartidaGuardada) ois.readObject();
        }
    }

    /**
     * Elimina el archivo de partida guardada.
     */
    public void eliminarGuardado() {
        File archivo = new File(ARCHIVO_GUARDADO);
        if (archivo.exists()) archivo.delete();
    }

    /**
     * Comprueba si existe una partida guardada.
     */
    public boolean existeGuardado() {
        return new File(ARCHIVO_GUARDADO).exists();
    }
}
