package ru.job4j.cars.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
     * PERSIST,
     *     MERGE,
     *     REMOVE,
     *     REFRESH,
     */
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "auto_user_id")
    private User user;

    /**
     * Автомобиль, который продается в этом объявлении
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "car_id")
    private Car car;

    /**
     * Список изменений цены
     */
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Fetch(FetchMode.SUBSELECT)
    @JoinColumn(name = "post_id")
    private List<PriceHistory> priceHistory = new ArrayList<>();

    /**
     * Список пользователей, подписанных на объявление
     */
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(
            name = "posts_users",
            joinColumns = { @JoinColumn(name = "post_id") },
            inverseJoinColumns = { @JoinColumn(name = "user_id") }
    )
    private List<User> users = new ArrayList<>();

    /**
     * Фотографии автомобиля
     */
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Fetch(FetchMode.SUBSELECT)
    @JoinColumn(name = "post_id")
    private List<File> files = new ArrayList<>();

}
