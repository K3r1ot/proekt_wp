package com.example.proekt;

import com.example.proekt.model.Food;
import com.example.proekt.repository.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class DataInitializer {

    @Autowired
    private FoodRepository foodRepository;

    @PostConstruct
    public void initFoodData() {
        if (foodRepository.count() == 0) {
            foodRepository.save(new Food("Пилешки гради", 165, 31));
            foodRepository.save(new Food("Јајце", 78, 6));
            foodRepository.save(new Food("Јаболко", 95, 0.3));
            foodRepository.save(new Food("Сом", 206, 22));
            foodRepository.save(new Food("Брокула", 55, 3.7));
            foodRepository.save(new Food("Овес", 389, 17));
            foodRepository.save(new Food("Бадеми", 576, 21.2));
            foodRepository.save(new Food("Млеко", 42, 3.4));
            foodRepository.save(new Food("Ориз", 130, 2.7));
            foodRepository.save(new Food("Јогурт", 59, 10));
            foodRepository.save(new Food("Кравјо месо", 250, 26));
            foodRepository.save(new Food("Сирење", 402, 25));
            foodRepository.save(new Food("Леќа", 116, 9));
            foodRepository.save(new Food("Кикиритки", 567, 26));
            foodRepository.save(new Food("Домати", 18, 0.9));
            foodRepository.save(new Food("Краставица", 16, 0.7));
            foodRepository.save(new Food("Компир", 77, 2));
            foodRepository.save(new Food("Грав", 347, 21));
            foodRepository.save(new Food("Лук", 149, 6.4));
            foodRepository.save(new Food("Спанаќ", 23, 2.9));
            foodRepository.save(new Food("Моркови", 41, 0.9));
            foodRepository.save(new Food("Цвекло", 43, 1.6));
            foodRepository.save(new Food("Лосос", 206, 22));
            foodRepository.save(new Food("Сушено овошје", 282, 5.2));
            foodRepository.save(new Food("Туршија", 16, 0.6));
            foodRepository.save(new Food("Пченица", 339, 13.7));
            foodRepository.save(new Food("Тесто", 260, 8.7));
            foodRepository.save(new Food("Мед", 304, 0.3));
            foodRepository.save(new Food("Козјо млеко", 69, 3.6));
            foodRepository.save(new Food("Грозје", 69, 0.7));
        }
    }
}
