package edu.hitsz.aircraft;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.strategy.HeroSingleShoot;
import edu.hitsz.strategy.HeroScatterShoot;
import edu.hitsz.strategy.HeroCircleShoot;

/**
 * 英雄飞机，游戏玩家操控
 * @author hitsz
 */
public class HeroAircraft extends AbstractAircraft {
    private volatile static HeroAircraft heroAircraft;

    // 构造方法传入策略，修改super
    private HeroAircraft(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp, new HeroSingleShoot());
    }

    public static HeroAircraft getHeroAircraft() {
        if (heroAircraft == null) {
            synchronized (HeroAircraft.class) {
                if (heroAircraft == null) {
                    heroAircraft = new HeroAircraft(
                            Main.WINDOW_WIDTH / 2,
                            Main.WINDOW_HEIGHT - ImageManager.HERO_IMAGE.getHeight(),
                            0, 0, 1000000);
                }
            }
        }
        return heroAircraft;
    }

    // 子弹威力
    private int power = 30;

    @Override
    public void forward() {
        // 英雄机由鼠标控制，不通过forward函数移动
    }

    // 删除原来的 shoot() 方法策略模式替代）

    // 加血方法
    public void increaseHp(int addHp) {
        this.hp += addHp;
        if (this.hp > this.maxHp) {
            this.hp = this.maxHp;
        }
    }


    // 普通火力：切换为 三发散射策略
    public void fireUp() {
        this.setShootStrategy(new HeroScatterShoot());
    }

    // 超级火力：切换为 环形10发策略
    public void fireMax() {
        this.setShootStrategy(new HeroCircleShoot());
    }
}