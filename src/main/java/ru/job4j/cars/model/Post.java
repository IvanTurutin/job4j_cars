package ru.job4j.cars.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Модель данных для объявлений
 */
@Entity
@Table(name = "auto_posts")
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Post {
    /**
     * Идентификатор объявления
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;
    /**
     * Текст объявления
     */
    private String text;
    /**
     * Время создания объявления
     */
    private LocalDateTime created = LocalDateTime.now();
    /**
     * Пользователь, создавший объявление
     */
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "auto_user_id")
    private User user;

    /**
     * Автомобиль, который продается в этом объявлении
     */
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "car_id")
    private Car car;

    /**
     * Список изменений цены
     */
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id")
    private List<PriceHistory> priceHistory = new ArrayList<>();

    /**
     * Список пользователей, подписанных на объявление
     */
    @ManyToMany
    @JoinTable(
            name = "participates",
            joinColumns = { @JoinColumn(name = "post_id") },
            inverseJoinColumns = { @JoinColumn(name = "user_id") }
    )
    private List<User> participates = new ArrayList<>();
}
