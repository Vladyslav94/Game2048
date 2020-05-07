package Game2048;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Controller extends KeyAdapter {
    private Model model;
    private View view;
    private static int WINNING_TILE = 2048;

    public Tile[][] getGameTiles(){
        return model.getGameTiles();
    }

    public int getScore(){
        return model.score;
    }

    public Controller(Model model){
        this.model = model;
        view = new View(this);
    }

    public View getView() {
        return view;
    }

    public void resetGame(){
        model.score = 0;
        view.isGameLost = false;
        view.isGameWon = false;
        model.resetGameTiles();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_ESCAPE)
            resetGame();
        if(!model.canMove())
            view.isGameLost = true;
        if(!view.isGameLost && !view.isGameWon){
            if(code == KeyEvent.VK_LEFT){
                model.left();
            } else if(code == KeyEvent.VK_RIGHT){
                model.right();
            } else if(code == KeyEvent.VK_UP){
                model.up();
            } else if(code == KeyEvent.VK_DOWN){
                model.down();
            } else if(code == KeyEvent.VK_Z){
                model.rollback();
            } else if(code == KeyEvent.VK_R){
                model.randomMove();
            } else if(code == KeyEvent.VK_A){
                model.autoMove();
            }
        }

        if(model.maxTile == WINNING_TILE)
            view.isGameWon = true;
        view.repaint();
    }
}
