package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.Main;
import edu.hitsz.basic.AbstractFlyingObject;

import java.awt.image.BufferedImage;

public abstract class AbstractProp extends AbstractFlyingObject {

    public AbstractProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    /**
     * 道具生效方法：由具体道具类实现
     */
    @Override
    public void forward() {
        // 道具缓慢向下移动
        this.locationY += speedY;
        this.locationX += speedX;

        // 飞出屏幕就消失
        if (locationY > Main.WINDOW_HEIGHT || locationY < 0
                || locationX > Main.WINDOW_WIDTH  || locationX < 0) {
            vanish();
        }
    }
    public abstract void apply(HeroAircraft heroAircraft);
}