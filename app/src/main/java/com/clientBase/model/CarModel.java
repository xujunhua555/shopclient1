package com.clientBase.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2020/3/25.
 */

public class CarModel implements Serializable {

    private String   carShopId;
    private String   carUserId;
    private String   carId;



    private ShopModel queryShop;

    public String getCarShopId() {
        return carShopId;
    }

    public void setCarShopId(String carShopId) {
        this.carShopId = carShopId;
    }

    public String getCarUserId() {
        return carUserId;
    }

    public void setCarUserId(String carUserId) {
        this.carUserId = carUserId;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public ShopModel getQueryShop() {
        return queryShop;
    }

    public void setQueryShop(ShopModel queryShop) {
        this.queryShop = queryShop;
    }
}
