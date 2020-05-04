package com.clientBase.observable;

import java.util.Observable;

public class GroupObservable extends Observable {

    //单例
    private static GroupObservable instance = null;

    public static GroupObservable getInstance() {

        if (null == instance) {
            instance = new GroupObservable();
        }
        return instance;
    }

    //通知观察者更新数据
    public void notifyStepChange(String msg) {
        setChanged();//设置changeFlag
        notifyObservers(msg);//通知观察者
    }

}