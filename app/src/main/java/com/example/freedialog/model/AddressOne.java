package com.example.freedialog.model;

import java.util.ArrayList;
import java.util.List;

public class AddressOne extends AddressModel{
    private List<AddressTwo> cityList;

    public List<AddressTwo> getCityList() {
        if(cityList==null){
            cityList=new ArrayList<>();
        }
        return cityList;
    }

    public void setCityList(List<AddressTwo> cityList) {
        this.cityList = cityList;
    }
}
