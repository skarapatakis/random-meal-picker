package com.programmerabroad.www.random.meal.picker.controller;

import com.programmerabroad.www.random.meal.picker.entity.Meal;
import com.programmerabroad.www.random.meal.picker.entity.Meals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/api")
public class MealController {

    private static final String RANDOM_MEAL_API_URL = "http://www.themealdb.com/api/json/v1/1/random.php";

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/meal")
    public String meal(Model model) {

        ResponseEntity<Meals> mealsResponseEntity = restTemplate.getForEntity(RANDOM_MEAL_API_URL, Meals.class);
        if(mealsResponseEntity.getStatusCode().is2xxSuccessful()) {
            assert mealsResponseEntity.getBody() != null;
            Meal meal = mealsResponseEntity.getBody().meals.get(0);
            String[] instructions = meal.getStrInstructions().split("\n");
            meal.setInstructions(instructions);
            model.addAttribute("meal", meal);
            return "meals";
        }else{
            return "redirect:/";
        }
    }
}
