package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;

import java.util.LinkedList;
import java.util.List;

import edu.hitsz.bullet.EnemyBullet;


public class PromaxEnemy extends AbstractAircraft {

    public PromaxEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
    }

    // 移动：只向下飞
    @Override
    public void forward() {
        super.forward();
        if (locationY >= Main.WINDOW_HEIGHT ) {
            vanish();
        }
    }

    // 攻击：直线 → 两排子弹（你要求的）
    @Override
    public List<BaseBullet> shoot() {
        List<BaseBullet> res = new LinkedList<>();
        int x = this.getLocationX();
        int y = this.getLocationY() + 10;
        int speedY = this.getSpeedY() + 5;

        // 直线两发
        BaseBullet bullet1 = new EnemyBullet(x - 18, y, 0, speedY, 10);
        BaseBullet bullet2 = new EnemyBullet(x + 18, y, 0, speedY, 10);

        res.add(bullet1);
        res.add(bullet2);
        return res;
    }
}