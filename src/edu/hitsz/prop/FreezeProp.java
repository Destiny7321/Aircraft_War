package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;

public class FreezeProp extends AbstractProp {

    public FreezeProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void apply(HeroAircraft heroAircraft) {
        // 【只打印，不实现功能】
        System.out.println("freeze all");
    }
}