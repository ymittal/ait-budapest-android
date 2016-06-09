package hu.ait.android.tictactoe.model;

public class TicTacToeModel {
    private static  TicTacToeModel instance = null;

    public static TicTacToeModel getInstance() {
        if (instance == null) {
            instance = new TicTacToeModel();
        }

        return instance;
    }

    private TicTacToeModel() {

    }

    public static final short EMPTY = 0;
    public static final short CIRCLE = 1;
    public static final short CROSS = 2;

    private short[][] model = {
            { EMPTY, EMPTY, EMPTY },
            { EMPTY, EMPTY, EMPTY },
            { EMPTY, EMPTY, EMPTY }
    };
    private short nextPlayer = CIRCLE;

    public boolean isGridFull() {
        for (int i=0; i<3; ++i) {
            for (int j=0; j<3; ++j) {
                if (model[i][j] == EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    public void resetModel() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                model[i][j] = EMPTY;
            }
        }
        nextPlayer = CIRCLE;
    }

    public short getFieldContent(int x, int y) {
        return model[x][y];
    }

    public short isGameOver() {
        for (int i=0; i<3; ++i) {
            if (model[i][0] == model[i][1] && model[i][1] == model[i][2]
                    && model[i][0] != EMPTY) {
                return model[i][0];
            }
        }
        for (int j=0; j<3; ++j) {
            if (model[0][j] == model[1][j] && model[1][j] == model[2][j]
                    && model[0][j] != EMPTY) {
                return model[0][j];
            }
        }
        if (model[1][1] != EMPTY) {
            if ((model[0][0] == model[1][1] && model[1][1] == model[2][2]) ||
                    (model[2][0] == model[1][1] && model[1][1] == model[0][2])) {
                return model[1][1];
            }
        }
        return EMPTY;
    }

    public short setFieldContent(int x, int y, short content) {
        return model[x][y] = content;
    }

    public short getNextPlayer() {
        return nextPlayer;
    }

    public void changeNextPlayer() {
        nextPlayer = (nextPlayer == CIRCLE) ? CROSS : CIRCLE;
    }
}
