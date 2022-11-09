package com.example.logic;

import com.example.interfaces.IEngine;
import com.example.interfaces.IGraphics;

import java.lang.reflect.Array;
import java.util.Random;
import java.util.Vector;

public class Board {
    private int xSize, ySize;
    private int xZeroCord, yZeroCord;
    private float cellSide, cellSpacing;
    Cell cells[][];

    Cell getCell(int x, int y) {
        return cells[x][y];
    }

    Vector<Vector<Integer>> xValues;
    Vector<Vector<Integer>> yValues;

    Board(int x, int y) {
        xSize = x;
        ySize = y;
        cells = new Cell[xSize][ySize];
        xValues = new Vector<Vector<Integer>>(xSize);
        for (int i = 0; i < xSize; ++i)
            xValues.add(new Vector<Integer>(ySize / 2 + ySize % 2));
        yValues = new Vector<Vector<Integer>>(ySize);
        for (int j = 0; j < ySize; ++j)
            yValues.add(new Vector<Integer>(xSize / 2 + xSize % 2));
        createRandomBoard();
    }

    void init(IEngine e) {
        xZeroCord = (int) e.getGraphics().getOriginalWidth() / 5;
        yZeroCord = (int) e.getGraphics().getOriginalHeight() / 3;
        int xSizeBoard = (int) e.getGraphics().getOriginalWidth() - xZeroCord;
        int ySizeBoard = (int) e.getGraphics().getOriginalHeight() - yZeroCord;
        int cellSizeX = xSizeBoard / xSize;
        int cellSizeY = ySizeBoard / ySize;
        cellSide = Math.min(cellSizeX, cellSizeY);
        int spaceX = (int) (xSizeBoard - cellSide * xSize);
        int spaceY = (int) (ySizeBoard - cellSide * ySize);
        cellSpacing = Math.min((xSizeBoard - cellSide * xSize) / xSize, ((int) e.getGraphics().getOriginalHeight() - yZeroCord - cellSide * ySize) / ySize);
        cellSide -= cellSpacing;
    }

    int getxSize() {
        return xSize;
    }

    int getySize() {
        return ySize;
    }

    void createRandomBoard() {
        boolean good = false;
        Random rd = new Random();
        int xConsecutives = 0;
        for (int i = 0; i < xSize; i++) {
            for (int j = 0; j < ySize; j++) {
                good = rd.nextBoolean();
                cells[i][j] = new Cell(i, j, good);
                if (good) {
                    xConsecutives++;
                } else if (xConsecutives > 0) {
                    xValues.get(i).add(xConsecutives);
                    xConsecutives = 0;
                }
            }
            if (xConsecutives > 0) {
                xValues.get(i).add(xConsecutives);
                xConsecutives = 0;
            }
        }

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

    void resetAllowChangeStatesCells() {
        for (int i = 0; i < xSize; ++i)
            for (int j = 0; j < ySize; ++j)
                cells[i][j].resetAllowChange();
    }

    void render(IGraphics graphics) {
        graphics.setColor(0xFF000000);

        for (int i = 0; i < xValues.size(); ++i) {
            Vector<Integer> aux = xValues.get(i);
            for (int j = aux.size() - 1; j >= 0; --j) {
                graphics.drawText(String.valueOf(aux.get(j)), xZeroCord + (cellSide * i) + cellSide / 2, yZeroCord + xZeroCord / aux.size() - 5 - (aux.size() - j) * xZeroCord / aux.size());
            }
        }

        for (int i = 0; i < yValues.size(); ++i) {
            Vector<Integer> aux = yValues.get(i);
            for (int j = aux.size() - 1; j >= 0; --j) {
                graphics.drawText(String.valueOf(aux.get(j)), xZeroCord + xZeroCord / aux.size() - 10 - (aux.size() - j) * xZeroCord / aux.size(), yZeroCord + (cellSide * i) + cellSide / 2);
            }
        }
        graphics.drawRect(xZeroCord - 1, yZeroCord - 1, (cellSide + cellSpacing) * xSize + 1, (cellSide + cellSpacing) * ySize + 1, 1);
        graphics.drawRect(3, yZeroCord - 1, xZeroCord - 4, (cellSide + cellSpacing) * ySize + 1, 1);
        graphics.drawRect(xZeroCord - 1, yZeroCord - xZeroCord, (cellSide + cellSpacing) * xSize + 1, xZeroCord - 1, 1);

        for (int i = 0; i < xSize; ++i) {
            for (int j = 0; j < ySize; ++j) {
                cells[i][j].render(graphics, xZeroCord, yZeroCord, cellSide, cellSpacing);
            }
        }
    }

    void handleInput(float x, float y) {
        if (x >= xZeroCord && x <= xZeroCord + (cellSide + cellSpacing) * xSize && y >= yZeroCord && y <= yZeroCord + (cellSide + cellSpacing) * ySize) {
            float xInBoard = (x - xZeroCord) / (cellSide + cellSpacing);
            float yInBoard = (y - yZeroCord) / (cellSide + cellSpacing);
            //int logicX = ;
            //int logicY = ;
            cells[(int) xInBoard][(int) yInBoard].changeState();
        }
    }
}
