package com.Tortilla_cloud.model;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.Data;


import java.util.ArrayList;
import java.util.List;

@Data
public class Tortilla {

    @NotBlank(message = "Name must be at least 3 characters long")
    @Size(min = 3, message = "Name must be at least 3 characters long")
    private String name;
    @Size(min = 1 , message = "You must choose atleast one ingredient ")
    private List<String> ingredients = new ArrayList<>();
}

