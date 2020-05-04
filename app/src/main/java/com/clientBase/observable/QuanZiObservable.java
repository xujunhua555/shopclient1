package com.clientBase.observable;

import java.util.Observable;

public class QuanZiObservable extends Observable {

    //单例
    private static QuanZiObservable instance = null;

    public static QuanZiObservable getInstance() {

        if (null == instance) {
            instance = new QuanZiObservable();
        }
        return instance;
    }

    //通知观察者更新数据
    public void notifyStepChange(String msg) {
        setChanged();//设置changeFlag
        notifyObservers(msg);//通知观察者
    }

}