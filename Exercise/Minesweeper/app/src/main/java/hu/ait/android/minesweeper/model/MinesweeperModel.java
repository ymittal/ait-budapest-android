package hu.ait.android.minesweeper.model;

import android.util.Log;

import java.util.Arrays;
import java.util.Random;

public class MinesweeperModel {
    public final static int SIZE = 5;
    public final static int NUM_MINES = 5;

    private static MinesweeperModel instance = null;

    public static MinesweeperModel getInstance() {
        if (instance == null) {
            instance = new MinesweeperModel();
        }
        return instance;
    }

    private int[][] model = new int[SIZE][SIZE];
    private short[][] board = new short[SIZE][SIZE];

    public static final short CLOSE = 0; // not clicked yet
    public static final short OPEN = 1;
    public static final short FLAG = 2;

    private MinesweeperModel() {
        initModel();
        initBoard();
        Log.d("Hello", Arrays.deepToString(model));
    }

    private void initModel() {
        Random r = new Random();
        int minesOnModel = 0;

        while (minesOnModel < NUM_MINES) {
            int x = r.nextInt(SIZE);
            int y = r.nextInt(SIZE);
            if (model[x][y] != -1) {
                model[x][y] = -1;
                minesOnModel += 1;
            }
        }

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

    public short getFieldStatus(int x, int y) {
        return board[x][y];
    }

    public void clickField(int x, int y) {
        board[x][y] = OPEN;
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

    public int getNumOfNeighboringMines(int x, int y) {
        return model[x][y];
    }

    public void setFlag(int x, int y) {
        board[x][y] = FLAG;
    }

    public void removeFlag(int x, int y) {
        board[x][y] = CLOSE;
    }

    public boolean isMineAtLocation(int x, int y) {
        return model[x][y] == -1;
    }
}
