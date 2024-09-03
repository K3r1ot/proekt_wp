package com.example.proekt.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import java.util.Optional;

@Entity
public class FoodEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Food food;

    @ManyToOne
    private User user;

    private double quantityInGrams;
    private LocalDate date;

    // Default constructor
    public FoodEntry() {}


    public FoodEntry(Food food, User user, double quantityInGrams, LocalDate date) {
        this.food = food;
        this.user = user;
        this.quantityInGrams = quantityInGrams;
        this.date = date;
    }





    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getQuantityInGrams() {
        return quantityInGrams;
    }

    public void setQuantityInGrams(double quantityInGrams) {
        this.quantityInGrams = quantityInGrams;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }


    public double getTotalCalories() {
        return (quantityInGrams / 100) * food.getCaloriesPer100g();
    }


    public double getTotalProtein() {
        return (quantityInGrams / 100) * food.getProteinPer100g();
    }
}
