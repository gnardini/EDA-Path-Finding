package game.policies;

import game.GameOptions;
import game.cell.Enemy;

public interface EnemyPolicy {
    void moveEnemy(GameOptions game, Enemy enemy);
}
