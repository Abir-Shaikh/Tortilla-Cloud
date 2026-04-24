package com.Tortilla_cloud.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Tortilla {
    private String name;
    private List<String> ingredients = new ArrayList<>();
}

