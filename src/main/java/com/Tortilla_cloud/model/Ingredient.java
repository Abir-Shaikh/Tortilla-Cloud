package com.Tortilla_cloud.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Ingredient {
    private final String id;
    private final String name;
    private final Type type;

    public static enum Type{
        WRAP , PROTEIN , VEGGIES , CHEESE , SAUCE , BAMBARBOLA , CUCUBUMBER
    }
}
