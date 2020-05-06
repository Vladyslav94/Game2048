package Game2048;

import java.util.*;

public class Model {
    private static final int FIELD_WIDTH = 4;
    private Tile[][] gameTiles;
    int score;
    int maxTile;
    private Stack<Tile[][]> previousStates;
    private Stack<Integer> previousScores;
    private boolean isSaveNeeded = true;

    public Model() {
        gameTiles = new Tile[FIELD_WIDTH][FIELD_WIDTH];
        resetGameTiles();
        this.score = 0;
        this.maxTile = 0;
        this.previousStates = new Stack<>();
        this.previousScores = new Stack<>();
    }

    public Tile[][] getGameTiles() {
        return gameTiles;
    }

    private List<Tile> getEmptyTiles() {
        List<Tile> list = new ArrayList<>();
        for (int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_WIDTH; j++) {
                if (gameTiles[i][j].isEmpty()) {
                    list.add(gameTiles[i][j]);
                }
            }
        }
        return list;
    }


    private void addTile() {
        if (getEmptyTiles().size() > 0) {
            getEmptyTiles().get((int) (getEmptyTiles().size() * Math.random())).value = (Math.random() < 0.9 ? 2 : 4);
        }
    }

    public void resetGameTiles() {
        this.gameTiles = new Tile[FIELD_WIDTH][FIELD_WIDTH];
        for (int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_WIDTH; j++) {
                gameTiles[i][j] = new Tile();
            }
        }
        addTile();
        addTile();
        this.score = 0;
        this.maxTile = 0;
    }

    private boolean compressTiles(Tile[] tiles) {
        boolean isCompressed = false;

        for (int i = 0; i < tiles.length - 1; i++) {
            if (tiles[i].isEmpty()) {
                Tile tmp = tiles[i];

                for (int j = i + 1; j < tiles.length; j++) {
                    if (!tiles[j].isEmpty()) {
                        tiles[i] = tiles[j];
                        tiles[j] = tmp;
                        isCompressed = true;
                        break;
                    }
                }
            }
        }

        return isCompressed;
    }

    private boolean mergeTiles(Tile[] tiles) {
        boolean isMerged = false;

        for (int i = 0; i < tiles.length - 1; i++) {
            if (tiles[i].value == tiles[i + 1].value && !tiles[i].isEmpty()) {
                int newValue = tiles[i].value * 2;
                tiles[i].value = newValue;
                tiles[i + 1].value = 0;
                score += newValue;

                if (newValue > maxTile) {
                    maxTile = newValue;
                }

                isMerged = true;
            }
        }
        compressTiles(tiles);
        return isMerged;
    }

    void left() {
        boolean wasChanged = false;
        for (int row = 0; row < gameTiles.length; row++) {
            wasChanged = compressTiles(gameTiles[row]) || wasChanged;
            wasChanged = mergeTiles(gameTiles[row]) || wasChanged;
        }
        if (wasChanged) {
            addTile();
        }
    }

    void right() {
        boolean wasChanged = false;
        Tile[][] temp = new Tile[4][4];
        int count = 0;

        for (int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = FIELD_WIDTH - 1; j >= 0; j--) {
                temp[i][count++] = gameTiles[i][j];
            }
            count = 0;
        }

        for (Tile[] gameTile : temp) {
            wasChanged = compressTiles(gameTile) || wasChanged;
            wasChanged = mergeTiles(gameTile) || wasChanged;
        }

        for (int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = FIELD_WIDTH - 1; j >= 0; j--) {
                gameTiles[i][count++] = temp[i][j];
            }
            count = 0;
        }

        if (wasChanged) {
            addTile();
        }
    }

    void down() {
        for (int i = 0; i < FIELD_WIDTH / 2; i++) {
            for (int j = i; j < FIELD_WIDTH - i - 1; j++) {
                Tile temp = gameTiles[i][j];
                gameTiles[i][j] = gameTiles[FIELD_WIDTH - 1 - j][i];
                gameTiles[FIELD_WIDTH - 1 - j][i] = gameTiles[FIELD_WIDTH - 1 - i][FIELD_WIDTH - 1 - j];
                gameTiles[FIELD_WIDTH - 1 - i][FIELD_WIDTH - 1 - j] = gameTiles[j][FIELD_WIDTH - 1 - i];
                gameTiles[j][FIELD_WIDTH - 1 - i] = temp;
            }
        }

        left();

        for (int m = 0; m < 3; m++) {
            for (int i = 0; i < FIELD_WIDTH / 2; i++) {
                for (int j = i; j < FIELD_WIDTH - i - 1; j++) {
                    Tile temp = gameTiles[i][j];
                    gameTiles[i][j] = gameTiles[FIELD_WIDTH - 1 - j][i];
                    gameTiles[FIELD_WIDTH - 1 - j][i] = gameTiles[FIELD_WIDTH - 1 - i][FIELD_WIDTH - 1 - j];
                    gameTiles[FIELD_WIDTH - 1 - i][FIELD_WIDTH - 1 - j] = gameTiles[j][FIELD_WIDTH - 1 - i];
                    gameTiles[j][FIELD_WIDTH - 1 - i] = temp;
                }
            }
        }
    }

    void up(){
        for (int m = 0; m < 3; m++) {
            for (int i = 0; i < FIELD_WIDTH / 2; i++) {
                for (int j = i; j < FIELD_WIDTH - i - 1; j++) {
                    Tile temp = gameTiles[i][j];
                    gameTiles[i][j] = gameTiles[FIELD_WIDTH - 1 - j][i];
                    gameTiles[FIELD_WIDTH - 1 - j][i] = gameTiles[FIELD_WIDTH - 1 - i][FIELD_WIDTH - 1 - j];
                    gameTiles[FIELD_WIDTH - 1 - i][FIELD_WIDTH - 1 - j] = gameTiles[j][FIELD_WIDTH - 1 - i];
                    gameTiles[j][FIELD_WIDTH - 1 - i] = temp;
                }
            }
        }

        left();

        for (int i = 0; i < FIELD_WIDTH / 2; i++) {
            for (int j = i; j < FIELD_WIDTH - i - 1; j++) {
                Tile temp = gameTiles[i][j];
                gameTiles[i][j] = gameTiles[FIELD_WIDTH - 1 - j][i];
                gameTiles[FIELD_WIDTH - 1 - j][i] = gameTiles[FIELD_WIDTH - 1 - i][FIELD_WIDTH - 1 - j];
                gameTiles[FIELD_WIDTH - 1 - i][FIELD_WIDTH - 1 - j] = gameTiles[j][FIELD_WIDTH - 1 - i];
                gameTiles[j][FIELD_WIDTH - 1 - i] = temp;
            }
        }
    }

    public boolean canMove(){
        if(getEmptyTiles().size() > 0)
            return true;
        boolean canMove = false;
        for (int i = 0; i < gameTiles.length-1; i++) {
            for (int j = 0; j < gameTiles[i].length-1; j++) {
                if (gameTiles[i][j].value == gameTiles[i][j + 1].value || gameTiles[i + 1][j].value == gameTiles[i][j].value) {
                    canMove = true;
                    break;
                }
            }
        }
        return canMove;
    }

    private void saveState(Tile[][] tiles){
        Tile[][] newGameTiles = new Tile[4][4];
        for (int i = 0; i < FIELD_WIDTH; i++) {
            System.arraycopy(gameTiles[i], 0, newGameTiles[i], 0, FIELD_WIDTH);
        }
        previousStates.push(newGameTiles);
        previousScores.push(score);
        isSaveNeeded = false;
    }

    public void rollback(){
        if(!previousScores.isEmpty() && !previousStates.isEmpty()){
            this.gameTiles = previousStates.pop();
            this.score = previousScores.pop();
        }
    }



}
