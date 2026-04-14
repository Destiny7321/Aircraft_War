package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;

import java.util.LinkedList;
import java.util.List;

import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.strategy.PromaxDoubleShoot;


public class PromaxEnemy extends AbstractAircraft {

    public PromaxEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp, new PromaxDoubleShoot());
    }

    // 移动：只向下飞
    @Override
    public void forward() {
        super.forward();
        if (locationY >= Main.WINDOW_HEIGHT ) {
            vanish();
        }
    }


}