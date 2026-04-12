package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;

public class BloodProp extends AbstractProp {
    private static final int HEAL_AMOUNT = 20;

    public BloodProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void apply(HeroAircraft heroAircraft) {
        heroAircraft.increaseHp(HEAL_AMOUNT);
    }
}