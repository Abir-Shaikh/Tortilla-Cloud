package com.Tortilla_cloud.repository;

import com.Tortilla_cloud.model.Ingredient;
import com.Tortilla_cloud.model.Type;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IngredientRepository extends CrudRepository<Ingredient , String> {
    List<Ingredient> findByType(Type type);
}
