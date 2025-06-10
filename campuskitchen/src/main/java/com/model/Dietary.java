package com.model;

import java.util.ArrayList;

/**
 * Dietary restrictions users may have
 */
public enum Dietary {
    VEGAN,
    VEGETARIAN,
    KOSHER,
    PALEO,
    GLUTEN_FREE;

    public static ArrayList<Dietary> emptyList() {
        return new ArrayList<>();
    }
}
