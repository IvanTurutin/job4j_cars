package ru.job4j.cars.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.job4j.cars.repository.HqlPostRepository;
import ru.job4j.cars.searchattributes.SearchAttribute;

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

    private final  static String FIELD_NAME = "carModel";
    private final static String CHARACTERISTIC = "fCarModelId";

    @Override
    public String getSearchAttribute() {
        return String.format(" %s.car.%s.id = :%s", HqlPostRepository.TABLE_ALIAS, FIELD_NAME, CHARACTERISTIC);
    }

    @Override
    public Object getCharactValue() {
        return id;
    }

    @Override
    public String getCharacteristic() {
        return CHARACTERISTIC;
    }
}
