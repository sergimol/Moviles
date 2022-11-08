package com.example.logic;

import java.util.Random;
import java.util.Vector;

import jdk.internal.net.http.common.Pair;

public class Board {
    private int xSize, ySize;
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

}
