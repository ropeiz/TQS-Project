package model;

public class Board {
    /** Ancho del tablero de juego. */
    private int width;

    /** Altura del tablero de juego. */
    private int height;

    /** Matriz que representa las celdas ocupadas y vacías del tablero. */
    private boolean[][] grid;

    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        grid = new boolean[height][width]; //board inicialization
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public int getHeight() {
        return this.height;
    }

    public boolean isCellOccupied(int x, int y) {
    	
        if (x < 0 || x >= width || y < 0 || y >= height) {
            return false; // fuera de rango
        }
        return grid[y][x];
    }

    public void occupyCell(int x, int y) {

        if (y >= 0 && y < height && x >= 0 && x < width) {
            grid[y][x] = true;
        }
    }
    
    // Método para marcar una celda como vacía
    public void clearCell(int x, int y) {
        if (!isOutOfBounds(x, y)) {
            grid[y][x] = false;
        }
    }

    // Método para obtener la cuadrícula completa (útil para la vista)
    public boolean[][] getGrid() {
        return grid;
    }

    // Método para limpiar las líneas completas del tablero
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
    
    public boolean checkCollision(Piece piece) {
        for (int y = 0; y < piece.getShape().length; y++) {
            for (int x = 0; x < piece.getShape()[y].length; x++) {
                if (piece.getShape()[y][x]) {
                    int boardX = piece.getX() + x;
                    int boardY = piece.getY() + y;

                    // Comprueba colisión en los límites o con celdas ocupadas
                    if ((boardX < 0 || boardX >= width) || 
                    	(boardY < 0 || boardY >= height) || 
                    	 grid[boardY][boardX]) {
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
    public void lockPiece(Piece piece) {
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

    // Verificar si una línea está completamente llena
    private boolean isLineFull(int y) {
        for (int x = 0; x < width; x++) {
            if (!grid[y][x]) { // Si alguna celda está vacía
                return false; 
            }
        }
        return true;
    }

    // Método auxiliar para limpiar una línea específica
    public void clearLine(int y) {
        for (int x = 0; x < width; x++) {
            grid[y][x] = false;
        }
    }

    // Método auxiliar para desplazar las líneas hacia abajo
    private void shiftLinesDown(int startY) {
        for (int y = startY; y > 0; y--) {
            System.arraycopy(grid[y - 1], 0, grid[y], 0, width);
        }
        // Limpiar la línea superior después de desplazarlas hacia abajo
        for (int x = 0; x < width; x++) {
            grid[0][x] = false;
        }
    }

    // Verificar si una coordenada está fuera de los límites
    private boolean isOutOfBounds(int x, int y) {
        return x < 0 || x >= width || y < 0 || y >= height;
    }
    
    

}
