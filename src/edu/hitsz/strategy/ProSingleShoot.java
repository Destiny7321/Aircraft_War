package edu.hitsz.strategy;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import java.util.ArrayList;
import java.util.List;

public class ProSingleShoot implements ShootStrategy {
    @Override
    public List<BaseBullet> shoot(AbstractAircraft aircraft) {
        List<BaseBullet> res = new ArrayList<>();
        int x = aircraft.getLocationX();
        int y = aircraft.getLocationY() + 20;
        int speedY = aircraft.getSpeedY() + 8;
        res.add(new EnemyBullet(x, y, 0, speedY, 10));
        return res;
    }
}