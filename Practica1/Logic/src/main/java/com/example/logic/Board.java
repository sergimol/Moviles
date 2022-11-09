package com.example.logic;

import com.example.interfaces.IEngine;
import com.example.interfaces.IGraphics;

import java.util.Random;
import java.util.Vector;

import jdk.internal.net.http.common.Pair;

public class Board {
    private int xSize, ySize;
    private int xZeroCord, yZeroCord;
    private float cellSide, cellSpacing;
    Cell cells[][];

    Cell getCell(int x, int y){
        return cells[x][y];
    }
    Vector<Vector<Integer>> xValues = new Vector<>();
    Vector<Vector<Integer>> yValues = new Vector<>();

    Board(int x, int y){
        xSize = x;
        ySize = y;
        cells = new Cell[xSize][ySize];
        createRandomBoard();
    }

    void init(IEngine e){
        xZeroCord = (int)e.getGraphics().getOriginalWidth() / 5;
        yZeroCord = (int)e.getGraphics().getOriginalHeight() / 3;
        int xSizeBoard = (int)e.getGraphics().getOriginalWidth() - xZeroCord;
        int ySizeBoard = (int)e.getGraphics().getOriginalHeight() - yZeroCord;
        int cellSizeX = xSizeBoard / xSize;
        int cellSizeY = ySizeBoard / ySize;
        cellSide = Math.min(cellSizeX, cellSizeY);
        int spaceX = (int)(xSizeBoard - cellSide * xSize);
        int spaceY = (int)(ySizeBoard - cellSide * ySize);
        cellSpacing = Math.min((xSizeBoard - cellSide * xSize) / xSize, ((int)e.getGraphics().getOriginalHeight() - yZeroCord - cellSide * ySize) / ySize);
        cellSide -= cellSpacing;
    }

    int getxSize(){
        return xSize;
    }

    int getySize(){
        return ySize;
    }

    void createRandomBoard(){
        boolean good = false;
        Random rd = new Random();
        int xConsecutives = 0, yConsecutives = 0;
        for(int i = 0; i < xSize; i++)
            for(int j = 0; j < ySize; j++){
                good = rd.nextBoolean();
                cells[i][j] = new Cell(i, j, good);
                if(good){
                    xConsecutives++;
                    yConsecutives++;
                }
                else{
                    //xValues.elementAt(i).add(xConsecutives);
                    xConsecutives = 0;
                    if(yConsecutives != 0){
                        //yValues.elementAt(j).add(yConsecutives);
                        yConsecutives = 0;
                    }
                }
            }
    }

    Pair<Integer, Integer> checkBoard(){
        int wrongCount = 0;
        int missingCount = 0;
        checkStates check;
        cellStates state;
        for(int i = 0; i < xSize; ++i)
            for(int j = 0; j < ySize; ++j){
                check = cells[i][j].checkCell();
                state = cells[i][j].getState();
                if(check == checkStates.Wrong){
                    wrongCount++;
                }
                else if(check == checkStates.Missing)
                    missingCount++;
            }
        return new Pair<>(wrongCount, missingCount);
    }

    void resetAllowChangeStatesCells(){
        for(int i = 0; i < xSize; ++i)
            for(int j = 0; j < ySize; ++j)
                cells[i][j].resetAllowChange();
    }

    void render(IGraphics graphics){
        graphics.setColor(0);
        graphics.drawRect(xZeroCord - 1, yZeroCord - 1, (cellSide + cellSpacing) * xSize + 1, (cellSide + cellSpacing) * ySize + 1);
        for (int i = 0; i < xSize; ++i) {
            for (int j = 0; j < ySize; ++j) {
                cells[i][j].render(graphics, xZeroCord, yZeroCord, cellSide, cellSpacing);
            }
        }
    }

    void handleInput(float x, float y){
        if(x >= xZeroCord && x <= xZeroCord + (cellSide + cellSpacing) * xSize && y >= yZeroCord && y <= yZeroCord + (cellSide + cellSpacing) * ySize){
            float xInBoard = (x - xZeroCord) / (cellSide + cellSpacing);
            float yInBoard = (y - yZeroCord) / (cellSide + cellSpacing);
            //int logicX = ;
            //int logicY = ;
            cells[(int)xInBoard][(int)yInBoard].changeState();
        }
    }
}
