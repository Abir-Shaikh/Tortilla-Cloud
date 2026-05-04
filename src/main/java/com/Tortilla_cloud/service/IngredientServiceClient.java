package com.Tortilla_cloud.service;

import com.Tortilla_cloud.model.Ingredient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class IngredientServiceClient {


    private final RestTemplate restTemplate;
    private static final String INGREDIENT_API_URL = "http://localhost:8080/api/ingredients";

    public IngredientServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    //getForObject we get only the objects not the headers
    public Ingredient getIngredientById(String ingredientId){
        log.info("Fetched ingredient with ID: {}" , ingredientId);
        return restTemplate.getForObject(
                INGREDIENT_API_URL + "/{id}" ,
                Ingredient.class ,
                ingredientId
        );
    }

    //getForEntity we get headers and statusCode
    public Ingredient getIngredientWithHeaders(String ingredientId){
        log.info("Fetching Ingredient with headers");
        var forEntity = restTemplate.
                getForEntity(
                        INGREDIENT_API_URL + "/{id}",
                        Ingredient.class,
                        ingredientId
                );

        log.info("Response Date: {}" , forEntity.getHeaders().getDate());
        log.info("Response Status: {}" , forEntity.getStatusCode());
        return forEntity.getBody();
    }

    //postForObject we can POSTs data and return the object created by me
    public Ingredient createIngredient(Ingredient ingredient){
        log.info("Creating new Ingredient: {}" , ingredient.getName());
        return restTemplate.postForObject(
                INGREDIENT_API_URL ,
                ingredient ,
                Ingredient.class
        );
    }

    //postForLocation we can POSTs the data and returns the URL of resource created
    public String createIngredientGetLocation(Ingredient ingredient){
        log.info("Creating Ingredient and getting location URL");
        var location = restTemplate.postForLocation(
                INGREDIENT_API_URL,
                ingredient
        );
        log.info("New Ingredient Created at: {}" , location);
        return location.toString();
    }

    //postForEntity we can POSTs data and return ResponseEntity with status + body + headers
    public Ingredient createIngredientWithStatus(Ingredient ingredient){
        log.info("Creating ingredient with full response");
        var responseEntity = restTemplate.postForEntity(
                INGREDIENT_API_URL,
                ingredient,
                Ingredient.class
        );
        log.info("Status: {}", responseEntity.getStatusCode());
        log.info("headers :{}", responseEntity.getHeaders().getLocation());
        return responseEntity.getBody();
    }

    //update an entire resource
    public void updateIngredient(Ingredient ingredient){
        log.info("Updating ingredient ID: {}", ingredient.getId());
        restTemplate.put(
                INGREDIENT_API_URL + "/{id}" ,
                ingredient ,
                ingredient.getId()
        );
        log.info("Ingredient updated successfully");
    }

    //delete an entire resource
    public void deleteIngredient(String ingredientId){
        log.info("Deleting ingredient ID: {}" , ingredientId);
        restTemplate.delete(
                INGREDIENT_API_URL + "/{id}" ,
                ingredientId
        );
        log.info("Ingredient deleted successfully");
    }
}
