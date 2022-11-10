package com.example.logic;

import com.example.interfaces.IEngine;
import com.example.interfaces.IFont;
import com.example.interfaces.IGraphics;

import java.util.Random;
import java.util.Vector;

public class Board {
    private int xSize, ySize;
    private int xZeroCord, yZeroCord;
    private float cellSide, cellSpacing;
    IFont font;
    Cell cells[][];

    Cell getCell(int x, int y) {
        return cells[x][y];
    }

    Vector<Vector<Integer>> xValues;
    Vector<Vector<Integer>> yValues;

    Board(int x, int y) {
        // Nº de celdas en x
        xSize = x;
        // Nº de celdas en y
        ySize = y;
        // Array que guarda las celdas del tablero
        cells = new Cell[xSize][ySize];
        // Vector para guardar las celdas consecutivas en cada columna
        xValues = new Vector<Vector<Integer>>(xSize);
        for (int i = 0; i < xSize; ++i)
            xValues.add(new Vector<Integer>(ySize / 2 + ySize % 2));
        // Vector para guardar las celdas consecutivas en cada fila
        yValues = new Vector<Vector<Integer>>(ySize);
        for (int j = 0; j < ySize; ++j)
            yValues.add(new Vector<Integer>(xSize / 2 + xSize % 2));
        createRandomBoard();
    }

    void init(IEngine e, IFont f) {
        // Coordenadas del canvas a partir de las cuales se dibuja el tablero
        xZeroCord = (int) e.getGraphics().getOriginalWidth() / 5;
        yZeroCord = (int) e.getGraphics().getOriginalHeight() / 3;
        int xSizeBoard = (int) e.getGraphics().getOriginalWidth() - xZeroCord;
        int ySizeBoard = (int) e.getGraphics().getOriginalHeight() - yZeroCord;
        int cellSizeX = xSizeBoard / xSize;
        int cellSizeY = ySizeBoard / ySize;
        // Tamaño de los laterales de las celdas
        cellSide = Math.min(cellSizeX, cellSizeY);
        // Tamaño del espacio entre celdas
        cellSpacing = Math.min((xSizeBoard - cellSide * xSize) / xSize, ((int) e.getGraphics().getOriginalHeight() - yZeroCord - cellSide * ySize) / ySize);
        cellSide -= cellSpacing;
        font = f;
    }

    int getxSize() {
        return xSize;
    }

    int getySize() {
        return ySize;
    }

    void createRandomBoard() {
        // Determina para cada celda si forma parte de la solución
        boolean good = false;
        Random rd = new Random();
        // Contador de celdas consecutivas en la columna
        int xConsecutives = 0;
        for (int i = 0; i < xSize; i++) {
            for (int j = 0; j < ySize; j++) {
                // Asignación de un valor aleatorio a good
                good = rd.nextBoolean();
                cells[i][j] = new Cell(i, j, good);
                // Si es buena se aumenta el contador
                if (good) {
                    xConsecutives++;
                }
                // Si no se guarda el valor del contador y se reinicia
                else if (xConsecutives > 0) {
                    xValues.get(i).add(xConsecutives);
                    xConsecutives = 0;
                }
            }
            // Al llegar al final de la columna se guarda el contador si es mayor de 0 y se reinicia
            if (xConsecutives > 0) {
                xValues.get(i).add(xConsecutives);
                xConsecutives = 0;
            }
        }

        // Contador para guardar las celdas consecutivas en cada columnna
        int yConsecutives = 0;
        for (int j = 0; j < ySize; j++) {
            for (int i = 0; i < xSize; i++) {
                if (cells[i][j].isGood) {
                    yConsecutives++;
                } else if (yConsecutives > 0) {
                    yValues.get(j).add(yConsecutives);
                    yConsecutives = 0;
                }
            }
            if (yConsecutives > 0) {
                yValues.get(j).add(yConsecutives);
                yConsecutives = 0;
            }
        }
    }

    int[] checkBoard() {
        int a[] = new int[2];
        // Contadores para la cantidad de celdas erróneas y la cantidad que no está en la solución
        int wrongCount = 0;
        int missingCount = 0;
        checkStates check;
        cellStates state;
        for (int i = 0; i < xSize; ++i)
            for (int j = 0; j < ySize; ++j) {
                check = cells[i][j].checkCell();
                state = cells[i][j].getState();
                if (check == checkStates.Wrong) {
                    wrongCount++;
                } else if (check == checkStates.Missing)
                    missingCount++;
            }
        a[0] = wrongCount;
        a[1] = missingCount;

        return a;
    }

    void resetRedCells(){
        // Reinicia el estado de las celdas que se estaban mostrando cómo erróneas
        for(int i = 0; i < xSize; ++i)
            for(int j = 0; j < ySize; ++j){
                if(cells[i][j].getState() == cellStates.Red)
                    cells[i][j].changeState();
            }
    }

    void resetAllowChangeStatesCells() {
        for (int i = 0; i < xSize; ++i)
            for (int j = 0; j < ySize; ++j)
                cells[i][j].resetAllowChange();
    }

    // Renderizado del tablero en el estado de juego
    void render(IGraphics graphics) {
        // Establece el color y la fuente de los números
        graphics.setColor(0xFF000000);
        graphics.setFont(font,0.3f * (graphics.relationAspectDimension() / 10) / graphics.getScale());
        // Escribe los números que corresponden a los valores consecutivos de las columnas
        for(int i = 0; i < xValues.size(); ++i){
            Vector<Integer> aux = xValues.get(i);
            for (int j = aux.size() - 1; j >= 0; --j) {
                graphics.drawText(String.valueOf(aux.get(j)), xZeroCord + (cellSide * i) + cellSide / 2, yZeroCord + xZeroCord / aux.size() - 5 - (aux.size() - j) * xZeroCord / aux.size());
            }
        }

        // // Escribe los números que corresponden a los valores consecutivos de las filas
        for (int i = 0; i < yValues.size(); ++i) {
            Vector<Integer> aux = yValues.get(i);
            for (int j = aux.size() - 1; j >= 0; --j) {
                graphics.drawText(String.valueOf(aux.get(j)), xZeroCord + xZeroCord / aux.size() - 10 - (aux.size() - j) * xZeroCord / aux.size(), yZeroCord + (cellSide * i) + cellSide / 2);
            }
        }
        // Rectángulo que rodea a las celdas
        graphics.drawRect(xZeroCord - 1, yZeroCord - 1, (cellSide + cellSpacing) * xSize + 1, (cellSide + cellSpacing) * ySize + 1, 1);
        // Rectángulo que rodea a los números de las filas
        graphics.drawRect(3, yZeroCord - 1, xZeroCord - 4, (cellSide + cellSpacing) * ySize + 1, 1);
        // Rectángulo que rodea a los números de las columnas
        graphics.drawRect(xZeroCord - 1, yZeroCord - xZeroCord, (cellSide + cellSpacing) * xSize + 1, xZeroCord - 1, 1);

        // Renderizado de las celdas
        for (int i = 0; i < xSize; ++i) {
            for (int j = 0; j < ySize; ++j) {
                cells[i][j].render(graphics, xZeroCord, yZeroCord, cellSide, cellSpacing);
            }
        }
    }

    // Renderizado del tablero en el estado final
    void renderWin(IGraphics graphics){
        float canvasCenterX = graphics.getOriginalWidth() / 2;
        float canvasCenterY = graphics.getOriginalHeight() / 2;
        // Coloca el (0, 0) del tablero en el centro del canvas - la mitad del tamaño del tablero para centrarlo
        xZeroCord = (int)(canvasCenterX - ((cellSide + cellSpacing) * xSize / 2));
        yZeroCord = (int)(canvasCenterY - ((cellSide + cellSpacing) * ySize / 2));

        // Renderiza sólo las celdas que forman parte de la solución
        for (int i = 0; i < xSize; ++i) {
            for (int j = 0; j < ySize; ++j) {
                if(cells[i][j].isGood)
                    cells[i][j].render(graphics, xZeroCord, yZeroCord, cellSide, cellSpacing);
            }
        }
    }

    void handleInput(float x, float y, IEngine e) {
        // Comprueba que las coordenadas del input estén dentro del tablero
        if (x >= xZeroCord && x <= xZeroCord + (cellSide + cellSpacing) * xSize && y >= yZeroCord && y <= yZeroCord + (cellSide + cellSpacing) * ySize) {
            // Convierte las coordenadas al número que corresponda entre 0 y xSize o 0 e ySize
            float xInBoard = (x - xZeroCord) / (cellSide + cellSpacing);
            float yInBoard = (y - yZeroCord) / (cellSide + cellSpacing);
            // Accede a la celda en el tablero y cambia su estado
            cells[(int) xInBoard][(int) yInBoard].changeState();
            e.getAudio().playSound("pop");
        }
    }
}
