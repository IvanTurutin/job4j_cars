package ru.job4j.cars.repository;

import ru.job4j.cars.model.User;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий пользователей
 * @see ru.job4j.cars.model.User
 */
public interface UserRepository {

    /**
     * Сохраняет пользователя в базе.
     * @param user пользователь.
     * @return пользователь с id обернутый в Optional если пользователь успешно добавлен,
     * Optional.empty() если пользователь не добавлен.
     */
    Optional<User> create(User user);

    /**
     * Обновить в базе пользователя.
     * @param user пользователь.
     */
    boolean update(User user);

    /**
     * Удалить пользователя по id.
     * @param userId ID
     */
    boolean delete(int userId);

    /**
     * Список пользователь отсортированных по id.
     * @return список пользователей.
     */
    List<User> findAllOrderById();

    /**
     * Найти пользователя по ID
     * @return пользователь.
     */
    Optional<User> findById(int id);

    /**
     * Список пользователей по login LIKE %key%
     * @param key key
     * @return список пользователей.
     */
    List<User> findByLikeLogin(String key);

    /**
     * Найти пользователя по login.
     *
     * @param login    login.
     * @param password пароль
     * @return Optional or user.
     */
    Optional<User> findByLoginAndPassword(String login, String password);
}
