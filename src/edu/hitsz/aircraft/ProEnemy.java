package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;
import edu.hitsz.observer.EnemyObserver;
import edu.hitsz.strategy.ProSingleShoot;

import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 精英敌机
 * 不可射击、不掉落道具
 * @author hitsz
 */
public class ProEnemy extends AbstractAircraft implements EnemyObserver {

    public ProEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp, new ProSingleShoot());
    }

    @Override
    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= Main.WINDOW_HEIGHT ) {
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
        }, 4000); // 4秒恢复
    }
}
