package ru.ginatulin.group;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "ru.ginatulin")
public class GroupServiceApp {
    public static void main(String[] args) {
        SpringApplication.run(GroupServiceApp.class, args);
    }
}
