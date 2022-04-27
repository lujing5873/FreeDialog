package com.example.freedialog.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class GsonUtils {
    Gson gson;
    private GsonUtils(){
//        GsonBuilder builder = new GsonBuilder();
//        builder.excludeFieldsWithoutExposeAnnotation();
        gson=new Gson();
    }
    private static class Holder{
        private final static GsonUtils INSTANCE=new GsonUtils();

    }
    public static final GsonUtils getInstance(){
        return Holder.INSTANCE;
    }

    public Gson getGson() {
        return gson;
    }

    public String toGson(Object obj){
        return gson.toJson(obj);
    }
    public <T> T fromJson(String json,Class<T> tClass){
        return gson.fromJson(json,tClass);
    }



    /**
     * @param json list的序列化字符串
     * @param <T>  T类型
     * @return List<T>
     */
    public <T> List<T> toList(String json, Class<T> clazz) {
        return gson.fromJson(json, TypeToken.getParameterized(List.class, clazz).getType());
    }

}
