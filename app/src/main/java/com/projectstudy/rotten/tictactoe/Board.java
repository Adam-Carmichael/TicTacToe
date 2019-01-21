package com.projectstudy.rotten.tictactoe;

// class containing board object, tracks state of the board
public class Board {
    private char[][] tileArr;
    private char playerTile;
    private char cpuTile;

    public Board() {
        tileArr = new char[3][3];       // default value is 0 or (backslash)u0000 for empty elems
    }

    public void setTile(char tile, int row, int col) {
        tileArr[row][col] = tile;
    }

    public char getTile(int row, int col) { return tileArr[row][col]; }

    public void resetBoard() {
        tileArr = null;
        System.gc();
    }

    public void setPlayerTile(char c){
        playerTile = c;
    }

    public void setCpuTile(char c) {
        cpuTile = c;
    }

    public char getPlayerTile() {
        return playerTile;
    }

    public char getCpuTile(){
        return cpuTile;
    }
}
