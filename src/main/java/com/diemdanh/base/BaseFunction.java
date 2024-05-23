package com.diemdanh.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.lang.reflect.Type;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BaseFunction {
    public static Map<String,String>stringToMap(String jsonString){
        Type type = com.google.gson.reflect.TypeToken.getParameterized(Map.class, String.class, Object.class).getType();

        // Convert JSON string to Map
        Gson gson = new Gson();
        Map<String, String> map = gson.fromJson(jsonString, type);
        return map;
    }
    public static Map<String,List<Long>> stringToListId(String jsonString) {
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);
        JsonArray idArray = jsonObject.getAsJsonArray("id");
        List<Long> listNumber=null;
        for (int i = 0; i < idArray.size(); i++) {
            Long idValue = idArray.get(i).getAsLong();
            System.out.println("ID value: " + idValue);
            listNumber.add(idValue);
        }
        Map<String,List<Long>> map = new HashMap<>();
        map.put("id",listNumber);
        return map;
    }
    public static Map<String,Boolean>stringToMapStringBoolean(String jsonString){
        Type type = com.google.gson.reflect.TypeToken.getParameterized(Map.class, String.class, Object.class).getType();

        // Convert JSON string to Map
        Gson gson = new Gson();
        Map<String, Boolean> map = gson.fromJson(jsonString, type);
        return map;
    }

    public static List<Integer> stringToListInteger(String arrayString) {
        Gson gson = new Gson();
        Integer[] array = gson.fromJson(arrayString, Integer[].class);
        if (array== null) return null;
        List<Integer> integerList = Arrays.asList(array);
        return integerList;
    }

    public static List<String> stringToListString(String arrayString) {
        Gson gson = new Gson();
        String[] array = gson.fromJson(arrayString, String[].class);
        if (array == null) return null;
        List<String> stringList = Arrays.asList(array);
        return stringList;
    }

    public static List<Long> convertStringToListLong(String numberString) {
        numberString = numberString.substring(1, numberString.length() - 1);
        String[] stringArray = numberString.split(",");

        // Tạo danh sách để lưu trữ các số nguyên
        List<Long> numberList = new ArrayList<>();

        // Chuyển đổi từng phần tử từ chuỗi ký tự sang số nguyên và thêm vào danh sách
        for (String s : stringArray) {
            numberList.add(Long.parseLong(s.trim()));
        }

        return numberList;
    }
}
