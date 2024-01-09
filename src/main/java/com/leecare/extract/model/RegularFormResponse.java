/*
 * RegularFormResponse.java
 *
 * Copyright Â© 2023 Leecare. All Rights Reserved.
 */

package com.leecare.extract.model;

import java.util.HashMap;
import java.util.Map;

/**
 * This is used for a RegularFormResponse.
 *
 * @author jjoy
 */
public class RegularFormResponse {
    private Map<Integer, ResidentDetails> resultMap = new HashMap<>();

    /**
     * @return resultMap
     */
    public Map<Integer, ResidentDetails> getResultMap() {
        return resultMap;
    }

    /**
     * Sets the resultMap.
     * 
     * @param resultMap resultMap
     */
    public void setResultMap(Map<Integer, ResidentDetails> resultMap) {
        this.resultMap = resultMap;
    }
}
