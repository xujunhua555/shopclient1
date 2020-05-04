package com.clientBase.model;

import java.io.Serializable;

/**
 * Created by Pony on 2020/2/16.
 */

public class ExchangeModel implements Serializable {




    private String  exchangeShopId;
    private String  exchangeShopUserId;
    private String  exchangeMyShopId;
    private String  exchangeMyUserId;

    private String  exchangeId;
    private String  exchangeState;
    private String  exchangeTime;

    private ShopModel myShop;
    private ShopModel imShop;


    public String getExchangeShopId() {
        return exchangeShopId;
    }

    public void setExchangeShopId(String exchangeShopId) {
        this.exchangeShopId = exchangeShopId;
    }

    public String getExchangeShopUserId() {
        return exchangeShopUserId;
    }

    public void setExchangeShopUserId(String exchangeShopUserId) {
        this.exchangeShopUserId = exchangeShopUserId;
    }

    public String getExchangeMyShopId() {
        return exchangeMyShopId;
    }

    public void setExchangeMyShopId(String exchangeMyShopId) {
        this.exchangeMyShopId = exchangeMyShopId;
    }

    public String getExchangeMyUserId() {
        return exchangeMyUserId;
    }

    public void setExchangeMyUserId(String exchangeMyUserId) {
        this.exchangeMyUserId = exchangeMyUserId;
    }

    public String getExchangeId() {
        return exchangeId;
    }

    public void setExchangeId(String exchangeId) {
        this.exchangeId = exchangeId;
    }

    public String getExchangeState() {
        return exchangeState;
    }

    public void setExchangeState(String exchangeState) {
        this.exchangeState = exchangeState;
    }

    public String getExchangeTime() {
        return exchangeTime;
    }

    public void setExchangeTime(String exchangeTime) {
        this.exchangeTime = exchangeTime;
    }

    public ShopModel getMyShop() {
        return myShop;
    }

    public void setMyShop(ShopModel myShop) {
        this.myShop = myShop;
    }

    public ShopModel getImShop() {
        return imShop;
    }

    public void setImShop(ShopModel imShop) {
        this.imShop = imShop;
    }
}
