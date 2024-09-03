package com.example.proekt.service.impl;

import com.example.proekt.model.Goal;
import com.example.proekt.model.User;
import com.example.proekt.repository.GoalRepository;
import com.example.proekt.service.GoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GoalServiceImpl implements GoalService {

    @Autowired
    private GoalRepository goalRepository;

    @Override
    public Goal setGoal(User user, double currentWeight, double targetWeight, double currentHeight) {

        if (user.getId() == null) {
            throw new IllegalStateException("User must be saved before setting a goal.");
        }

        double dailyCalories = calculateCalories(currentWeight, targetWeight, currentHeight);
        double dailyProtein = calculateProtein(currentWeight, targetWeight);

        Goal goal = new Goal();
        goal.setUser(user);
        goal.setCurrentWeight(currentWeight);
        goal.setTargetWeight(targetWeight);
        goal.setDailyCalories(dailyCalories);
        goal.setDailyProtein(dailyProtein);

        return goalRepository.save(goal);
    }


    @Override
    public List<Goal> getGoalsForUser(User user) {
        return goalRepository.findByUser(user);
    }

    @Override
    public void updateGoal(Goal goal) {

        goalRepository.save(goal);
    }

    @Override
    public void deleteGoal(Long goalId) {
        goalRepository.deleteById(goalId);
    }

//    @Override
//    public Goal getGoalById(Long goalId) {
//        return goalRepository.findById(goalId)
//                .orElseThrow(() -> new IllegalArgumentException("Не постој цел со ид: " + goalId));
//    }

    private double calculateCalories(double currentWeight, double targetWeight, double height) {

        double BMR = 10 * currentWeight + 6.25 * height - 5 * 30 + 5;


        double calorieAdjustment = currentWeight > targetWeight ? -500 : 500;


        double dailyCalories = BMR + calorieAdjustment;


        return Math.max(dailyCalories, 1200);
    }

    private double calculateProtein(double currentWeight, double targetWeight) {

        double proteinPerKg = 1.8;


        return currentWeight * proteinPerKg;
    }
}
