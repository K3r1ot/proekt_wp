package com.example.proekt.service;

import com.example.proekt.model.Food;
import java.util.List;
import java.util.Optional;

public interface FoodService {
    List<Food> getAllFoods();
    Food getFoodByName(String name);

    Optional<Food> getFoodById(Long foodId);
}
