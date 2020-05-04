package com.clientBase.observable;

import java.util.Observable;

public class CircleObservable extends Observable {

    //单例
    private static CircleObservable instance = null;

    public static CircleObservable getInstance() {

        if (null == instance) {
            instance = new CircleObservable();
        }
        return instance;
    }

    //通知观察者更新数据
    public void notifyStepChange(String msg) {
        setChanged();//设置changeFlag
        notifyObservers(msg);//通知观察者
    }

}