package com.clientBase.observable;

import java.util.Observable;

public class ShopObservable extends Observable {

    //单例
    private static ShopObservable instance = null;

    public static ShopObservable getInstance() {

        if (null == instance) {
            instance = new ShopObservable();
        }
        return instance;
    }

    //通知观察者更新数据
    public void notifyStepChange(String msg) {
        setChanged();//设置changeFlag
        notifyObservers(msg);//通知观察者
    }

}