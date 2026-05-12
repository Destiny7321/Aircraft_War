package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.observer.PropSubject;

public class BombProp extends AbstractProp {

    private PropSubject propSubject;

    // 构造方法：和你原来的格式完全一样（4个参数）
    public BombProp(int locationX, int locationY, int speedX, int speedY, PropSubject propSubject) {
        super(locationX, locationY, speedX, speedY);
        this.propSubject = propSubject;
    }

    @Override
    public void apply(HeroAircraft hero) {
        propSubject.notifyBomb();
    }
}