package edu.hitsz.aircraft;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.application.Main;
import java.util.LinkedList;
import java.util.List;

public class AceEnemy extends AbstractAircraft {

    public AceEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
    }

    @Override
    public void forward() {
        // 先向下移动
        super.forward();

        // 左右移动
        locationX += speedX;

        // 边界反弹
        if (locationX <= 0 || locationX >= Main.WINDOW_WIDTH) {
            speedX = -speedX;
        }
    }

    @Override
    public List<BaseBullet> shoot() {
        List<BaseBullet> res = new LinkedList<>();

        int x = this.locationX;
        int y = this.locationY + 10;
        int speedY = this.speedY + 6;

        // 扇形 3 发
        res.add(new EnemyBullet(x - 25, y, -3, speedY, 10));
        res.add(new EnemyBullet(x,      y,  0, speedY, 10));
        res.add(new EnemyBullet(x + 25, y,  3, speedY, 10));

        return res;
    }
}