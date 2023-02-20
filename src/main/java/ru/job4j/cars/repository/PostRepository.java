package ru.job4j.cars.repository;

import ru.job4j.cars.model.*;

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
     * @return список объявлений, удовлетворяющих запросу
     */
    List<Post> findWithPhoto();

    /**
     * Осуществляет поиск объявлений по марке автомобиля
     * @param carModel марка автомобиля
     * @return список объявлений, удовлетворяющих запросу
     */
    PostRepository findByCarModel(CarModel carModel);

    /**
     * Осуществляет поиск объявлений по типу кузова
     * @param body тип кузова
     * @return список объявлений, удовлетворяющих запросу
     */
    PostRepository findByBody(Body body);

    /**
     * Осуществляет поиск объявлений по типу коробки передач
     * @param transmission тип коробки передач
     * @return список объявлений, удовлетворяющих запросу
     */
    PostRepository findByTransmission(Transmission transmission);

    /**
     * Запускает поиск объявлений по набору характеристик автомобиля
     * @return список объявлений, удовлетворяющих запросу
     */
    List<Post> execute();

}
