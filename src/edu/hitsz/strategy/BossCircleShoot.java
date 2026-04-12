package edu.hitsz.strategy;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import java.util.ArrayList;
import java.util.List;

public class BossCircleShoot implements ShootStrategy {
    @Override
    public List<BaseBullet> shoot(AbstractAircraft aircraft) {
        List<BaseBullet> res = new ArrayList<>();
        int x = aircraft.getLocationX();
        int y = aircraft.getLocationY();
        double radius = 8;

        for (int i = 0; i < 20; i++) {
            double angle = Math.PI * 2 * i / 20;
            int sx = (int) (radius * Math.cos(angle));
            int sy = (int) (radius * Math.sin(angle)) + 5;
            res.add(new EnemyBullet(x, y, sx, sy, 10));
        }
        return res;
    }
}