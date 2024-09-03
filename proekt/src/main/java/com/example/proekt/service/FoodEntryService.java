package com.example.proekt.service;

import com.example.proekt.model.FoodEntry;
import com.example.proekt.model.User;

import java.time.LocalDate;
import java.util.List;

public interface FoodEntryService {


    FoodEntry logFood(User user, String foodName, double quantityInGrams);


    List<FoodEntry> getAllFoodEntries();


    List<FoodEntry> getFoodEntriesForUserOnDate(User user, LocalDate date);


    FoodEntry getFoodEntryByFoodName(String foodName);
}
