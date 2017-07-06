package com.shanghaigm.dms.model.entity.ck;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import java.io.Serializable;

/**
 * Created by Tom on 2017/6/26.
 */

public class OrderAddSearchInfo extends BaseObservable implements Serializable {
    private String name;
    private String tel;
    private String company;
    private String detailed_address;
    private int customerCode;
    public OrderAddSearchInfo() {
    }

    public OrderAddSearchInfo(String name, String tel, String company,String detailed_address,int customerCode) {
        this.name = name;
        this.company = company;
        this.tel = tel;
        this.detailed_address = detailed_address;
        this.customerCode = customerCode;
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Bindable
    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    @Bindable
    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDetailed_address() {
        return detailed_address;
    }

    public void setDetailed_address(String detailed_address) {
        this.detailed_address = detailed_address;
    }

    public int getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(int customerCode) {
        this.customerCode = customerCode;
    }
}