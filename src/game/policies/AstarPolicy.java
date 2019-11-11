package game.policies;

import game.GameOptions;
import game.cell.Enemy;
import game.cell.Player;

import java.util.Comparator;
import java.util.PriorityQueue;

import static game.Constants.BOARD_SIZE;

public class AstarPolicy implements EnemyPolicy {

    @Override
    public void moveEnemy(GameOptions game, Enemy enemy) {
        Player player = game.getPlayer();
        PriorityQueue<Position> search = new PriorityQueue<>(Comparator.comparingInt(pos -> pos.cost(player)));
        boolean[][] visited = new boolean[BOARD_SIZE][BOARD_SIZE];
        addStartingPositions(search, enemy);
        while (!search.isEmpty()) {
            Position position = search.remove();
            if (position.x == player.x && position.y == player.y) {
                makeEnemyMovement(enemy, position, game);
                return;
            }
            if (game.isCellEmpty(position.x, position.y) && !visited[position.x][position.y]) {
                visited[position.x][position.y] = true;
                search.add(position.moving(1, 0));
                search.add(position.moving(-1, 0));
                search.add(position.moving(0, 1));
                search.add(position.moving(0, -1));
            }
        }
    }

    private void addStartingPositions(PriorityQueue<Position> search, Enemy enemy) {
        search.add(new Position(enemy.x + 1, enemy.y, 1, 0));
        search.add(new Position(enemy.x - 1, enemy.y, -1, 0));
        search.add(new Position(enemy.x, enemy.y + 1, 0, 1));
        search.add(new Position(enemy.x, enemy.y - 1, 0, -1));
    }

    private void makeEnemyMovement(Enemy enemy, Position position, GameOptions game) {
        int newX = enemy.x + position.initialXDiff;
        int newY = enemy.y + position.initialYDiff;
        if (game.isCellEmpty(newX, newY)) {
            game.swap(enemy.x, enemy.y, newX, newY);
            enemy.x = newX;
            enemy.y = newY;
        }
    }

    private static class Position {
        int initialXDiff;
        int initialYDiff;
        int x;
        int y;

        public Position(int x, int y, int initialXDiff, int initialYDiff) {
            this.x = x;
            this.y = y;
            this.initialXDiff = initialXDiff;
            this.initialYDiff = initialYDiff;
        }

        Position moving(int x, int y) {
            return new Position(this.x + x, this.y + y, initialXDiff, initialYDiff);
        }

        int cost(Player player) {
            return Math.abs(x - player.x) + Math.abs(y - player.y);
        }
    }
}
