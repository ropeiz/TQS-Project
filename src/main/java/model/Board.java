package model;

public final class Board {
    /** Ancho del tablero de juego. */
    private int width;

    /** Altura del tablero de juego. */
    private int height;

    /** Matriz que representa las celdas ocupadas y vacías del tablero. */
    private boolean[][] grid;

    /**
     * Constructor de la clase Board.
     *
     * Inicializa el tablero con el ancho y la altura especificados
     * y crea una matriz booleana para representar el estado del tablero.
     *
     * @param newWidth El ancho del tablero.
     * @param newHeight La altura del tablero.
     */
    public Board(final int newWidth, final int newHeight) {
        this.width = newWidth;
        this.height = newHeight;
        grid = new boolean[height][width]; // inicialización del tablero
    }

    /**
     * Devuelve el ancho del tablero.
     * @return El ancho del tablero.
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * Devuelve la altura del tablero.
     * @return La altura del tablero.
     */
    public int getHeight() {
        return this.height;
    }

    /**
     * Verifica si una celda está ocupada en las coordenadas especificadas.
     *
     * @param x La coordenada x de la celda a verificar.
     * @param y La coordenada y de la celda a verificar.
     * @return true si la celda está ocupada,
     * false si está libre o fuera de rango.
     */
    public boolean isCellOccupied(final int x, final int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            return false; // fuera de rango
        }
        return grid[y][x];
    }

    /**
     * Marca una celda como ocupada en las coordenadas especificadas.
     *
     * @param x La coordenada x de la celda a ocupar.
     * @param y La coordenada y de la celda a ocupar.
     */
    public void occupyCell(final int x, final int y) {
        if (y >= 0 && y < height && x >= 0 && x < width) {
            grid[y][x] = true;
        }
    }


    /** Método para marcar una celda como vacía.
     * @param x La posición X de la casilla
     * @param y La posición Y de la casilla
     * */
    public void clearCell(final int x, final int y) {
        if (!isOutOfBounds(x, y)) {
            grid[y][x] = false;
        }
    }

    /** @return Devuelve la cuadrícula completa (útil para la vista).*/
    public boolean[][] getGrid() {
        return grid;
    }

    /** @return Devuelve la cantidad de líneas completas.*/
    public int clearFullLines() {
        int linesCleared = 0;
        for (int y = 0; y < height; y++) {
            if (isLineFull(y)) {
                clearLine(y);
                linesCleared++;
                shiftLinesDown(y);
            }
        }
        return linesCleared; // Cantidad de líneas limpiadas
    }

    /**
     * Verifica si una pieza colisiona con los límites
     * del tablero o con otras piezas ocupadas.
     * Este método recorre la forma de la pieza
     * y verifica si alguna de sus celdas ocupa
     * una posición fuera de los límites del tablero
     * o colisiona con una celda ya ocupada.
     *
     * @param piece La pieza que se va a verificar para colisiones.
     * @return true si hay una colisión, false si no hay colisión.
     */
    public boolean checkCollision(final Piece piece) {
        for (int y = 0; y < piece.getShape().length; y++) {
            for (int x = 0; x < piece.getShape()[y].length; x++) {
                if (piece.getShape()[y][x]) {
                    int boardX = piece.getX() + x;
                    int boardY = piece.getY() + y;

                    // Comprueba colisión en los límites o con celdas ocupadas
                    if ((boardX < 0 || boardX >= width)
                    	|| (boardY < 0 || boardY >= height)
                    	|| grid[boardY][boardX]) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Bloquea la pieza actual en el tablero, es decir, la fija en su posición.
     * @param piece La pieza que se va a bloquear.
     */
    public void lockPiece(final Piece piece) {
        for (int y = 0; y < piece.getShape().length; y++) {
            for (int x = 0; x < piece.getShape()[y].length; x++) {
                if (piece.getShape()[y][x]) {
                    int boardX = piece.getX() + x;
                    int boardY = piece.getY() + y;
                    grid[boardY][boardX] = true; // Marca la celda como ocupada
                }
            }
        }
    }

    /**
     * @param y La posición Y de la línea que se comprueba.
     * @return Devuelve si una línea está completamente llena.
     * */
    private boolean isLineFull(final int y) {
        for (int x = 0; x < width; x++) {
            if (!grid[y][x]) { // Si alguna celda está vacía
                return false;
            }
        }
        return true;
    }

    /** Método auxiliar para limpiar una línea específica.
     * @param y La posición Y que se limpiará.
     * */
    public void clearLine(final int y) {
        for (int x = 0; x < width; x++) {
            grid[y][x] = false;
        }
    }

    /** Método auxiliar para desplazar las líneas hacia abajo.
     * @param startY La posición Y desde la que comienza
     * */
    private void shiftLinesDown(final int startY) {
        for (int y = startY; y > 0; y--) {
            System.arraycopy(grid[y - 1], 0, grid[y], 0, width);
        }
        //Limpiar la línea superior después de desplazarlas hacia abajo.
        for (int x = 0; x < width; x++) {
            grid[0][x] = false;
        }
    }

    /**
     * @param x La posición X a comprobar
     * @param y La posición Y a comprobar
     * @return Devuelve si una coordenada está fuera de los límites.
     * */
    private boolean isOutOfBounds(final int x, final int y) {
        return x < 0 || x >= width || y < 0 || y >= height;
    }

}
