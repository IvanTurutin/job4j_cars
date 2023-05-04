package ru.job4j.cars.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

/**
 * Модель марка автомобиля
 */
@Entity
@Table(name = "car_models")
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CarModel implements SearchAttribute {

    /**
     * Название типа аттрибута поиска для клаcса CarModel
     */
    public static final String CAR_MODEL = "carModel";


    /**
     * Идентификатор марки автомобиля
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    /**
     * Название марки
     */
    private String name;

    @Override
    public String getType() {
        return CAR_MODEL;
    }

}
