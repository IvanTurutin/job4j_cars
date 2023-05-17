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
     * История владельцев автомобиля. Отношение ManyToMany разделил на OneToMany(Car)+OneToMany(Owner) через CarOwner
     * для того, чтобы была возможность использовать дополнительные колонки в CarOwner
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "car", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Fetch(FetchMode.SUBSELECT)
    private List<CarOwner> carOwners = new ArrayList<>();

    /**
     * Корректно добавляет собственника к автомобилю
     * @param owner собственник
     * @param startAt начало владения
     * @param endAt конец владения
     */
    public void addCarOwner(Owner owner, LocalDateTime startAt, LocalDateTime endAt) {
        CarOwner carOwner = new CarOwner(owner, this);
        carOwner.setStartAt(startAt);
        carOwner.setEndAt(endAt);
        carOwners.add(carOwner);
    }
}

