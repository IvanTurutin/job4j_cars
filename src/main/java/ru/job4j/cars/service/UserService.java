package ru.job4j.cars.service;

import ru.job4j.cars.model.User;

import java.util.List;
import java.util.Optional;

/**
 * Сервис пользователей
 * @see ru.job4j.cars.model.User
 */
public interface UserService {

    /**
     * Обрабатывает запрос на сохранение пользователя.
     * @param user пользователь.
     * @return true если добавлен, false если не добавлен
     */
    boolean create(User user);

    /**
     * Обрабатывает запрос на обновление пользователя.
     *
     * @param user пользователь.
     */
    Optional<User> update(User user);

    /**
     * Обрабатывает запрос на удаление пользователя.
     * @param userId ID
     */
    boolean delete(int userId);

    /**
     * Обрабатывает запрос на поиск всех пользователей.
     * @return список пользователей.
     */
    List<User> findAllOrderById();

    /**
     * Обрабатывает запрос на поиск пользователя
     * @return пользователь.
     */
    Optional<User> findById(int id);

    /**
     * Обрабатывает запрос на поиск пользователя по логину LIKE %key%
     * @param key key
     * @return список пользователей.
     */
    List<User> findByLikeLogin(String key);

    /**
     * Обрабатывает запрос на поиск пользователя по логину.
     *
     * @param login    login.
     * @param password пароль
     * @return Optional or user.
     */
    Optional<User> findByLoginAndPassword(String login, String password);
}
