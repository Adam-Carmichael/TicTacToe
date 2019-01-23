package com.projectstudy.rotten.tictactoe;

public class GameLogic {
    private char playerTile;
    private char cpuTile;

    public GameLogic() {
        // not sure yet
    }

    public void setPlayerTile(char c){ playerTile = c; }
    public void setCpuTile(char c) {
        cpuTile = c;
    }
    public char getPlayerTile() {
        return playerTile;
    }
    public char getCpuTile(){
        return cpuTile;
    }

    // sets all private variables of GameLogic class to default values
    public void resetLogic() {
        playerTile = 0;
        cpuTile = 0;
    }
}
