package edu.hitsz.strategy;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.HeroBullet;
import java.util.ArrayList;
import java.util.List;

public class HeroCircleShoot implements ShootStrategy {
    @Override
    public List<BaseBullet> shoot(AbstractAircraft aircraft) {
        List<BaseBullet> res = new ArrayList<>();
        int x = aircraft.getLocationX();
        int y = aircraft.getLocationY();
        double radius = 6;

        for (int i = 0; i < 10; i++) {
            double angle = Math.PI * 2 * i / 10;
            int sx = (int) (radius * Math.cos(angle));
            int sy = (int) (radius * Math.sin(angle)) - 8;
            res.add(new HeroBullet(x, y, sx, sy, 10));
        }
        return res;
    }
}