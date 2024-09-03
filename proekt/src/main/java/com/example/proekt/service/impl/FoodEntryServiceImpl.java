package com.example.proekt.service.impl;

import com.example.proekt.model.Food;
import com.example.proekt.model.FoodEntry;
import com.example.proekt.model.User;
import com.example.proekt.repository.FoodEntryRepository;
import com.example.proekt.repository.FoodRepository;
import com.example.proekt.service.FoodEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class FoodEntryServiceImpl implements FoodEntryService {

    @Autowired
    private FoodEntryRepository foodEntryRepository;


    private FoodRepository foodRepository;

    public FoodEntryServiceImpl(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    @Override
    public FoodEntry logFood(User user, String foodName, double quantityInGrams) {

        Optional<Food> optionalFood = foodRepository.findByName(foodName);

        if (optionalFood.isEmpty()) {
            throw new IllegalArgumentException("Food not found: " + foodName);
        }

        Food food = optionalFood.get();


        FoodEntry foodEntry = new FoodEntry(food, user, quantityInGrams, LocalDate.now());
        return foodEntryRepository.save(foodEntry);
    }


    @Override
    public List<FoodEntry> getAllFoodEntries() {
        return foodEntryRepository.findAll();
    }

    @Override
    public List<FoodEntry> getFoodEntriesForUserOnDate(User user, LocalDate date) {
        return foodEntryRepository.findByUserAndDate(user, date);
    }

    @Override
    public FoodEntry getFoodEntryByFoodName(String foodName) {

        List<FoodEntry> entries = foodEntryRepository.findAll();
        return entries.stream()
                .filter(entry -> entry.getFood().getName().equals(foodName))
                .findFirst()
                .orElse(null);
    }
}
