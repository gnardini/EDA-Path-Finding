package game.policies;

import game.GameOptions;
import game.cell.Enemy;
import game.cell.Player;

import static game.Constants.BOARD_SIZE;

public class ManhattanPolicy implements EnemyPolicy {

    @Override
    public void moveEnemy(GameOptions game, Enemy enemy) {
        int[] movement = targetMovement(enemy, game.getPlayer());
        int newX = enemy.x + movement[0];
        int newY = enemy.y + movement[1];
        if (newX >= 0 && newX < BOARD_SIZE && newY >= 0 && newY < BOARD_SIZE && game.isCellEmpty(newX, newY)) {
            game.swap(enemy.x, enemy.y, newX, newY);
            enemy.x = newX;
            enemy.y = newY;
        }
    }

    private int[] targetMovement(Enemy enemy, Player player) {
        int xDiff = enemy.x - player.x;
        int yDiff = enemy.y - player.y;
        if (Math.abs(xDiff) > Math.abs(yDiff)) {
            return new int[] { xDiff > 0 ? -1 : 1, 0 };
        } else {
            return new int[] { 0, yDiff > 0 ? -1 : 1 };
        }
    }
}
