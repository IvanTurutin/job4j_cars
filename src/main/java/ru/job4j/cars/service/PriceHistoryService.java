package ru.job4j.cars.service;

import ru.job4j.cars.model.Post;
import ru.job4j.cars.model.PriceHistory;

import java.util.List;
import java.util.Optional;

/**
 * Сервис изменения цены
 * @see PriceHistory
 */
public interface PriceHistoryService {
    /**
     * Обрабатывает запрос на сохранение изменение цены.
     * @param priceHistory изменение цены.
     * @return true если добавлен, false если не добавлен
     */
    boolean create(PriceHistory priceHistory);

    /**
     * Обрабатывает запрос на удаление изменения цены.
     * @param priceHistoryId ID
     * @return true если удален, false если не удален
     */
    boolean delete(int priceHistoryId);

    /**
     * Обрабатывает запрос на поиск цены
     * @return изменение цены обернутое в Optional если найдено, и Optional.empty() если не найдено.
     */
    Optional<PriceHistory> findById(int id);

    /**
     * Обрабатывает запрос на поиск изменений цены принадлежащих объявлению
     * @param post объявление
     * @return список объявлений.
     */
    List<PriceHistory> findByPost(Post post);
}
