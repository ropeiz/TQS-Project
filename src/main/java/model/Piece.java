package model;

import java.util.Random;

public class Piece {
    private boolean[][] shape; // shape representation
    private int x;
    private int y;

    public Piece(boolean[][] shape) {
        this.shape = shape;
        this.x = 0;
        this.y = 0;
    }

    public boolean[][] getShape() {
        return shape;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void move(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }
    // Rotación de la pieza en el sentido de las agujas del reloj
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

    // Rotación de la pieza en el sentido contrario a las agujas del reloj
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

    // Devuelve el ancho de la pieza (para validaciones de colisión y límites)
    public int getWidth() {
        return shape[0].length;
    }

    // Devuelve la altura de la pieza
    public int getHeight() {
        return shape.length;
    }

    // Coloca la pieza en una posición específica del tablero
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
}