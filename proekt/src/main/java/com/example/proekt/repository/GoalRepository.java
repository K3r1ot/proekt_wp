package com.example.proekt.repository;

import com.example.proekt.model.Goal;
import com.example.proekt.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GoalRepository extends JpaRepository<Goal, Long> {
    List<Goal> findByUser(User user);
    Goal findById(long id);
}
