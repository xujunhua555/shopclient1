package com.clientBase.observable;

import java.util.Observable;

public class HuoDongObservable extends Observable {

    //单例
    private static HuoDongObservable instance = null;

    public static HuoDongObservable getInstance() {

        if (null == instance) {
            instance = new HuoDongObservable();
        }
        return instance;
    }

    //通知观察者更新数据
    public void notifyStepChange(String msg) {
        setChanged();//设置changeFlag
        notifyObservers(msg);//通知观察者
    }

}