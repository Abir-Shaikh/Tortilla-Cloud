package com.Tortilla_cloud.controller;

import com.Tortilla_cloud.model.Ingredient;
import com.Tortilla_cloud.model.Tortilla;
import com.Tortilla_cloud.model.Type;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/design")
public class DesignTortillaController {
    @GetMapping
    public String showDesignForm(Model model) {
        List<Ingredient> ingredients = Arrays.asList(
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
        Type[] types = Type.values();

        for (Type type : types) {
            model.addAttribute(type.name().toLowerCase(),
                    filterByType(ingredients, type));
        }
        model.addAttribute("design", new Tortilla());
        return "design";
    }

    @PostMapping
    public String processDesign(Tortilla design) {
        log.info("Processing design: {}", design);
        return "redirect:/orders/current";
    }

    private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
        return ingredients.stream()
                .filter(ingredient -> ingredient.getType() == type)
                .toList();
    }
}
