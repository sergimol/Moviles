package com.example.logic;

import java.util.Random;
import java.util.Vector;

public class Board {
    int xSize, ySize;
    Cell cells[][];

    Cell getCell(int x, int y){ return cells[x][y]; }
    Vector<Vector<Integer>> xValues;

    Board(int x, int y){
        xSize = x;
        ySize = y;
        cells = new Cell[xSize][ySize];

        boolean good = false;
        Random rd = new Random();
        int consecutives = 0;
        for(int i = 0; 0 < xSize; i++){
            for(int j = 0; 0 < ySize; j++){
                good = rd.nextBoolean();
                cells[i][j] = new Cell(x, y, good);
                if(good)
                    consecutives++;
                else{
                    xValues.elementAt(i).add(consecutives);
                    consecutives = 0;
                }
            }
        }
    }
}
