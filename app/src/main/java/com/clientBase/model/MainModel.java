package com.clientBase.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2020/3/25.
 */

public class MainModel implements Serializable {


    private List<UserModel> dian;
    private List<ShopModel> bao;
    private  List<ShopModel> shop;


    public List<UserModel> getDian() {
        return dian;
    }

    public void setDian(List<UserModel> dian) {
        this.dian = dian;
    }

    public List<ShopModel> getBao() {
        return bao;
    }

    public void setBao(List<ShopModel> bao) {
        this.bao = bao;
    }

    public List<ShopModel> getShop() {
        return shop;
    }

    public void setShop(List<ShopModel> shop) {
        this.shop = shop;
    }
}
