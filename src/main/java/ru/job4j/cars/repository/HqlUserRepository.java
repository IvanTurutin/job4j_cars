package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import ru.job4j.cars.model.User;

import java.util.*;

/**
 * Репозиторий пользователей 
 * @see ru.job4j.cars.model.User
 */
@ThreadSafe
@AllArgsConstructor
public class HqlUserRepository implements UserRepository {

    private final CrudRepository crudRepository;

    public static final String USER_MODEL = "User";
    public static final String LOGIN = "fLogin";
    public static final String ID = "fId";
    public static final String TRUNCATE_TABLE = String.format("DELETE FROM %s", USER_MODEL);
    public static final String DELETE_STATEMENT = String.format(
            "DELETE %s WHERE id = :%s",
            USER_MODEL, ID
    );
    public static final String FIND_ALL_STATEMENT = String.format("from %s", USER_MODEL);
    public static final String FIND_ALL_ORDER_BY_ID_STATEMENT = FIND_ALL_STATEMENT + " order by id";
    public static final String FIND_BY_ID_STATEMENT = FIND_ALL_STATEMENT + String.format(" where id = :%s", ID);
    public static final String KEY = "fKey";
    public static final String FIND_BY_LOGIN_LIKE_STATEMENT = FIND_ALL_STATEMENT
            + String.format(" where login like :%s", KEY);
    public static final String FIND_BY_LOGIN_STATEMENT = FIND_ALL_STATEMENT
            + String.format(" where login = :%s", LOGIN);

    /**
     * Сохраняет пользователя в базе.
     * @param user пользователь.
     * @return пользователь с id обернутый в Optional если пользователь успешно добавлен,
     * Optional.empty() если пользователь не добавлен.
     */
    @Override
    public User create(User user) {
        crudRepository.run(session -> session.persist(user));
        return user;
    }

    /**
     * Обновить в базе пользователя.
     * @param user пользователь.
     */
    @Override
    public void update(User user) {
        crudRepository.run(session -> session.merge(user));
    }

    /**
     * Удалить пользователя по id.
     * @param userId ID
     */
    @Override
    public void delete(int userId) {
        crudRepository.run(
                DELETE_STATEMENT,
                Map.of(ID, userId)
        );
    }

    /**
     * Список пользователей отсортированных по id.
     * @return список пользователей.
     */
    @Override
    public List<User> findAllOrderById() {
        return crudRepository.query(FIND_ALL_ORDER_BY_ID_STATEMENT, User.class);
    }

    /**
     * Найти пользователя по ID
     * @return пользователь.
     */
    @Override
    public Optional<User> findById(int id) {
        return crudRepository.optional(
                FIND_BY_ID_STATEMENT, User.class,
                Map.of(ID, id)
        );
    }

    /**
     * Список пользователей по login LIKE %key%
     * @param key key
     * @return список пользователей.
     */
    @Override
    public List<User> findByLikeLogin(String key) {
        return crudRepository.query(
                FIND_BY_LOGIN_LIKE_STATEMENT, User.class,
                Map.of(KEY, "%" + key + "%")
        );
    }

    /**
     * Найти пользователя по login.
     * @param login login.
     * @return Optional or user.
     */
    @Override
    public Optional<User> findByLogin(String login) {
        return crudRepository.optional(
                FIND_BY_LOGIN_STATEMENT, User.class,
                Map.of(LOGIN, login)
        );
    }

    public void truncate() {
        crudRepository.run(
                TRUNCATE_TABLE,
                new HashMap<>());
    }
}
