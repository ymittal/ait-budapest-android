package hu.ait.android.minesweeper.model;

import android.util.Log;

import java.util.Arrays;
import java.util.Random;

import hu.ait.android.minesweeper.view.MinesweeperView;

public class MinesweeperModel {
    public static final short CLOSE = 0; // not clicked yet
    public static final short OPEN = 1;
    public static final short FLAG = 2;

    public static int SIZE = 6; // default 6x6 grid
    public static int NUM_MINES = 6;

    private static MinesweeperModel instance = null;

    private int[][] model;
    private short[][] board;

    private MinesweeperModel() {
        initModel();
        initBoard();
        Log.d("LOG_TAG", SIZE + " " + NUM_MINES + " " + Arrays.deepToString(model));
    }

    public static MinesweeperModel getInstance() {
        if (instance == null) {
            instance = new MinesweeperModel();
        }
        return instance;
    }

    private void initModel() {
        model = new int[SIZE][SIZE];
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
        board = new short[SIZE][SIZE];
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
        if (model[x][y] == 0) {
            for (int m = x - 1; m <= x + 1; ++m) {
                for (int n = y - 1; n <= y + 1; ++n) {
                    if (isWithinBounds(m, n) && board[m][n] == CLOSE
                            && model[m][n] != -1)
                        clickField(m, n);
                }
            }
        } else {
            return;
        }
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

    public void resetModel(int size, int numMines) {
        instance = null;
        SIZE = size;
        NUM_MINES = numMines;
        MinesweeperView.resetFlagsLeft(numMines);
    }
}
