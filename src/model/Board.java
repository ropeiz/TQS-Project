package model;

public class Board {
    private int width;
    private int height;
    private boolean[][] grid;

    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        grid = new boolean[height][width]; //board inicialization
    }

    public boolean isCellOccupied(int x, int y) {
    	
        if (x < 0 || x >= width || y < 0 || y >= height) {
            return false; // out of range
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
        return linesCleared; // Retorna la cantidad de líneas limpiadas para el sistema de puntuación
    }

    // Método auxiliar para verificar si una línea está completamente llena
    private boolean isLineFull(int y) {
        for (int x = 0; x < width; x++) {
            if (!grid[y][x]) {
                return false; // Si alguna celda está vacía, la línea no está llena
            }
        }
        return true;
    }

    // Método auxiliar para limpiar una línea específica
    private void clearLine(int y) {
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

    // Método auxiliar para verificar si una coordenada está fuera de los límites
    private boolean isOutOfBounds(int x, int y) {
        return x < 0 || x >= width || y < 0 || y >= height;
    }

}
