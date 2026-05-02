package com.Tortilla_cloud.controller.restController;

import com.Tortilla_cloud.model.Ingredient;
import com.Tortilla_cloud.repository.IngredientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/ingredients")
@CrossOrigin(origins = "*")
public class IngredientController {

    private final IngredientRepository ingredientRepository;

    public IngredientController(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    //get all ingredient
    @GetMapping
    public Iterable<Ingredient> allIngredients(){
        log.info("Fetching all ingredients");
        return ingredientRepository.findAll();
    }

    //get ingredient by id
    public Ingredient getIngredientById(@PathVariable String id){
        log.info("Fetching ingredient with id: {}" , id);
        return ingredientRepository.findById(id).orElse(null);
    }

    //post , create new ingredient
    @PostMapping
    public ResponseEntity<Ingredient> saveIngredient(@RequestBody Ingredient ingredient){
        log.info("Creating new Ingredient: {}" , ingredient.getName());
        Ingredient save = ingredientRepository.save(ingredient);
        return new ResponseEntity<>(save , HttpStatus.CREATED);
    }
}
