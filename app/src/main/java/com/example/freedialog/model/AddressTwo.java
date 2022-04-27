package com.example.freedialog.model;

import java.util.ArrayList;
import java.util.List;

public class AddressTwo extends AddressModel{
    private List<AddressModel> areaList;

    public List<AddressModel> getAreaList() {
        if(areaList==null){
            areaList=new ArrayList<>();
        }
        return areaList;
    }

    public void setAreaList(List<AddressModel> areaList) {
        this.areaList = areaList;
    }
}
