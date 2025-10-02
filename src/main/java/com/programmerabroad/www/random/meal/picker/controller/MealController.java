package com.programmerabroad.www.random.meal.picker.controller;

import com.programmerabroad.www.random.meal.picker.entity.Meals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/api")
public class MealController {

    private static final String RANDOM_MEAL_API_URL = "http://www.themealdb.com/api/json/v1/1/random.php";
    private static final String MEAL_BY_LETTER_API_URL = "http://www.themealdb.com/api/json/v1/1/search.php?f=a";
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/meal")
    @ResponseBody
    public Meals meal() {
        //ResponseEntity<Meals> mealsResponseEntity = restTemplate.getForEntity(RANDOM_MEAL_API_URL, Meals.class);
        ResponseEntity<Meals> mealsResponseEntity = restTemplate
                .getForEntity(MEAL_BY_LETTER_API_URL,
                        Meals.class);

        if(mealsResponseEntity.getStatusCode().is2xxSuccessful()) {
            return mealsResponseEntity.getBody();
        }else{
            return null;
        }
    }
}
