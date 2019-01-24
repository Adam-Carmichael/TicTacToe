package com.projectstudy.rotten.tictactoe;

import android.widget.Button;

// class containing board object, tracks state of the board
public class Board {
    private Button[][] butArr;
    private char[][] tileArr;
    private char playerTile;
    private char cpuTile;
    private boolean cpuTurn;

    public Board() {
        tileArr = new char[3][3];       // default value is 0 or (backslash)u0000 for empty elems
        butArr = new Button[3][3];
    }

    public void setButArr(Button[][] someArr) {
        int i, y;
        System.out.println("Printing rows: " + someArr.length);
        System.out.println("Printing columns: " + someArr[0].length);
        for (i = 0; i < someArr.length; ++i) {
            for (y = 0; y < someArr[0].length; ++y) {
                butArr[i][y] = someArr[i][y];
            }
        }
    }

    public void setPlayerTile(int row, int col) {
        tileArr[row][col] = playerTile;
    }

    public void setCpuTile(int row, int col) {
        tileArr[row][col] = cpuTile;
    }

    public char getTile(int row, int col) {
        return tileArr[row][col];
    }

    public void setCpuTurn(boolean turn) { cpuTurn = turn; }
    public boolean getCpuTurn() { return cpuTurn; }

    public void setPlayerTile(char someTile) { playerTile = someTile; }
    public char getPlayerTile() { return playerTile; }

    public void setCpuTile(char someTile) { cpuTile = someTile; }
    public char getCpuTile() { return cpuTile; }

    // sets all private variables of Board class to default values
    public void resetBoard() {
        tileArr = null;
        butArr = null;
        System.gc();
    }
}

