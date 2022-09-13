package com.example.freedialog.utils;

import com.example.freedialog.model.AddressModel;
import com.example.freedialog.model.AddressOne;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TimeUtils {
    public static List<AddressModel> getYear(int start, int end){
        List<AddressModel> year=new ArrayList<>();
        for (int i=start;i<=end;i++){
            AddressModel tmp=new AddressOne();
            tmp.setCode(i+""); //年份
            tmp.setName(i+"年");//年份
            year.add(tmp);
        }
        return year;
    }


    public static List<AddressModel> getMonth(){
        List<AddressModel> year=new ArrayList<>();
        for (int i=1;i<=12;i++){
            AddressModel tmp=new AddressOne();
            tmp.setCode(i+""); //年份
            tmp.setName(i+"月");//年份
            year.add(tmp);
        }
        return year;
    }


    public static List<AddressModel> getDays(int year,int month){
        List<AddressModel> days=new ArrayList<>();
        Calendar data=Calendar.getInstance();
        data.set(Calendar.YEAR,year);
        data.set(Calendar.MONTH,month-1);
        data.set(Calendar.DATE,1);
        data.roll(Calendar.DATE,-1);
        int day= data.get(Calendar.DATE);
        for (int i=1;i<=day;i++){
            AddressModel tmp=new AddressOne();
            tmp.setCode(i+""); //年份
            tmp.setName(i+"日");//年份
            days.add(tmp);
        }
        return days;
    }

}
