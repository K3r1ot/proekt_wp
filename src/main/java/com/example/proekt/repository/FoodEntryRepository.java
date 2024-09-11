package com.example.proekt.repository;

import com.example.proekt.model.FoodEntry;
import com.example.proekt.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface FoodEntryRepository extends JpaRepository<FoodEntry, Long> {
    List<FoodEntry> findByUserAndDate(User user, LocalDate date);
}
