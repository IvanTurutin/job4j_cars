package ru.job4j.cars.service;

import ru.job4j.cars.model.*;

import java.util.List;
import java.util.Optional;

/**
 * Сервис объявлений
 * @see Post
 */
public interface PostService {
    /**
     * Обрабатывает запрос на сохранение объявления.
     * @param post объявление.
     * @return true если успех, и false если неудача
     */
    boolean add(Post post);

    /**
     * Обрабатывает обновление объявления.
     * @param post объявление.
     * @return true если успех, и false если неудача
     */
    boolean update(Post post);

    /**
     * Обрабатывает запрос на удаление объявления.
     * @param postId ID
     * @return true если успех, и false если неудача
     */
    boolean delete(int postId);

    /**
     * Обрабатывает запрос на поиск всех объявлений.
     * @return список объявлений.
     */
    List<Post> findAll();


    List<Post> findAll(User user);

    /**
     * Обрабатывает запрос на поиск объявления по ID
     * @return объявление обернутое в Optional если найдено, и Optional.empty() если не найдено.
     */
    Optional<Post> findById(int id);

    /**
     * Обрабатывает запрос на поиск списка объявлений, принадлежащих пользователю
     * @param user пользователь
     * @return список объявлений.
     */
    List<Post> findByUser(User user);

    /**
     * Обрабатывает запрос на поиск объявлений за последние дни
     * @param days количество дней, за которые требуется найти объявления
     * @return список объявлений удовлетворяющих требованию
     */
    List<Post> findLastDays(int days);

    /**
     * Обрабатывает запрос на поиск всех объявлений на которые подписан пользователь
     * @param user пользователь для которого осуществляется поиск
     * @return список объявлений
     */
    List<Post> findBySubscribedUser(User user);

    /**
     * Обрабатывает запрос на поиск объявлений удовлетворяющих атрибутам поиска
     * @param characts список атрибутов поиска
     * @return список объявлений
     */
    List<Post> findBySearchAttributes(List<SearchAttribute> characts);



}
