package com.example.proekt.controller;

import com.example.proekt.model.Food;
import com.example.proekt.model.FoodEntry;
import com.example.proekt.model.Goal;
import com.example.proekt.model.User;
import com.example.proekt.repository.GoalRepository;
import com.example.proekt.service.FoodEntryService;
import com.example.proekt.service.FoodService;
import com.example.proekt.service.GoalService;
import com.example.proekt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
public class GoalController {

    @Autowired
    private GoalService goalService;

    @Autowired
    private UserService userService;

    private GoalRepository goalRepository;

    @Autowired
    private FoodEntryService foodEntryService;

    @Autowired
    private FoodService foodService;

    public GoalController(GoalRepository goalRepository, FoodEntryService foodEntryService) {
        this.goalRepository = goalRepository;
        this.foodEntryService = foodEntryService;
    }

    @GetMapping({"/", "/profile"})
    public String showProfilePage(Model model) {
        User user = getCurrentUser();
        List<Goal> goals = goalService.getGoalsForUser(user);
        model.addAttribute("user", user);
        model.addAttribute("goals", goals);
        return "profile";
    }

    @GetMapping("/set-goal")
    public String showSetGoalPage() {
        return "set-goal";
    }
    @PostMapping("/set-goal")
    public String setGoal(@RequestParam double currentWeight,
                          @RequestParam double targetWeight,
                          @RequestParam double height, Model model) {
        User user = getCurrentUser();
        Goal goal = goalService.setGoal(user, currentWeight, targetWeight, height);
        model.addAttribute("goal", goal);
        return "goal-details";
    }

    @PostMapping("/delete-goal")
    public String deleteGoal(@RequestParam Long goalId) {
        goalService.deleteGoal(goalId);
        return "redirect:/profile";
    }



    @GetMapping("/goal-details")
    public String showGoalDetailsPage(Model model) {
        User user = getCurrentUser();
        List<Goal> goals = goalService.getGoalsForUser(user);
        Goal latestGoal = goals.isEmpty() ? null : goals.get(goals.size() - 1);

        if (latestGoal == null) {

            model.addAttribute("message", "Немате поставено цел. Ве молиме поставете цел.");
            return "redirect:/set-goal";

        }


        List<FoodEntry> todayFoodEntries = foodEntryService.getFoodEntriesForUserOnDate(user, LocalDate.now());


        double totalCalories = todayFoodEntries.stream()
                .mapToDouble(FoodEntry::getTotalCalories)
                .sum();

        double totalProtein = todayFoodEntries.stream()
                .mapToDouble(FoodEntry::getTotalProtein)
                .sum();

        model.addAttribute("goal", latestGoal);
        model.addAttribute("todayFoodEntries", todayFoodEntries);
        model.addAttribute("totalCalories", totalCalories);
        model.addAttribute("totalProtein", totalProtein);

        return "goal-details";
    }


    @GetMapping("/log-food")
    public String showLogFoodPage(Model model) {
        List<Food> allFoods = foodService.getAllFoods();
        model.addAttribute("foodEntry", new FoodEntry());
        model.addAttribute("allFoods", allFoods);
        return "log-food";
    }

    @PostMapping("/log-food")
    public String logFood(@RequestParam Long foodId, @RequestParam double quantityInGrams, Model model) {
        User user = getCurrentUser();
        Optional<Food> optionalFood = foodService.getFoodById(foodId);

        if (optionalFood.isEmpty()) {
            throw new IllegalArgumentException("Food not found with ID: " + foodId);
        }

        Food food = optionalFood.get();
        foodEntryService.logFood(user, food.getName(), quantityInGrams);

        List<Goal> goals = goalService.getGoalsForUser(user);

        double totalCalories = foodEntryService.getFoodEntriesForUserOnDate(user, LocalDate.now())
                .stream()
                .mapToDouble(FoodEntry::getTotalCalories)
                .sum();

        double totalProtein = foodEntryService.getFoodEntriesForUserOnDate(user, LocalDate.now())
                .stream()
                .mapToDouble(FoodEntry::getTotalProtein)
                .sum();

        String feedback = generateFeedback(goals, totalCalories, totalProtein);


        List<Food> allFoods = foodService.getAllFoods();
        model.addAttribute("foodEntry", new FoodEntry());
        model.addAttribute("allFoods", allFoods);
        model.addAttribute("feedback", feedback);

        return "log-food";
    }


    private String generateFeedback(List<Goal> goals, double totalCalories, double totalProtein) {
        if (goals.isEmpty()) {
            return "Немате поставено цели.";
        }

        Goal currentGoal = goals.get(0);

        String feedback = "";
        if (totalCalories < currentGoal.getDailyCalories()) {
            feedback += "Треба да изедете уште " + (currentGoal.getDailyCalories() - totalCalories) + " калории денес.\n";
        } else {
            feedback += "Го надминавте дневниот лимит за калории.\n";
        }

        if (totalProtein < currentGoal.getDailyProtein()) {
            feedback += "Треба да внесете уште " + (currentGoal.getDailyProtein() - totalProtein) + " грама протеини денес.";
        } else {
            feedback += "Го надминавте дневниот лимит за протеини.";
        }

        return feedback;
    }


    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            String username = ((UserDetails) authentication.getPrincipal()).getUsername();

            User user = userService.findByUsername(username);
            if (user == null) {
                throw new UsernameNotFoundException("User not found");
            }
            return user;
        }
        throw new IllegalStateException("User not authenticated");
    }
}
