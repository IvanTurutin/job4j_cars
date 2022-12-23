package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.cars.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Репозиторий пользователей 
 * @see ru.job4j.cars.model.User
 */
@ThreadSafe
@AllArgsConstructor
public class HqlUserRepository implements UserRepository {

    private final SessionFactory sf;

    private static final Logger LOG = LoggerFactory.getLogger(HqlUserRepository.class.getName());

    public static final String USERS_TABLE = "auto_users";
    public static final String TRUNCATE_TABLE = String.format(
            "truncate table %s restart identity cascade",
            USERS_TABLE
    );
    public static final String USER_MODEL = "User";
    public static final String LOGIN = "fLogin";
    public static final String PASSWORD = "fPass";
    public static final String ID = "fId";
    public static final String UPDATE_STATEMENT = String.format(
            "UPDATE %s SET login = :%s, password = :%s WHERE id = :%s",
            USER_MODEL, LOGIN, PASSWORD, ID
    );
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
    public Optional<User> create(User user) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
            return Optional.of(user);
        } catch (Exception e) {
            LOG.error("Exception in UserRepository", e);
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return Optional.empty();
    }

    /**
     * Обновить в базе пользователя.
     * @param user пользователь.
     */
    @Override
    public void update(User user) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery(UPDATE_STATEMENT)
                    .setParameter(LOGIN, user.getLogin())
                    .setParameter(PASSWORD, user.getPassword())
                    .setParameter(ID, user.getId())
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            LOG.error("Exception in UserRepository", e);
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    /**
     * Удалить пользователя по id.
     * @param userId ID
     */
    @Override
    public void delete(int userId) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery(DELETE_STATEMENT)
                    .setParameter(ID, userId)
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            LOG.error("Exception in UserRepository", e);
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    /**
     * Список пользователь отсортированных по id.
     * @return список пользователей.
     */
    @Override
    public List<User> findAllOrderById() {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            Query<User> query = session.createQuery(FIND_ALL_ORDER_BY_ID_STATEMENT, User.class);
            session.getTransaction().commit();
            return query.list();
        } catch (Exception e) {
            LOG.error("Exception in UserRepository", e);
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return new ArrayList<>();
    }

    /**
     * Найти пользователя по ID
     * @return пользователь.
     */
    @Override
    public Optional<User> findById(int id) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            Query<User> query = session.createQuery(FIND_BY_ID_STATEMENT, User.class);
            query.setParameter(ID, id);
            session.getTransaction().commit();
            return query.uniqueResultOptional();
        } catch (Exception e) {
            LOG.error("Exception in UserRepository", e);
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return Optional.empty();
    }

    /**
     * Список пользователей по login LIKE %key%
     * @param key key
     * @return список пользователей.
     */
    @Override
    public List<User> findByLikeLogin(String key) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            Query<User> query = session.createQuery(FIND_BY_LOGIN_LIKE_STATEMENT, User.class);
            query.setParameter(KEY, "%" + key + "%");
            session.getTransaction().commit();
            return query.list();
        } catch (Exception e) {
            LOG.error("Exception in UserRepository", e);
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return new ArrayList<>();
    }

    /**
     * Найти пользователя по login.
     * @param login login.
     * @return Optional or user.
     */
    @Override
    public Optional<User> findByLogin(String login) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            Query<User> query = session.createQuery(FIND_BY_LOGIN_STATEMENT, User.class);
            query.setParameter(LOGIN, login);
            session.getTransaction().commit();
            return query.uniqueResultOptional();
        } catch (Exception e) {
            LOG.error("Exception in UserRepository", e);
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return Optional.empty();
    }

    public void truncate() {
        Session session = sf.openSession();
        try {
            session = sf.openSession();
            session.beginTransaction();
            session.createSQLQuery(TRUNCATE_TABLE).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            LOG.error("Exception in UserRepository", e);
            session.getTransaction().rollback();
        } finally {
            session.close();
        }

    }
}
