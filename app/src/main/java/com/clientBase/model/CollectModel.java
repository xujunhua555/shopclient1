package com.clientBase.model;

import java.io.Serializable;

/**
 * Created by Pony on 2020/4/14.
 */

public class CollectModel implements Serializable {


    /**
     * collectUserId : 92
     * collectTime : 2020-04-14 20:36
     * collectMessageId : 25
     * collectId : 2
     * queryShop : {"shopDPName":"","isBao":"2","shopTypeName":"武侠","shopImg":"20200116151130.jpg","shopCreatime":"2020-04-14 16:54","shopUserName":"小哲","shopTypeId":"22","shopMessage":"睁开眼睛，看着自己的双腿，它们陪你走了一生的路，可是，你们什么时候注意过它们的存在？","shopId":25,"shopName":"小说","shopMoney":"66","shopFlag":"1","shopPhone":"","shopIsIm":"1","shopIsSend":"1","shopQQorWX":"","shopUserId":"102","shopRecycling":"1"}
     */

    private int collectUserId;
    private String collectTime;
    private int collectMessageId;
    private int collectId;
    private ShopModel queryShop;


    public int getCollectUserId() {
        return collectUserId;
    }

    public void setCollectUserId(int collectUserId) {
        this.collectUserId = collectUserId;
    }

    public String getCollectTime() {
        return collectTime;
    }

    public void setCollectTime(String collectTime) {
        this.collectTime = collectTime;
    }

    public int getCollectMessageId() {
        return collectMessageId;
    }

    public void setCollectMessageId(int collectMessageId) {
        this.collectMessageId = collectMessageId;
    }

    public int getCollectId() {
        return collectId;
    }

    public void setCollectId(int collectId) {
        this.collectId = collectId;
    }

    public ShopModel getQueryShop() {
        return queryShop;
    }

    public void setQueryShop(ShopModel queryShop) {
        this.queryShop = queryShop;
    }
}
