package com.clientBase.model;

import java.io.Serializable;

/**
 * Created by pony on 2019/11/15.
 */

public class PriceModel implements Serializable {

    private String priceId;
    private String priceMoney;
    private String priceShopId;
    private String priceShopName;
    private String priceUserId;
    private String priceUserName;
    private String priceTime;

    public String getPriceId() {
        return priceId;
    }

    public void setPriceId(String priceId) {
        this.priceId = priceId;
    }

    public String getPriceMoney() {
        return priceMoney;
    }

    public void setPriceMoney(String priceMoney) {
        this.priceMoney = priceMoney;
    }

    public String getPriceShopId() {
        return priceShopId;
    }

    public void setPriceShopId(String priceShopId) {
        this.priceShopId = priceShopId;
    }

    public String getPriceShopName() {
        return priceShopName;
    }

    public void setPriceShopName(String priceShopName) {
        this.priceShopName = priceShopName;
    }

    public String getPriceUserId() {
        return priceUserId;
    }

    public void setPriceUserId(String priceUserId) {
        this.priceUserId = priceUserId;
    }

    public String getPriceUserName() {
        return priceUserName;
    }

    public void setPriceUserName(String priceUserName) {
        this.priceUserName = priceUserName;
    }

    public String getPriceTime() {
        return priceTime;
    }

    public void setPriceTime(String priceTime) {
        this.priceTime = priceTime;
    }
}
