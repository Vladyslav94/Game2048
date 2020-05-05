package Game2048;

public class Main {

    public static void main(String[] args) {
        Model model = new Model();
        model.gameTiles = new Tile[][]{{new Tile(2), new Tile(4), new Tile(0), new Tile(2)},
                {new Tile(8), new Tile(2), new Tile(4), new Tile(0)},
                {new Tile(4), new Tile(2), new Tile(2), new Tile(0)},
                {new Tile(8), new Tile(4), new Tile(2), new Tile(0)}};

        for (int i = 0; i < model.gameTiles.length; i++) {
            for (int j = 0; j < model.gameTiles[i].length; j++) {
                System.out.print(model.gameTiles[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("After");

        model.up();
        for (int i = 0; i < model.gameTiles.length; i++) {
            for (int j = 0; j < model.gameTiles[i].length; j++) {
                System.out.print(model.gameTiles[i][j] + " ");
            }
            System.out.println();
        }

    }
}

