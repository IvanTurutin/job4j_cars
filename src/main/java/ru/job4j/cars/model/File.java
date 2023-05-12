package ru.job4j.cars.model;

import lombok.*;
import ru.job4j.cars.repository.HqlPostRepository;
import ru.job4j.cars.searchattributes.SearchAttribute;

import javax.persistence.*;

/**
 * Модель данных файла
 */
@Entity
@Table(name = "files")
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class File implements SearchAttribute {

    public File(String name, String path) {
        this.name = name;
        this.path = path;
    }

    /**
     * Идентификатор файла
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    /**
     * Имя файла
     */
    private String name;

    /**
     * Путь к файлу
     */
    private String path;

    private final  static String FIELD_NAME = "files";
    private final static String CHARACTERISTIC = "fQuantity";
    private final static int QUANTITY = 0;

    @Override
    public String getSearchAttribute() {
        return String.format(" %s.%s.size > :%s", HqlPostRepository.TABLE_ALIAS, FIELD_NAME, CHARACTERISTIC);
    }

    @Override
    public Object getCharactValue() {
        return QUANTITY;
    }

    @Override
    public String getCharacteristic() {
        return CHARACTERISTIC;
    }
}
