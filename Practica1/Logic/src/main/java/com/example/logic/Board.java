package com.example.logic;

import java.util.Random;
import java.util.Vector;

public class Board {
    int xSize, ySize;
    Cell cells[][];

    Cell getCell(int x, int y){ return cells[x][y]; }
    Vector<Vector<Integer>> xValues;
    Vector<Vector<Integer>> yValues;

    Board(int x, int y){
        xSize = x;
        ySize = y;
        cells = new Cell[xSize][ySize];
        CreateRandomBoard();
    }

    void CreateRandomBoard(){
        boolean good = false;
        Random rd = new Random();
        int xConsecutives = 0, yConsecutives = 0;
        for(int i = 0; 0 < xSize; i++)
            for(int j = 0; 0 < ySize; j++){
                good = rd.nextBoolean();
                cells[i][j] = new Cell(i, j, good);
                if(good){
                    xConsecutives++;
                    yConsecutives++;
                }                    
                else{
                    xValues.elementAt(i).add(xConsecutives);
                    xConsecutives = 0;
                    if(yConsecutives != 0){
                        yValues.elementAt(j).add(yConsecutives);
                        yConsecutives = 0;
                    }
                }
            }
    }
}
