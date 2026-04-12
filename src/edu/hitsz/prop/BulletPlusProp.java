package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;

public class BulletPlusProp extends AbstractProp {

    public BulletPlusProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void apply(HeroAircraft heroAircraft) {
        heroAircraft.fireMax();
    }
}