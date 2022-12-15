package com.example.practica1;
import android.content.res.AssetManager;
import android.os.Bundle;

import com.example.androidengine.AEngine;
import com.example.androidengine.AFont;
import com.example.androidengine.AGraphics;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.Vector;

public class Board implements Serializable {
    private int xSize, ySize;
    private int xZeroCord, yZeroCord;
    private float cellSide, cellSpacing;
    Cell cells[][];

    Vector<Vector<Integer>> yValues;
    Vector<Vector<Integer>> xValues;

    //no es serializable
    AFont font;

    Cell getCell(int x, int y) {
        return cells[x][y];
    }


    Board() {

    }

    Board(int x, int y) {
        // Nº de celdas en x
        xSize = x;
        // Nº de celdas en y
        ySize = y;
        setArrays();
        createRandomBoard();
    }

    Board(AssetManager assets, String levelName) throws IOException {
        InputStream is = assets.open(levelName + ".txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
        xSize = Integer.parseInt(br.readLine());
        ySize = Integer.parseInt(br.readLine());
        setArrays();
        readBoard(br);
    }

    void setArrays(){
        // Array que guarda las celdas del tablero
        cells = new Cell[xSize][ySize];
        // Vector para guardar las celdas consecutivas en cada columna
        yValues = new Vector<Vector<Integer>>(xSize);
        for (int i = 0; i < xSize; ++i)
            yValues.add(new Vector<Integer>(ySize / 2 + ySize % 2));
        // Vector para guardar las celdas consecutivas en cada fila
        xValues = new Vector<Vector<Integer>>(ySize);
        for (int j = 0; j < ySize; ++j)
            xValues.add(new Vector<Integer>(xSize / 2 + xSize % 2));
    }

    void init(AEngine e, AFont f) {
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
        int yConsecutives = 0;
        for (int i = 0; i < xSize; i++) {
            for (int j = 0; j < ySize; j++) {
                // Asignación de un valor aleatorio a good
                good = rd.nextBoolean();
                cells[i][j] = new Cell(i, j, good);
                // Si es buena se aumenta el contador
                if (good) {
                    yConsecutives++;
                }
                // Si no se guarda el valor del contador y se reinicia
                else if (yConsecutives > 0) {
                    yValues.get(i).add(yConsecutives);
                    yConsecutives = 0;
                }
            }
            // Al llegar al final de la columna se guarda el contador si es mayor de 0 y se reinicia
            if (yConsecutives > 0) {
                yValues.get(i).add(yConsecutives);
                yConsecutives = 0;
            }
        }

        setxValues();
    }

    void readBoard(BufferedReader br) throws IOException{
        boolean good;
        String line;
        int xConsecutives = 0;
        int j = 0;
        while((line = br.readLine()) != null && j < ySize){
            String []split = line.split("");
            for(int i = 0; i < xSize; ++i){
                good = (Integer.parseInt(split[i]) == 1);
                cells[i][j] = new Cell(i, j, good);
                if (good) {
                    xConsecutives++;
                }
                // Si no se guarda el valor del contador y se reinicia
                else if (xConsecutives > 0) {
                    xValues.get(j).add(xConsecutives);
                    xConsecutives = 0;
                }
            }
            if (xConsecutives > 0) {
                xValues.get(j).add(xConsecutives);
                xConsecutives = 0;
            }
            j++;
        }

        setyValues();
    }

    void setyValues(){
        // Contador para guardar las celdas consecutivas en cada fila
        int yConsecutives = 0;
        for (int i = 0; i < xSize; i++) {
            for (int j = 0; j < ySize; j++) {
                if (cells[i][j].isGood) {
                    yConsecutives++;
                } else if (yConsecutives > 0) {
                    yValues.get(i).add(yConsecutives);
                    yConsecutives = 0;
                }
            }
            if (yConsecutives > 0) {
                yValues.get(i).add(yConsecutives);
                yConsecutives = 0;
            }
        }
    }

    void setxValues(){
        // Contador para guardar las celdas consecutivas en cada columnna
        int xConsecutives = 0;
        for (int j = 0; j < ySize; j++) {
            for (int i = 0; i < xSize; i++) {
                if (cells[i][j].isGood) {
                    xConsecutives++;
                } else if (xConsecutives > 0) {
                    xValues.get(j).add(xConsecutives);
                    xConsecutives = 0;
                }
            }
            if (xConsecutives > 0) {
                xValues.get(j).add(xConsecutives);
                xConsecutives = 0;
            }
        }
    }

    int[] checkBoard() {
        int []a = new int[2];
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
    void render(AGraphics graphics) {
        // Establece el color y la fuente de los números
        graphics.setColor(0xFF000000);
        graphics.setFont(font,0.3f * (graphics.relationAspectDimension() / 10) / graphics.getScale());
        int minSpace;
        int numOffset;
        int lowestY = yZeroCord;

        minSpace = xZeroCord / (ySize / 2 + ySize % 2);
        // Escribe los números que corresponden a los valores consecutivos de las columnas
        for(int i = 0; i < yValues.size(); ++i){
            Vector<Integer> aux = yValues.get(i);
            for (int j = aux.size() - 1; j >= 0; --j) {
                numOffset = minSpace * 3 / 2 * (aux.size() - j);
                int y = yZeroCord + minSpace - numOffset;
                graphics.drawText(String.valueOf(aux.get(j)), xZeroCord + (cellSide * i) + cellSide / 2, y);
                lowestY = Math.min(yZeroCord + minSpace - (minSpace * 3 / 2 * (aux.size() - j + 1)), lowestY);
            }
        }

        //Calcula el espacio mínimo entre números de la misma fila
        minSpace = xZeroCord / (xSize / 2 + xSize % 2);
        //Escribe los números que corresponden a los valores consecutivos de las filas
        for (int i = 0; i < xValues.size(); ++i) {
            Vector<Integer> aux = xValues.get(i);
            for (int j = aux.size() - 1; j >= 0; --j) {
                numOffset = minSpace * 2 / 3 * (aux.size() - j);
                graphics.drawText(String.valueOf(aux.get(j)), xZeroCord - numOffset, yZeroCord + (cellSide * i) + cellSide / 2);
            }
        }
        // Rectángulo que rodea a las celdas
        graphics.drawRect(xZeroCord - 1, yZeroCord - 1, (cellSide + cellSpacing) * xSize + 1, (cellSide + cellSpacing) * ySize + 1, 1);
        // Rectángulo que rodea a los números de las filas
        graphics.drawRect(3, yZeroCord - 1, xZeroCord - 4, (cellSide + cellSpacing) * ySize + 1, 1);
        // Rectángulo que rodea a los números de las columnas
        graphics.drawRect(xZeroCord - 1, lowestY, (cellSide + cellSpacing) * xSize + 1, yZeroCord - lowestY, 1);

        // Renderizado de las celdas
        for (int i = 0; i < xSize; ++i) {
            for (int j = 0; j < ySize; ++j) {
                cells[i][j].render(graphics, xZeroCord, yZeroCord, cellSide, cellSpacing);
            }
        }
    }

    // Renderizado del tablero en el estado final
    void renderWin(AGraphics graphics){
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

    boolean handleInput(float x, float y, AEngine e, boolean normaltouch) {
        // Comprueba que las coordenadas del input estén dentro del tablero
        if (x >= xZeroCord && x <= xZeroCord + (cellSide + cellSpacing) * xSize && y >= yZeroCord && y <= yZeroCord + (cellSide + cellSpacing) * ySize) {
            // Convierte las coordenadas al número que corresponda entre 0 y xSize o 0 e ySize
            float xInBoard = (x - xZeroCord) / (cellSide + cellSpacing);
            float yInBoard = (y - yZeroCord) / (cellSide + cellSpacing);
            e.getAudio().playSound("pop");

            // Accede a la celda en el tablero y cambia su estado
            //devuelve en el caso de haber marcado como azul un error si la casilla era equivocada
            return cells[(int) xInBoard][(int) yInBoard].changeState((normaltouch)?cellStates.Blue:cellStates.Empty);
        }
        //de default no sera un error
        return true;
    }

    public void save (Bundle outState){
        //outState.putSerializable("BoardCells", hearts);
        //todo esot hayq ue serializarlo
        outState.putSerializable("Board_xSize", xSize);
        outState.putSerializable("Board_ySize", ySize);
        outState.putSerializable("Board_Cells", cells);
        outState.putSerializable("Board_yValues", yValues);
        outState.putSerializable("Board_xValues", xValues);


    }
    public void load (Bundle saveState){
        //hearts = (int) saveState.getSerializable("vidas");

        xSize = (int) saveState.getSerializable("Board_xSize");
        ySize = (int) saveState.getSerializable("Board_ySize");
        cells = (Cell[][]) saveState.getSerializable("Board_Cells");
        yValues = (Vector<Vector<Integer>>) saveState.getSerializable("Board_yValues");
        xValues = (Vector<Vector<Integer>>) saveState.getSerializable("Board_xValues");
    }


}