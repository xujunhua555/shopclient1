package com.clientBase.observable;

import java.util.Observable;

public class ShopSendObservable extends Observable {

    //单例
    private static ShopSendObservable instance = null;

    public static ShopSendObservable getInstance() {

        if (null == instance) {
            instance = new ShopSendObservable();
        }
        return instance;
    }

    //通知观察者更新数据
    public void notifyStepChange(String msg) {
        setChanged();//设置changeFlag
        notifyObservers(msg);//通知观察者
    }

}