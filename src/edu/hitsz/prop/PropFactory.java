package edu.hitsz.prop;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.AceEnemy;
import edu.hitsz.aircraft.BossEnemy;
import edu.hitsz.aircraft.ProEnemy;
import edu.hitsz.aircraft.PromaxEnemy;

public class PropFactory {

    public static AbstractProp createProp(AbstractAircraft enemy, int locationX, int locationY) {
        double rand = Math.random();

        if (enemy instanceof BossEnemy) {
            if (rand < 0.20) {
                return new BloodProp(locationX, locationY, 0, 10);
            } else if (rand < 0.40) {
                return new BulletProp(locationX, locationY, 0, 10);
            } else if (rand < 0.60) {
                return new BulletPlusProp(locationX, locationY, 0, 10);
            } else if (rand < 0.80) {
                return new BombProp(locationX, locationY, 0, 10);
            } else {
                return new FreezeProp(locationX, locationY, 0, 10);
            }
        }

        // 精英敌机 ProEnemy：不掉冰冻
        else if (enemy instanceof ProEnemy) {
            if (rand < 0.25) {
                return new BloodProp(locationX, locationY, 0, 10);
            } else if (rand < 0.50) {
                return new BulletProp(locationX, locationY, 0, 10);
            } else if (rand < 0.75) {
                return new BulletPlusProp(locationX, locationY, 0, 10);
            } else {
                return new BombProp(locationX, locationY, 0, 10);
            }
        }

        // 精锐 PromaxEnemy：不掉普通子弹
        else if (enemy instanceof PromaxEnemy) {
            if (rand < 0.25) {
                return new BloodProp(locationX, locationY, 0, 10);
            } else if (rand < 0.50) {
                return new BulletPlusProp(locationX, locationY, 0, 10);
            } else if (rand < 0.75) {
                return new BombProp(locationX, locationY, 0, 10);
            } else {
                return new FreezeProp(locationX, locationY, 0, 10);
            }
        }

        // 王牌 AceEnemy：5种道具全都掉
        else if (enemy instanceof AceEnemy) {
            if (rand < 0.20) {
                return new BloodProp(locationX, locationY, 0, 10);
            } else if (rand < 0.40) {
                return new BulletProp(locationX, locationY, 0, 10);
            } else if (rand < 0.60) {
                return new BulletPlusProp(locationX, locationY, 0, 10);
            } else if (rand < 0.80) {
                return new BombProp(locationX, locationY, 0, 10);
            } else {
                return new FreezeProp(locationX, locationY, 0, 10);
            }
        }

        return null;
    }
}