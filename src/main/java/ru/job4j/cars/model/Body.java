package ru.job4j.cars.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.job4j.cars.repository.HqlPostRepository;
import ru.job4j.cars.searchattributes.SearchAttribute;

import javax.persistence.*;

/**
 * Модель кузова
 */
@Entity
@Table(name = "bodies")
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Body implements SearchAttribute {

    /**
     * Идентификатор типа кузова
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    /**
     * Название типа кузова
     */
    private String name;

    private final  static String FIELD_NAME = "body";
    private final static String CHARACTERISTIC = "fBodyId";

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
