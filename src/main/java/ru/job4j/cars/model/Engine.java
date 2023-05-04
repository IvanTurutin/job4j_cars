package ru.job4j.cars.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

/**
 * Модель данных Двигатель
 */
@Entity
@Table(name = "engines")
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Engine implements SearchAttribute {
    /**
     * Идентификатор двигателя
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    /**
     * Название двигателя/модель/модификация
     */
    private String name;

    @Override
    public String getType() {
        return this.getClass().getSimpleName().toLowerCase();
    }
}
