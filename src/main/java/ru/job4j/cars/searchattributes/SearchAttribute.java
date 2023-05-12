package ru.job4j.cars.searchattributes;

import org.springframework.stereotype.Component;

/**
 * Интерфейс для определения типа атрибутов поиска
 */
@Component
public interface SearchAttribute {

    /**
     * Возвращает значение характеристики
     * @return id объекта
     */
    Object getCharactValue();

    /**
     * Возвращает строковое представление характеристики
     * @return строковое представление характеристики
     */
    String getCharacteristic();

    /**
     * Возвращает часть запроса с условием поиска
     * @return строковое представление типа аттрибута
     */
    String getSearchAttribute();
}
