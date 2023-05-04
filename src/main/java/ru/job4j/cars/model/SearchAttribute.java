package ru.job4j.cars.model;

/**
 * Интерфейс для определения типа атрибутов поиска
 */
public interface SearchAttribute {

    /**
     * Возвращает Id объекта
     * @return id объекта
     */
    int getId();

    /**
     * Возвращает тип аттрибута поиска
     * @return строковое представление типа аттрибута
     */
    String getType();
}
