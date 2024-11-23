package model;

import java.util.Arrays;

/**
 * Representa el tablero de Tetris.
 */
public class Board {
	/** Matriz que representa las celdas ocupadas y vacías del tablero. */
    private boolean[][] grid;

    /** Ancho del tablero de juego. */
    private int width;

    /** Altura del tablero de juego. */
    private int height;

    /**
     * Constructor de la clase Board.
     *
     * Inicializa el tablero con el ancho y la altura especificados y crea una
     * matriz booleana para representar el estado del tablero.
     *
     * @param newWidth  El ancho del tablero.
     * @param newHeight La altura del tablero.
     */
    public Board(final int newWidth, final int newHeight) {
        this.width = newWidth;
        this.height = newHeight;
        this.grid = new boolean[height][width];
    }

    /**
    * Devuelve el ancho del tablero.
    *
    * @return El ancho del tablero.
    */
    public int getWidth() {
        return this.width;
    }

    /**
     * Devuelve la altura del tablero.
     *
     * @return La altura del tablero.
     */
    public int getHeight() {
        return this.height;
    }

    /**
     * Verifica si una celda está ocupada.
     * @param x Coordenada x de la celda.
     * @param y Coordenada y de la celda.
     * @return true si la celda está ocupada; false de lo contrario.
     */
    public boolean isCellOccupied(final int x, final int y) {
        return grid[y][x];
    }

    /**
     * Verifica si una pieza colisiona con
     * los límites del tablero o con otras
     * piezas ocupadas. Este método recorre
     * la forma de la pieza y verifica si
     * alguna de sus celdas ocupa una posición
     * fuera de los límites del tablero o
     * colisiona con una celda ya ocupada.
     *
     * @param piece La pieza que se va a verificar para colisiones.
     * @return true si hay una colisión, false si no hay colisión.
     */
    public boolean checkCollision(final Piece piece) {
        boolean[][] shape = piece.getShape();
        int pieceX = piece.getX();
        int pieceY = piece.getY();

        for (int row = 0; row < shape.length; row++) {
            for (int col = 0; col < shape[row].length; col++) {
                if (shape[row][col]) {
                    int boardX = pieceX + col;
                    int boardY = pieceY + row;

                    if (boardX < 0 || boardX >= width 
                    	|| boardY < 0 || boardY >= height) {
                        return true; // Fuera de límites
                    }
                    if (boardY >= 0 && grid[boardY][boardX]) {
                        return true; // Colisión con celdas ocupadas
                    }
                }
            }
        }
        return false;
    }

    /**
    * Bloquea la pieza actual en el tablero, es decir, la fija en su posición.
    *
    * @param piece La pieza que se va a bloquear.
    */
    public void lockPiece(final Piece piece) {
        boolean[][] shape = piece.getShape();
        int pieceX = piece.getX();
        int pieceY = piece.getY();

        for (int row = 0; row < shape.length; row++) {
            for (int col = 0; col < shape[row].length; col++) {
                if (shape[row][col]) {
                    grid[pieceY + row][pieceX + col] = true;
                }
            }
        }
    }

    /** Elimina las líneas completas. */
    public void clearFullLines() {
        grid = Arrays.stream(grid)
            .filter(row -> !isFull(row))
            .toArray(boolean[][]::new);

        // Añadir nuevas filas vacías en la parte superior
        while (grid.length < height) {
            grid = prependRow(grid, new boolean[width]);
        }
    }


    /**
    Método auxiliar para limpiar una línea específica.
    @param y La posición Y que se limpiará.*/
    public void clearLine(final int y) {
        for (int x = 0; x < width; x++) {
            grid[y][x] = false; }
        }

    /**
    * @param row La línea que se comprueba.
    * @return Devuelve si una línea está completamente llena.
    */
    private boolean isFull(final boolean[] row) {
        for (boolean cell : row) {
            if (!cell) {
                return false;
            }
        }
        return true;
    }

    /**
    * Crea un nuevo tablero que contiene una nueva fila al inicio.
    * @param grid tablero original.
    * @param newRow La nueva fila que se agrega al tablero.
    * @return Un nuevo tablero con newRow como la primera fila.
    */
    private boolean[][] prependRow(final boolean[][] grid, final boolean[] newRow) {
        boolean[][] newGrid = new boolean[grid.length + 1][];
        newGrid[0] = newRow;
        System.arraycopy(grid, 0, newGrid, 1, grid.length);
        return newGrid;
    }

    /**
    * Marca una celda como ocupada en las coordenadas especificadas.*
    * @param x La coordenada x de la celda a ocupar.
    * @param y La coordenada y de la celda a ocupar.*/
    public void occupyCell(final int x, final int y) {
        if (y >= 0 && y < height && x >= 0 && x < width) {
            grid[y][x] = true;}
        }

    /**
     * Renderiza el tablero actual junto con la posición de la pieza dada.
     * La pieza solo se renderiza si está dentro de los límites del tablero.
     * @param piece La pieza que se desea renderizar sobre el tablero.
     * @return Un arreglo bidimensional de caracteres que representa 
     * el estado actual del tablero con la pieza añadida.
     */
    public char[][] renderWithPiece(final Piece piece) {
        char[][] rendered = new char[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                rendered[y][x] = grid[y][x] ? 'X' : ' ';
            }
        }

        boolean[][] shape = piece.getShape();
        int pieceX = piece.getX();
        int pieceY = piece.getY();

        for (int row = 0; row < shape.length; row++) {
            for (int col = 0; col < shape[row].length; col++) {
                if (shape[row][col]) {
                    int renderX = pieceX + col;
                    int renderY = pieceY + row;

                    if (renderX >= 0 && renderX < width 
                    && renderY >= 0 && renderY < height) {
                        rendered[renderY][renderX] = 'O'; 
                    }
                }
            }
        }

        return rendered;
    }
}
