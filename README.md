# Buscaminas en Consola — POO UPS
Proyecto final de **Programación Orientada a Objetos** — Universidad Politécnica Salesiana.
## Descripción

Implementación en consola del clásico juego **Buscaminas**:
- Tablero 10×10 con 10 minas colocadas aleatoriamente
- Coordenadas tipo `A5` (letra fila + número columna)
- Revelado en cascada de casillas vacías
- Sistema de marcado con banderas
- Guardado y carga de partida (serialización binaria)
- Patrón arquitectónico **MVC**
## Estructura del proyecto

```
buscaminas/
├── src/
│   ├── Main.java
│   ├── model/
│   │   ├── Casilla.java
│   │   ├── Tablero.java
│   │   ├── Jugador.java
│   │   ├── EstadoCasilla.java
│   │   ├── EstadoJuego.java
│   │   └── PartidaGuardada.java
│   ├── view/
│   │   └── Vista.java
│   ├── controller/
│   │   └── Controlador.java
│   ├── exceptions/
│   │   ├── BuscaminasException.java
│   │   ├── CasillaYaDescubiertaException.java
│   │   └── CoordenadaInvalidaException.java
│   └── persistence/
│       └── GestorArchivos.java
└── test/
    └── model/
        ├── CasillaTest.java
        ├── TableroTest.java
        └── JugadorTest.java
```
## Cómo compilar y ejecutar

### Requisitos
- Java 11 o superior
- (Opcional) JUnit 5 para pruebas unitarias

### Compilar
```bash
cd buscaminas
javac -d out -sourcepath src src/Main.java
```### Ejecutar
```bash
java -cp out Main
```

### Compilar y correr tests (JUnit 5)
```bash
javac -cp junit-platform-console-standalone.jar -d out src/**/*.java test/**/*.java
java -jar junit-platform-console-standalone.jar --class-path out --scan-class-path
```

## Cómo jugar

| Acción              | Comando      | Ejemplo  |
|---------------------|--------------|----------|
| Descubrir casilla   | `LetraNum`   | `A5`     |
| Marcar/desmarcar    | `M LetraNum` | `M B3`   |
| Guardar partida     | `G`          |          |
| Salir al menú       | `S`          |          |

### Símbolos del tablero
| Símbolo | Significado                          |
|---------|--------------------------------------|
| `·`     | Casilla cubierta (no revelada)       |
| `V`     | Casilla vacía sin minas alrededor    |
| `1-8`   | Número de minas en casillas vecinas  |
| `F`     | Bandera (casilla marcada por jugador)|
| `X`     | Mina (al perder se revelan todas)    |

## Ejemplo de tablero

```
  Minas restantes: 9
      1  2  3  4  5  6  7  8  9 10
    -------------------------------
 A |  V  V  1  ·  ·  ·  ·  ·  ·  ·
 B |  V  V  1  ·  ·  ·  ·  ·  ·  ·
 C |  1  1  2  ·  ·  ·  ·  ·  ·  ·
 D |  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·
```
