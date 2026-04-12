package edu.hitsz.aircraft;

import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;

public class AceEnemyFactory implements EnemyFactory {
    @Override
    public AbstractAircraft createEnemy() {
        //TODO:speedx调成随机左右方向
        return new AceEnemy(
                (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.ACE_ENEMY_IMAGE.getWidth())),
                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
                1, 10, 100
        );
    }
}