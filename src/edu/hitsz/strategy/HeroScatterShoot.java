package edu.hitsz.strategy;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.HeroBullet;
import java.util.ArrayList;
import java.util.List;

public class HeroScatterShoot implements ShootStrategy {
    @Override
    public List<BaseBullet> shoot(AbstractAircraft aircraft) {
        List<BaseBullet> res = new ArrayList<>();
        int x = aircraft.getLocationX();
        int y = aircraft.getLocationY() - 20;
        int speedY = aircraft.getSpeedY() - 8;

        res.add(new HeroBullet(x - 18, y, -2, speedY, 10));
        res.add(new HeroBullet(x, y, 0, speedY, 10));
        res.add(new HeroBullet(x + 18, y, +2, speedY, 10));
        return res;
    }
}