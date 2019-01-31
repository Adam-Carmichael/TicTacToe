package com.projectstudy.rotten.tictactoe;

import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Collections;

// class containing board object, tracks state of the board
public class Board {
    private Button[][] butArr;
    private char[][] tileArr;
    private char playerTile;
    private char cpuTile;
    private boolean cpuTurn;
    private int[] coordArr;
    private boolean gameEnded;

    public Board() {
        tileArr = new char[3][3];       // default value is 0 or (backslash)u0000 for empty elems
        butArr = new Button[3][3];
        coordArr = new int[2];
        gameEnded = false;
    }

    // method that iterates through tileArr and checks if tic tac toe win conditions are met
    // returns X if X is winner, O if O is winner, and 0 if no winner
    public char checkWin() {
        int i, j;

        // check rows for a winner
        for (i = 0; i < tileArr.length; ++i) {
            if ((tileArr[i][0] == 'X' &&
                tileArr[i][0] == tileArr[i][1] &&
                tileArr[i][1] == tileArr[i][2])
                ||
                (tileArr[i][0] == 'O' &&
                tileArr[i][0] == tileArr[i][1] &&
                tileArr[i][1] == tileArr[i][2])) {
                    gameEnded = true;
                    System.out.println("row was returned");
                    return tileArr[i][0];
            }
        }

        // check cols for a winner
        for (j = 0; j < tileArr[0].length; ++j) {
            if ((tileArr[0][j] == 'X' &&
                tileArr[0][j] == tileArr[1][j] &&
                tileArr[1][j] == tileArr[2][j])
                ||
                (tileArr[0][j] == 'O' &&
                tileArr[0][j] == tileArr[1][j] &&
                tileArr[1][j] == tileArr[2][j])) {
                    gameEnded = true;
                    System.out.println("column was returned");
                    return tileArr[0][j];
            }
        }

        // check upper-left to bot-right diagonal for a winner
        if ((tileArr[0][0] == 'X' &&
            tileArr[0][0] == tileArr[1][1] &&
            tileArr[1][1] == tileArr[2][2])
            ||
            (tileArr[0][0] == 'O' &&
            tileArr[0][0] == tileArr[1][1] &&
            tileArr[1][1] == tileArr[2][2])) {
                gameEnded = true;
                System.out.println("diagonal was returned");
                return tileArr[0][0];
        }

        // check bot-left to top-right diagonal for a winner
        if ((tileArr[2][0] == 'O' &&
            tileArr[2][0] == tileArr[1][1] &&
            tileArr[1][1] == tileArr[0][2])
            ||
            (tileArr[2][0] == 'X' &&
            tileArr[2][0] == tileArr[1][1] &&
            tileArr[1][1] == tileArr[0][2])) {
                gameEnded = true;
                System.out.println("diagonal was returned");
                return tileArr[2][0];
        }

        return 0;
    }

    public void setButArr(Button[][] someArr) {
        int i, j;
        for (i = 0; i < someArr.length; ++i) {
            for (j = 0; j < someArr[0].length; ++j) {
                butArr[i][j] = someArr[i][j];
            }
        }
    }

    public void setButInvis(int i, int j) {
        butArr[i][j].setVisibility(View.INVISIBLE);
    }

    public void setGameEnded(boolean someBool) {
        gameEnded = someBool;
    }
    public boolean getGameEnded() { return gameEnded; }

    public void setPlayerTile(int row, int col) {
        tileArr[row][col] = playerTile;
    }
    public char getPlayerTile() {
        return playerTile;
    }

    public void setCpuTile(int row, int col) {
        tileArr[row][col] = cpuTile;
    }
    public char getCpuTile() {
        return cpuTile;
    }

    public char getTile(int row, int col) {
        return tileArr[row][col];
    }

    public void setCpuTurn(boolean turn) { cpuTurn = turn; }
    public boolean getCpuTurn() { return cpuTurn; }

    public void setPlayerTile(char someTile) { playerTile = someTile; }
    public void setCpuTile(char someTile) { cpuTile = someTile; }

    // returns the coordinates of a randomly selected (available) point on the button grid
    public int[] getCoordCpuRandom() {
        // first create two arrayLists for rows and cols
        // they are made to contain the numbers 0-2 and are then shuffled as
        // to be organized randomly
        int i;
        ArrayList<Integer> rowArr = new ArrayList<>(3);
        for (i = 0; i < 3; ++i) {
            rowArr.add(i);
        }
        Collections.shuffle(rowArr);

        ArrayList<Integer> colArr = new ArrayList<>(3);
        for (i = 0; i < 3; ++i) {
            colArr.add(i);
        }
        Collections.shuffle(colArr);

        // iterate through all the rows and columns of the board
        // check for empty tiles using the randomized row and col values
        int j;
        int row, col;
        for (i = 0; i < 3; ++i) {
            row = rowArr.get(i);

            for (j = 0; j < 3; ++j) {
                col = colArr.get(j);

                if (tileArr[row][col] == 'X' || tileArr[row][col] == 'O') {
                    continue;
                } else if (tileArr[row][col] == 0) {
                    coordArr[0] = row;
                    coordArr[1] = col;
                    return coordArr;
                }
            }
        }
        return null;    // if null is returned the board is full
    }
}

