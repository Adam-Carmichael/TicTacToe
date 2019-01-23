package com.projectstudy.rotten.tictactoe;

import android.widget.Button;

// class containing board object, tracks state of the board
public class Board {
    private char[][] tileArr;
    private Button[] butArr;

    public Board() {
        tileArr = new char[3][3];       // default value is 0 or (backslash)u0000 for empty elems
        butArr = new Button[9];
    }

    public void setButArr(Button[] someButArr) {
        int i;
        for (i = 0; i < someButArr.length; ++i) {
            butArr[i] = someButArr[i];
        }
    }

    public void setTile(char tile, int row, int col) {
        tileArr[row][col] = tile;
    }

    public char getTile(int row, int col) {
        return tileArr[row][col];
    }

    // sets all private variables of Board class to default values
    public void resetBoard() {
        tileArr = null;
        System.gc();
    }
}

