package edu.hitsz.aircraft;

import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;

public class ProEnemyFactory implements EnemyFactory {
    @Override
    public AbstractAircraft createEnemy() {
        return new ProEnemy(
                (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.PRO_ENEMY_IMAGE.getWidth())),
                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
                0, 10, 50
        );
    }
}