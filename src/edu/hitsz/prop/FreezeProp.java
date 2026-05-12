package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.observer.PropSubject;

public class FreezeProp extends AbstractProp {

    private PropSubject propSubject;

    public FreezeProp(int locationX, int locationY, int speedX, int speedY, PropSubject propSubject) {
        super(locationX, locationY, speedX, speedY);
        this.propSubject = propSubject;
    }

    @Override
    public void apply(HeroAircraft hero) {
        propSubject.notifyFreeze();
    }
}