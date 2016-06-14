package hu.ait.android.minesweeper.model;

import android.util.Log;

import java.util.Arrays;
import java.util.Random;

public class MinesweeperModel {
    public final static short SIZE = 5;
    public final static short NUM_MINES = 5;

    private static MinesweeperModel instance = null;

    public static MinesweeperModel getInstance() {
        if (instance == null) {
            instance = new MinesweeperModel();
        }
        return instance;
    }

    private short[][] model = new short[SIZE][SIZE];
    private short[][] board = new short[SIZE][SIZE];

    public static final short CLOSE = 0; // not clicked yet
    public static final short OPEN = 1;
    public static final short FLAG = 2;

    private MinesweeperModel() {
        initModel();
        initBoard();
    }

    private void initModel() {
        /*Random r = new Random();
        short minesOnModel = 0;

        while (minesOnModel < NUM_MINES) {
            short x = (short) r.nextInt(SIZE);
            short y = (short) r.nextInt(SIZE);
            if (model[x][y] != -1) {
                model[x][y] = -1;
                minesOnModel += 1;
            }
        }*/
        model[0][0] = model[1][2] = model[4][2] = model[1][0] = model[3][1] = -1;

        for (int i = 0; i < SIZE; ++i) {
            for (int j = 0; j < SIZE; ++j) {
                if (model[i][j] != -1)
                    setNumOfNeighboringMines(i, j);
            }
        }
    }

    private void initBoard() {
        for (int i = 0; i < SIZE; ++i) {
            for (int j = 0; j < SIZE; ++j) {
                board[i][j] = CLOSE;
            }
        }
    }

    public boolean isFullGridVisible() {
        for (int i = 0; i < SIZE; ++i) {
            for (int j = 0; j < SIZE; ++j) {
                if (board[i][j] == CLOSE)
                    return false;
            }
        }
        return true;
    }

    public boolean isVisible(int x, int y) {
        return board[x][y] == OPEN;
    }

    private void setNumOfNeighboringMines(int i, int j) {
        model[i][j] = 0;
        for (int m = i - 1; m <= i + 1; ++m) {
            for (int n = j - 1; n <= j + 1; ++n) {
                if (isWithinBounds(m, n) && model[m][n] == -1)
                    model[i][j] += 1;
            }
        }
    }

    private boolean isWithinBounds(int m, int n) {
        return (m >= 0 && m < SIZE && n >= 0 && n < SIZE);
    }

    public short getNumOfNeighboringMines(int x, int y) {
        return model[x][y];
    }

    public void setFlag(int x, int y) {
        board[x][y] = FLAG;
    }

    public void removeFlag(int x, int y) {
        board[x][y] = CLOSE;
    }
}
