package ru.job4j.cars.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

/**
 * Модель данных Владелец автомобиля
 */
@Entity
@Table(name = "owners")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Owner {

    /**
     * Идентификатор автомобиля
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    /**
     * ФИО владельца
     */
    private String name;

    @Override
    public String toString() {

        return "Owner{"
                + "id=" + id
                + ", name='" + name + '\''
                + '}';
    }
}
