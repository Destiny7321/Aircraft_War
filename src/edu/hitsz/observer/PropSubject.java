package edu.hitsz.observer;

import java.util.ArrayList;
import java.util.List;

public abstract class PropSubject {

    protected List<EnemyObserver> observers = new ArrayList<>();

    // 注册观察者
    public void attach(EnemyObserver observer) {
        observers.add(observer);
    }

    // 移除观察者
    public void detach(EnemyObserver observer) {
        observers.remove(observer);
    }

    // 通知所有对象炸弹生效
    public void notifyBomb() {
        for (EnemyObserver obs : observers) {
            obs.updateBomb();
        }
    }

    // 通知所有对象冰冻生效
    public void notifyFreeze() {
        for (EnemyObserver obs : observers) {
            obs.updateFreeze();
        }
    }
}