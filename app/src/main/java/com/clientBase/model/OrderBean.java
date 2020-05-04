package com.clientBase.model;

import java.io.Serializable;

/**
 * Created by pony on 2018/9/17.
 */

public class OrderBean implements Serializable {


    /**
     * shopMessage : {"shopType":"食物","shopTime":"2018-09-12 15:13:18","shopPrice":"15","shopName":"骨头","shopId":2,"shopImg":"1501153406372.jpg","shopContent":"宠物店（pet shop）是专门为宠物提供宠物用品零售、宠物美容、宠物寄养、宠物活体销售的场所。其经营项目一般包括宠物用品超市、活体销售、宠物美容、宠物寄养、宠物医疗、宠物乐园、宠物摄影、待产养护。"}
     * orderMessageMoney : 15
     * orderMessageId : 2
     * orderUserId : 106
     * orderTime : 2018-09-17 15:47:00
     * orderUserName : 小丸子
     * orderId : 1
     * orderMessageName : 骨头
     */

    private ShopBean shopMessage;
    private String orderMessageMoney;
    private String orderMessageId;
    private String orderUserId;
    private String orderTime;
    private String orderUserName;
    private int orderId;
    private String orderMessageName;
    private String orderAddress;
    private String orderState;

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public String getOrderAddress() {
        return orderAddress;
    }

    public void setOrderAddress(String orderAddress) {
        this.orderAddress = orderAddress;
    }

    public ShopBean getShopMessage() {
        return shopMessage;
    }

    public void setShopMessage(ShopBean shopMessage) {
        this.shopMessage = shopMessage;
    }

    public String getOrderMessageMoney() {
        return orderMessageMoney;
    }

    public void setOrderMessageMoney(String orderMessageMoney) {
        this.orderMessageMoney = orderMessageMoney;
    }

    public String getOrderMessageId() {
        return orderMessageId;
    }

    public void setOrderMessageId(String orderMessageId) {
        this.orderMessageId = orderMessageId;
    }

    public String getOrderUserId() {
        return orderUserId;
    }

    public void setOrderUserId(String orderUserId) {
        this.orderUserId = orderUserId;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getOrderUserName() {
        return orderUserName;
    }

    public void setOrderUserName(String orderUserName) {
        this.orderUserName = orderUserName;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getOrderMessageName() {
        return orderMessageName;
    }

    public void setOrderMessageName(String orderMessageName) {
        this.orderMessageName = orderMessageName;
    }
}
