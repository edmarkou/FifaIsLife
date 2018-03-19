package com.edmarkou.fifaislife;



public class Player {

    private String mPlayerName;
    private int mWins;
    private int mLoses;

    public Player(String playerName, int wins, int loses){

        mPlayerName = playerName;
        mWins = wins;
        mLoses = loses;

    }

    public int getLoses() {
        return mLoses;
    }

    public void setLoses(int mLoses) {
        this.mLoses = mLoses;
    }

    public int getWins() {
        return mWins;
    }

    public void setWins(int mWins) {
        this.mWins = mWins;
    }

    public String getPlayerName() {
        return mPlayerName;
    }

    public void setmPlayerName(String mPlayerName) {
        this.mPlayerName = mPlayerName;
    }
}
