package com.Tortilla_cloud.controller;

import com.Tortilla_cloud.model.Ingredient;
import com.Tortilla_cloud.model.Tortilla;
import com.Tortilla_cloud.model.Type;
import com.Tortilla_cloud.repository.IngredientRepository;
import com.Tortilla_cloud.repository.TortillaRepository;
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

    private final IngredientRepository ingredientRepository;
    private final TortillaRepository tortillaRepository;

    public DesignTortillaController(IngredientRepository ingredientRepository, TortillaRepository tortillaRepository) {
        this.ingredientRepository = ingredientRepository;
        this.tortillaRepository = tortillaRepository;
    }

    @ModelAttribute("design")
    public Tortilla design() {
        return new Tortilla();
    }


    @ModelAttribute
    public void addIngredientsToModel(Model model) {
        for (Type type : Type.values()) {
            model.addAttribute
                    (type.name().toLowerCase(),
                    ingredientRepository.findByType(type));
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
        tortillaRepository.save(design);
        log.info("Saved tortilla: {}", design);
        return "redirect:/orders/current";
    }

}
