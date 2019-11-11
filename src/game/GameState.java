package game;

import game.cell.*;
import game.policies.AstarPolicy;
import game.policies.EnemyPolicy;

import static game.Constants.BOARD_SIZE;

public class GameState implements GameOptions {

    private final EnemyPolicy enemyPolicy = new AstarPolicy();

    public Cell[][] board;
    public Player player;
    public Enemy[] enemies;

    public GameState() {
        resetGame();
    }

    public void moveDown() {
        if (player.y < BOARD_SIZE - 1 && isCellEmpty(player.x, player.y + 1)) {
            swap(player.x, player.y, player.x, player.y + 1);
            player.y++;
        }
    }

    public void moveUp() {
        if (player.y > 0 && isCellEmpty(player.x, player.y - 1)) {
            swap(player.x, player.y, player.x, player.y - 1);
            player.y--;
        }
    }

    public void moveLeft() {
        if (player.x > 0 && isCellEmpty(player.x - 1, player.y)) {
            swap(player.x, player.y, player.x - 1, player.y);
            player.x--;
        }
    }

    public void moveRight() {
        if (player.x < BOARD_SIZE - 1 && isCellEmpty(player.x + 1, player.y)) {
            swap(player.x, player.y, player.x + 1, player.y);
            player.x++;
        }
    }

    public boolean isCellEmpty(int i, int j) {
        if (i < 0 || i >= BOARD_SIZE || j < 0 || j >= BOARD_SIZE) {
            return false;
        }
        return board[i][j].isEmpty();
    }

    public synchronized void swap(int i, int j, int ii, int jj) {
        Cell temp = board[i][j];
        board[i][j] = board[ii][jj];
        board[ii][jj] = temp;
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    public boolean isOver() {
        return false;
    }

    public void resetGame() {
        board = new Cell[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = new EmptyCell();
            }
        }
        for (int i = 6; i < 14; i++) {
            board[i][5] = new Wall();
            board[i][15] = new Wall();
        }
        for (int i = 4; i < 16; i++) {
            board[3][i] = new Wall();
            board[16][i] = new Wall();
        }
        player = new Player();
        player.x = 10;
        player.y = 10;
        board[player.x][player.y] = player;
        enemies = new Enemy[10];
        for (int i = 0; i < 5; i++) {
            enemies[i * 2] = createEnemy(1 + i * 4, 1);
            enemies[i * 2 + 1] = createEnemy(1 + i * 4, 18);
        }
    }

    private Enemy createEnemy(int x, int y) {
        Enemy enemy = new Enemy();
        board[x][y] = enemy;
        enemy.x = x;
        enemy.y = y;
        return enemy;
    }

    public void updateEnemies() {
        for (Enemy enemy : enemies) {
            enemyPolicy.moveEnemy(this, enemy);
        }
    }
}
