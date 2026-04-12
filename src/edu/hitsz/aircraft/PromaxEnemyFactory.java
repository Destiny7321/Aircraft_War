package edu.hitsz.aircraft;

import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;

public class PromaxEnemyFactory implements EnemyFactory {
    @Override
    public AbstractAircraft createEnemy() {
        return new PromaxEnemy(
                (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.PROMAX_ENEMY_IMAGE.getWidth())),
                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
                0, 10, 50
        );
    }
}