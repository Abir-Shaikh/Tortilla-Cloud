package com.Tortilla_cloud.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.*;

@Data
//@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED , force = true)
@Entity
public class Ingredient {
    @Id
    private String id;
    private String name;

    @Enumerated(EnumType.STRING)
    private Type type;
}
