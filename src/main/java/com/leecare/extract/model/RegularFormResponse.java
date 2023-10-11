package com.leecare.extract.model;

import java.util.HashMap;
import java.util.Map;

public class RegularFormResponse {

    Map<Integer, ResidentDetails> resultMap = new HashMap<>();

    public Map<Integer, ResidentDetails> getResultMap() {
        return resultMap;
    }

    public void setResultMap(Map<Integer, ResidentDetails> resultMap) {
        this.resultMap = resultMap;
    }


}
