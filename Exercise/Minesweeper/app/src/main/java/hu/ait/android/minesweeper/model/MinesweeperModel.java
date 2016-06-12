package hu.ait.android.minesweeper.model;

import android.util.Log;

import java.util.Random;

public class MinesweeperModel {
    private static MinesweeperModel instance = null;

    public static MinesweeperModel getInstance() {
        if (instance == null) {
            instance = new MinesweeperModel();
        }
        return instance;
    }

    public final static short SIZE = 5;
    public final static short NUM_MINES = 3;

    private short[][] model = new short[SIZE][SIZE];
    private short[][] gameboard = new short[SIZE][SIZE];

    public static final short MINE = -1;
    public static final short NEIGHBORING_MINES = 0;

    public static final short CLOSE = 0; // not clicked yet
    public static final short OPEN = 1;
    public static final short FLAG = 2;

    private MinesweeperModel() {
        initModel();
        initGameboard();
    }

    private void initModel() {
        Random r = new Random();
        short minesOnModel = 0;

        while (minesOnModel < NUM_MINES) {
            short x = (short) r.nextInt(SIZE);
            short y = (short) r.nextInt(SIZE);
            if (model[x][y] != MINE) {
                model[x][y] = MINE;
                minesOnModel++;
            }
        }
    }

    private void initGameboard() {
        for (int i = 0; i < SIZE; ++i) {
            for (int j = 0; j < SIZE; ++j) {
                gameboard[i][j] = CLOSE;
            }
        }
    }

    public boolean isGridOpen() {
        for (int i = 0; i < SIZE; ++i) {
            for (int j = 0; j < SIZE; ++j) {
                if (gameboard[i][j] == CLOSE)
                    return false;
            }
        }
        return true;
    }

    public boolean isFieldOpen(short x, short y) {
        return gameboard[x][y] == OPEN;
    }

    public short getNumOfNeighboringMines(short x, short y) {
        return model[x][y];
    }

    public void setFlag(short x, short y) {
        gameboard[x][y] = FLAG;
    }

    public void removeFlag(short x, short y) {
        gameboard[x][y] = CLOSE;
    }
}
