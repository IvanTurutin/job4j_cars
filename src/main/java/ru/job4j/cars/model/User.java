package ru.job4j.cars.model;

import lombok.Data;

import javax.persistence.*;

/**
 * Модель данных User
 */
@Entity
@Table(name = "auto_users")
@Data
public class User {
    /**
     * Идентификатор пользователя
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    /**
     * Логин пользователя
     */
    private String login;
    /**
     * Пароль пользователя
     */
    private String password;
}
