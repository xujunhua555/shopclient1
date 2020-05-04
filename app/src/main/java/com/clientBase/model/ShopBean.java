package com.clientBase.model;

import java.io.Serializable;

/**
 * Created by pony on 2018/9/12.
 */

public class ShopBean implements Serializable{


    /**
     * shopType : 球鞋
     * shopMessage : 篮球鞋即SNEAKER，同时也将球鞋爱好者融入到SNEAKER一词中，使得SNEAKER有更宽泛的定义。 篮球是一项剧烈运动，为了能应付激烈的运动，对于一双篮球鞋来讲，就需要有很好的耐久性，支撑性、稳定性，舒适性和良好的减震作用；
     * shopTime : 2018-11-25 20:18
     * shopName : NIKE球鞋
     * shopId : 1
     * shopMoney : 500
     * shopState : 1
     * shopImg : timg-4.jpeg
     * shopUserId : 106
     * shopUserName : 小丸子
     */

    private String shopType;
    private String shopMessage;
    private String shopTime;
    private String shopName;
    private int shopId;
    private String shopMoney;
    private String shopState;
    private String shopImg;
    private String shopUserId;
    private String shopUserName;

    public String getShopType() {
        return shopType;
    }

    public void setShopType(String shopType) {
        this.shopType = shopType;
    }

    public String getShopMessage() {
        return shopMessage;
    }

    public void setShopMessage(String shopMessage) {
        this.shopMessage = shopMessage;
    }

    public String getShopTime() {
        return shopTime;
    }

    public void setShopTime(String shopTime) {
        this.shopTime = shopTime;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public String getShopMoney() {
        return shopMoney;
    }

    public void setShopMoney(String shopMoney) {
        this.shopMoney = shopMoney;
    }

    public String getShopState() {
        return shopState;
    }

    public void setShopState(String shopState) {
        this.shopState = shopState;
    }

    public String getShopImg() {
        return shopImg;
    }

    public void setShopImg(String shopImg) {
        this.shopImg = shopImg;
    }

    public String getShopUserId() {
        return shopUserId;
    }

    public void setShopUserId(String shopUserId) {
        this.shopUserId = shopUserId;
    }

    public String getShopUserName() {
        return shopUserName;
    }

    public void setShopUserName(String shopUserName) {
        this.shopUserName = shopUserName;
    }
}
