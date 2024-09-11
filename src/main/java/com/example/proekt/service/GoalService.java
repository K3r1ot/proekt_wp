package com.example.proekt.service;

import com.example.proekt.model.Goal;
import com.example.proekt.model.User;

import java.util.List;
import java.util.Optional;

public interface GoalService {


    Goal setGoal(User user, double currentWeight, double targetWeight, double currentHeight);

    List<Goal> getGoalsForUser(User user);

    void updateGoal(Goal goal);


    void deleteGoal(Long goalId);
}
