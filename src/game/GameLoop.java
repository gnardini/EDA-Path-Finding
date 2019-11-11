package game;

import java.awt.*;

import static game.Constants.BOARD_SIZE;
import static game.Constants.SCALE;

public class GameLoop {

    private static final long ONE_SECOND = 1 * 1000000000;
    private final GameState gameState;
    private long lastUpdate;

    public GameLoop(GameState gameState) {
        this.gameState = gameState;
        lastUpdate = System.nanoTime();
    }

    public void loop(Graphics graphics) {
        repaintScreen(graphics);
        long time = System.nanoTime();
        if (time - lastUpdate > ONE_SECOND / 2) {
            gameState.updateEnemies();
            System.out.println("Time elapsed: " + ((double) System.nanoTime() - time) / 1000000 + "ms");
            lastUpdate = time;
        }
    }

    private void repaintScreen(Graphics g) {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                g.setColor(gameState.board[i][j].getColor());
                g.fillRect(i * SCALE, j * SCALE, SCALE, SCALE);
            }
        }
    }
}
