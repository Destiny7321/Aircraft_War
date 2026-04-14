package edu.hitsz.prop;


import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.AceEnemy;
import edu.hitsz.aircraft.ProEnemy;
import edu.hitsz.aircraft.PromaxEnemy;
import edu.hitsz.application.ImageManager;

public class PropFactory {
    /**
     * 简单工厂：根据概率随机生成3种道具（加血/火力/超级火力）
     * @param locationX 道具生成X坐标（敌机坠毁位置）
     * @param locationY 道具生成Y坐标（敌机坠毁位置）
     * @return 生成的道具实例
     */
        public static AbstractProp createProp(AbstractAircraft enemy, int locationX, int locationY) {
            double rand = Math.random();

            // 精英敌机 ProEnemy：不掉冰冻

            if (enemy instanceof ProEnemy) {
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