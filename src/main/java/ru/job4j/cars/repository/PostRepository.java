package ru.job4j.cars.repository;

import ru.job4j.cars.model.Post;
import ru.job4j.cars.model.User;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий объявлений
 * @see ru.job4j.cars.model.Post
 */
public interface PostRepository {
    /**
     * Сохраняет объявление в базе.
     * @param post объявление.
     * @return добавленное объявление.
     */
    Post add(Post post);

    /**
     * Обновляет в базе объявление.
     * @param post объявление.
     */
    void update(Post post);

    /**
     * Удаляет объявление по id.
     * @param postId ID
     */
    void delete(int postId);

    /**
     * Список объявлений отсортированных по id.
     * @return список объявлений.
     */
    List<Post> findAllOrderById();

    /**
     * Найти объявление по ID
     * @return объявление обернутое в Optional если найдено, и Optional.empty() если не найдено.
     */
    Optional<Post> findById(int id);

    /**
     * Список объявлений, принадлежащих пользователю
     * @param user пользователь
     * @return список объявлений.
     */
    List<Post> findByUser(User user);

    /**
     * Осуществляет поиск объявлений за последние дни
     * @param days количество дней, за которые требуется найти объявления
     * @return список объявлений удовлетворяющих требованию
     */
    List<Post> findLastDays(int days);

    /**
     * Осуществляет поиск всех объявлений с фото
     * @return список объявлений
     */
    List<Post> findWithPhoto();

    /**
     * Осуществляет поиск объявлений по марке автомобиля
     * @param carName марка автомобиля
     * @return список объявлений
     */
    List<Post> findCarName(String carName);

}
