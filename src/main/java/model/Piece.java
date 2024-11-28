package model;

import java.util.Random;

/**
 * Clase que representa una pieza del juego Tetris.
 */
public class Piece {
    /** Matriz booleana que representa la forma de la pieza. */
    private boolean[][] shape;

    /** Coordenada X de la posición actual de la pieza en el tablero. */
    private int x;

    /** Coordenada Y de la posición actual de la pieza en el tablero. */
    private int y;

    /**
     * Constructor de la clase Piece.
     *
     * Inicializa una nueva pieza con la forma dada,
     * y establece su posición inicial en (0, 0).
     *
     * @param newShape La forma de la pieza representada en matriz booleana.
     */
    public Piece(final boolean[][] newShape) {
        this.shape = newShape;
        this.x = 0;
        this.y = 0;
    }

    /**
     * Devuelve la forma de la pieza.
     *
     * @return La forma de la pieza representada como una matriz booleana.
     */
    public boolean[][] getShape() {
        return shape;
    }

    /**
     * Devuelve la coordenada x de la posición de la pieza.
     *
     * @return La coordenada x de la pieza.
     */
    public int getX() {
        return x;
    }

    /**
     * Devuelve la coordenada y de la posición de la pieza.
     *
     * @return La coordenada y de la pieza.
     */
    public int getY() {
        return y;
    }

    /**
     * Mueve la pieza a una nueva posición.
     *
     * Este método actualiza la posición de la pieza sumando dx y dy
     * a las coordenadas actuales de la pieza.
     *
     * @param dx El cambio en la coordenada x.
     * @param dy El cambio en la coordenada y.
     */
    public void move(final int dx, final int dy) {
        if (dy >= 0) {
        this.x += dx;
        this.y += dy;
        }
    }

    /** Rotación de la pieza en el sentido de las agujas del reloj.*/
    public void rotateClockwise() {
        int rows = shape.length;
        int cols = shape[0].length;
        boolean[][] rotatedShape = new boolean[cols][rows];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                rotatedShape[j][rows - 1 - i] = shape[i][j];
            }
        }
        shape = rotatedShape;
    }

    /** Rotación de la pieza en el sentido contrario a las agujas del reloj.*/
    public void rotateCounterClockwise() {
        int rows = shape.length;
        int cols = shape[0].length;
        boolean[][] rotatedShape = new boolean[cols][rows];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                rotatedShape[cols - 1 - j][i] = shape[i][j];
            }
        }
        shape = rotatedShape;
    }

    /**
     * Genera una pieza aleatoria.
     *
     * @return Una nueva pieza aleatoria.
     */
    public static Piece generateRandomPiece() {
        Random random = new Random();
        boolean[][][] shapes = {
            // Pieza cuadrada
            {{true, true}, {true, true}},
            // Pieza en forma de línea
            {{true, true, true, true}},
            // Pieza en forma de T
            {{false, true, false}, {true, true, true}},
            // Pieza en forma de L
            {{true, false}, {true, false}, {true, true}},
            // Pieza en forma de Z
            {{true, true, false}, {false, true, true}},
            // Pieza en forma de J
            {{false, true}, {false, true}, {true, true}},
            // Pieza en forma de S
            {{false, true, true}, {true, true, false}}
        };

        boolean[][] randomShape = shapes[random.nextInt(shapes.length)];
        return new Piece(randomShape);
    }

    /** @return Devuelve el ancho de la pieza
     *(para validaciones de colisión y límites).*/
    public int getWidth() {
        return shape[0].length;
    }

    /** @return Devuelve la altura de la pieza.*/
    public int getHeight() {
        return shape.length;
    }

    /** Coloca la pieza en una posición específica del tablero.
     * @param newX será la nueva posición X
     * @param newY será la nueva posición Y
     * */
    public void setPosition(final int newX, final int newY) {
        this.x = newX;
        this.y = newY;
    }
}
