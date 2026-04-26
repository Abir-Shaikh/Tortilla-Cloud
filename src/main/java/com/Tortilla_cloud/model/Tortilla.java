package com.Tortilla_cloud.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.Data;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Tortilla {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Name must be at least 3 characters long")
    @Size(min = 3, message = "Name must be at least 3 characters long")
    private String name;

    private Date createdAt;

    @ManyToMany(targetEntity = Ingredient.class)
    @Size(min = 1 , message = "You must choose atleast one ingredient ")
    private List<Ingredient> ingredients ;

    @PrePersist
    void createdAt(){
        this.createdAt = createdAt;
    }
}

