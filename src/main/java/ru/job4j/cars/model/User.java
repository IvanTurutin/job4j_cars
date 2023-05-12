package ru.job4j.cars.model;

import lombok.*;

import javax.persistence.*;

/**
 * Модель данных User
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "auto_users")
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {
    /**
     * Идентификатор пользователя
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;
    /**
     * Логин пользователя
     */
    private String login;
    /**
     * Пароль пользователя
     */
    private String password;

    /**
     * Имя пользователя
     */
    private String name;

    /**
     * Телефон пользователя
     */
    private String phone;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "timezone_id")
    private TimeZone timeZone;

/*
    двусторонняя связь не работает

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts;
*/

    public User(String login, String password, String name, TimeZone timeZone) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.timeZone = timeZone;
    }
}
