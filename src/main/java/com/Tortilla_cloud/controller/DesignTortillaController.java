package com.Tortilla_cloud.controller;

import com.Tortilla_cloud.model.Ingredient;
import com.Tortilla_cloud.model.Tortilla;
import com.Tortilla_cloud.model.Type;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/design")
public class DesignTortillaController {
    private static final List<Ingredient> INGREDIENTS = Arrays.asList(
            new Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
            new Ingredient("COTO", "Corn Tortilla", Type.WRAP),
            new Ingredient("WHTO", "Whole Wheat Tortilla", Type.WRAP),

            new Ingredient("SMCH", "Smoked Chicken", Type.PROTEIN),
            new Ingredient("CARN", "Carnitas", Type.PROTEIN),
            new Ingredient("SLST", "Sliced Steak", Type.PROTEIN),

            new Ingredient("TMTO", "Diced Tomato", Type.VEGGIES),
            new Ingredient("LETT", "Lettuce", Type.VEGGIES),
            new Ingredient("SWPO", "Sweet Potato", Type.VEGGIES),
            new Ingredient("HUMM", "Hummus", Type.VEGGIES),

            new Ingredient("CHED", "Cheddar", Type.CHEESE),
            new Ingredient("JACK", "Monterrey Jack", Type.CHEESE),

            new Ingredient("SLSA", "Salsa", Type.SAUCE),
            new Ingredient("MAYO", "Mayonnaise", Type.SAUCE)
    );


    @ModelAttribute("design")
    public Tortilla design() {
        return new Tortilla();
    }


    @ModelAttribute
    public void addIngredientsToModel(Model model) {
        for (Type type : Type.values()) {
            model.addAttribute(type.name().toLowerCase(), filterByType(INGREDIENTS, type));
        }
    }

    @GetMapping
    public String showDesignForm() {
        return "design";
    }

    @PostMapping
    public String processDesign(@Valid @ModelAttribute("design") Tortilla design, Errors errors) {
        if (errors.hasErrors()) {
            return "design";
        }
        log.info("Processing design: {}", design);
        return "redirect:/orders/current";
    }

    private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
        return ingredients.stream()
                .filter(ingredient -> ingredient.getType() == type)
                .toList();
    }
}
