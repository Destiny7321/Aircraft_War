package edu.hitsz.bullet;

import edu.hitsz.observer.EnemyObserver;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 敌机子弹
 * @Author hitsz
 */
public class EnemyBullet extends BaseBullet implements EnemyObserver {

    public EnemyBullet(int locationX, int locationY, int speedX, int speedY, int power) {
        super(locationX, locationY, speedX, speedY, power);
    }

    @Override
    public void forward() {
        // 子弹向下飞
        this.locationY += this.speedY;
        this.locationX += this.speedX;
        // 飞出屏幕就消失
        if (locationY > 800 || locationX >512 || locationY < 0 || locationX < 0) {
            vanish();
        }
    }
    @Override
    public void updateBomb() {
        vanish();
    }

    @Override
    public void updateFreeze() {
        int oldSpeedY = speedY;
        speedY = 0;
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                speedY = oldSpeedY;
            }
        }, 5000);
    }
}
