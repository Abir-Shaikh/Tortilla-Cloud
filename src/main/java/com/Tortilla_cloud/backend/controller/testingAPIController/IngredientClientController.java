package com.Tortilla_cloud.controller.testingAPIController;

import com.Tortilla_cloud.model.Ingredient;
import com.Tortilla_cloud.model.Type;
import com.Tortilla_cloud.service.IngredientServiceClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/test")
@CrossOrigin(origins = "*")
public class IngredientClientController {

    private final IngredientServiceClient ingredientServiceClient;

    public IngredientClientController(IngredientServiceClient ingredientServiceClient) {
        this.ingredientServiceClient = ingredientServiceClient;
    }

    // TEST 1: Get ingredient by ID using getForObject()
    @GetMapping("/ingredient/get/{id}")
    public Ingredient testGetIngredient(@PathVariable String id) {
        log.info("TEST: Calling getIngredientById({})", id);
        Ingredient ingredient = ingredientServiceClient.getIngredientById(id);
        log.info("TEST: Result = {}", ingredient);
        return ingredient;
    }

    // TEST 2: Get ingredient with headers using getForEntity()
    @GetMapping("/ingredient/get-headers/{id}")
    public Ingredient testGetIngredientWithHeaders(@PathVariable String id) {
        log.info("TEST: Calling getIngredientWithHeaders({})", id);
        Ingredient ingredient = ingredientServiceClient.getIngredientWithHeaders(id);
        log.info("TEST: Result = {}", ingredient);
        return ingredient;
    }

    // TEST 3: Create ingredient using postForObject()
    @PostMapping("/ingredient/create-post")
    public Ingredient testCreateIngredient() {
        log.info("TEST: Creating ingredient via postForObject()");
        Ingredient newIng = new Ingredient("TEST1", "Test Ingredient 1", Type.WRAP);
        Ingredient result = ingredientServiceClient.createIngredient(newIng);
        log.info("TEST: Created ingredient = {}", result);
        return result;
    }

    // TEST 4: Create ingredient and get location using postForLocation()
    @PostMapping("/ingredient/create-location")
    public String testCreateIngredientLocation() {
        log.info("TEST: Creating ingredient via postForLocation()");
        Ingredient newIng = new Ingredient("TEST2", "Test Ingredient 2", Type.SAUCE);
        String location = ingredientServiceClient.createIngredientGetLocation(newIng);
        log.info("TEST: Location = {}", location);
        return location;
    }

    // TEST 5: Create ingredient with status using postForEntity()
    @PostMapping("/ingredient/create-entity")
    public Ingredient testCreateIngredientEntity() {
        log.info("TEST: Creating ingredient via postForEntity()");
        Ingredient newIng = new Ingredient("TEST3", "Test Ingredient 3", Type.CHEESE);
        Ingredient result = ingredientServiceClient.createIngredientWithStatus(newIng);
        log.info("TEST: Created = {}", result);
        return result;
    }

    // TEST 6: Update ingredient using put()
//    @PutMapping("/ingredient/update/{id}")
//    public String testUpdateIngredient(@PathVariable String id) {
//        log.info("TEST: Updating ingredient {}", id);
//        Ingredient ing = ingredientServiceClient.getIngredientById(id);
//        if (ing != null) {
//            ing.setName(ing.getName() + " (UPDATED)");
//            ingredientServiceClient.updateIngredient(ing);
//            log.info("TEST: Updated successfully");
//            return "Updated: " + ing.getName();
//        }
//        return "Ingredient not found";
//    }

    // TEST 7: Delete ingredient using delete()
    @DeleteMapping("/ingredient/delete/{id}")
    public String testDeleteIngredient(@PathVariable String id) {
        log.info("TEST: Deleting ingredient {}", id);
        ingredientServiceClient.deleteIngredient(id);
        log.info("TEST: Deleted successfully");
        return "Deleted ingredient: " + id;
    }
}