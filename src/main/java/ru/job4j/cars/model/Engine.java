package ru.job4j.cars.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.job4j.cars.repository.HqlPostRepository;
import ru.job4j.cars.search_attributes.SearchAttribute;

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

    private final  static String FIELD_NAME = "engine";
    private final static String CHARACTERISTIC = "fEngineId";

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
