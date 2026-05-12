package edu.hitsz.aircraft;

import edu.hitsz.observer.EnemyObserver;
import edu.hitsz.strategy.BossCircleShoot;

public class BossEnemy extends AbstractAircraft implements EnemyObserver {

    public BossEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp, new BossCircleShoot());
    }

    @Override
    public void forward() {
        // BOSS 只左右移动，不向下
        locationX += speedX;

        // 左右碰壁反弹
        if (locationX < 50) {
            speedX = Math.abs(speedX);
        }
        if (locationX > 460) {
            speedX = -Math.abs(speedX);
        }
    }
    @Override
    public void updateBomb() {
        // 不受影响
    }

    @Override
    public void updateFreeze() {
        // 不受影响
    }
}