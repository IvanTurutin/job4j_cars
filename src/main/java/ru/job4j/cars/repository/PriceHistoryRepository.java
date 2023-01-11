package ru.job4j.cars.repository;

import ru.job4j.cars.model.Post;
import ru.job4j.cars.model.PriceHistory;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий изменения цены
 * @see ru.job4j.cars.model.PriceHistory
 */
public interface PriceHistoryRepository {
    /**
     * Сохраняет изменение цены в базе.
     * @param priceHistory изменение цены.
     * @return добавленное изменение цены.
     */
    PriceHistory create(PriceHistory priceHistory);

    /**
     * Удаляет изменение цены по id.
     * @param priceHistoryId ID
     */
    void delete(int priceHistoryId);

    /**
     * Ищет изменение цены по ID
     * @return изменение цены обернутое в Optional если найдено, и Optional.empty() если не найдено.
     */
    Optional<PriceHistory> findById(int id);

    /**
     * Список изменений цены, принадлежащих объявлению
     * @param post объявление
     * @return список объявлений.
     */
    List<PriceHistory> findByPost(Post post);
}
