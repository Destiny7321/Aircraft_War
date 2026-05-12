package edu.hitsz.prop;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.AceEnemy;
import edu.hitsz.aircraft.BossEnemy;
import edu.hitsz.aircraft.ProEnemy;
import edu.hitsz.aircraft.PromaxEnemy;
import edu.hitsz.observer.PropSubject;

public class PropFactory {

    // 多加一个参数：PropSubject
    public static AbstractProp createProp(AbstractAircraft enemy, int locationX, int locationY, PropSubject propSubject) {
        double rand = Math.random();

        if (enemy instanceof BossEnemy) {
            if (rand < 0.20) {
                return new BloodProp(locationX, locationY, 0, 10);
            } else if (rand < 0.40) {
                return new BulletProp(locationX, locationY, 0, 10);
            } else if (rand < 0.60) {
                return new BulletPlusProp(locationX, locationY, 0, 10);
            } else if (rand < 0.80) {
                return new BombProp(locationX, locationY, 0, 10, propSubject);
            } else {
                return new FreezeProp(locationX, locationY, 0, 10, propSubject);
            }
        }

        else if (enemy instanceof ProEnemy) {
            if (rand < 0.25) {
                return new BloodProp(locationX, locationY, 0, 10);
            } else if (rand < 0.50) {
                return new BulletProp(locationX, locationY, 0, 10);
            } else if (rand < 0.75) {
                return new BulletPlusProp(locationX, locationY, 0, 10);
            } else {
                return new BombProp(locationX, locationY, 0, 10, propSubject);
            }
        }

        else if (enemy instanceof PromaxEnemy) {
            if (rand < 0.25) {
                return new BloodProp(locationX, locationY, 0, 10);
            } else if (rand < 0.50) {
                return new BulletPlusProp(locationX, locationY, 0, 10);
            } else if (rand < 0.75) {
                return new BombProp(locationX, locationY, 0, 10, propSubject);
            } else {
                return new FreezeProp(locationX, locationY, 0, 10, propSubject);
            }
        }

        else if (enemy instanceof AceEnemy) {
            if (rand < 0.20) {
                return new BloodProp(locationX, locationY, 0, 10);
            } else if (rand < 0.40) {
                return new BulletProp(locationX, locationY, 0, 10);
            } else if (rand < 0.60) {
                return new BulletPlusProp(locationX, locationY, 0, 10);
            } else if (rand < 0.80) {
                return new BombProp(locationX, locationY, 0, 10, propSubject);
            } else {
                return new FreezeProp(locationX, locationY, 0, 10, propSubject);
            }
        }

        return null;
    }
}