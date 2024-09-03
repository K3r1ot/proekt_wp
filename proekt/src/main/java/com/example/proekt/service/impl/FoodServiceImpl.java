package com.example.proekt.service.impl;

import com.example.proekt.model.Food;
import com.example.proekt.repository.FoodRepository;
import com.example.proekt.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FoodServiceImpl implements FoodService {

    @Autowired
    private FoodRepository foodRepository;

    @Override
    public List<Food> getAllFoods() {
        return foodRepository.findAll();
    }

    @Override
    public Food getFoodByName(String name) {
        return (Food) foodRepository.findByName(name).orElse(null);
    }

    @Override
    public Optional<Food> getFoodById(Long foodId) {
        return foodRepository.findById(foodId);
    }

}
