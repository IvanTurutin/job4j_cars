package ru.job4j.cars.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Модель данных Автомобиль
 */

@Entity
@Table(name = "cars")
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Car {
    /**
     * Идентификатор автомобиля
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    /**
     * Тип кузова автомобиля
     */
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "body_id")
    private Body body;

    /**
     * Модель автомобиля
     */
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "car_model_id")
    private CarModel carModel;

    /**
     * Тип коробки передач автомобиля
     */
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "transmission_id")
    private Transmission transmission;

    /**
     * Двигатель автомобиля
      */
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "engine_id", foreignKey = @ForeignKey(name = "ENGINE_ID_FK"))
    private Engine engine;

    /**
     * Текущий владелец автомобиля
     */
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_id")
    private Owner owner;

    /**
     * История владельцев автомобиля
     */
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(name = "car_owners", joinColumns = {
            @JoinColumn(name = "car_id", nullable = false, updatable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "owner_id", nullable = false, updatable = false)})
    private List<Owner> owners = new ArrayList<>();

}

