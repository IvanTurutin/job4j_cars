package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.*;
import ru.job4j.cars.searchattributes.SearchAttribute;

import java.time.LocalDateTime;
import java.util.*;

@ThreadSafe
@Repository
@AllArgsConstructor
public class HqlPostRepository implements PostRepository {

    private final CrudRepository crudRepository;
    private static final Logger LOG = LoggerFactory.getLogger(HqlPostRepository.class.getName());

    public static final String TABLE_ALIAS = "t";
    public static final String MODEL = "Post";
    public static final String ID = "fId";
    public static final String FROM = "fFrom";
    public static final String USER_ID = "fUserId";
    public static final String DELETE_STATEMENT = String.format(
            "DELETE %s WHERE id = :%s",
            MODEL, ID
    );
    public static final String FIND_ALL_STATEMENT = String.format(
            "select %s from %s as %s", TABLE_ALIAS, MODEL, TABLE_ALIAS
    );
    public static final String FIND_ALL_ORDER_BY_ID_STATEMENT = FIND_ALL_STATEMENT
            + String.format(" order by %s.id", TABLE_ALIAS);
    public static final String FIND_BY_ID_STATEMENT = FIND_ALL_STATEMENT
            + String.format(" where %s.id = :%s", TABLE_ALIAS, ID);
    public static final String FIND_LAST_DAYS = FIND_ALL_STATEMENT
            + String.format(" where %s.created > :%s", TABLE_ALIAS, FROM);
    public static final String FIND_BY_USER_STATEMENT = FIND_ALL_STATEMENT
            + String.format(" where %s.user.id = :%s", TABLE_ALIAS, ID);
    public static final String TRUNCATE_TABLE = String.format("DELETE FROM %s", MODEL);
    public static final String FIND_BY_SUBSCRIBED_USER = FIND_ALL_STATEMENT
            + String.format(" join %s.users u where u.id = :%s", TABLE_ALIAS, USER_ID);


    @Override
    public Optional<Post> add(Post post) {
        return crudRepository.add(post);
    }

    @Override
    public boolean update(Post post) {
        return crudRepository.run(session -> session.merge(post));
    }

    @Override
    public boolean delete(int postId) {
        return crudRepository.query(
                DELETE_STATEMENT,
                Map.of(ID, postId)
        );
    }

    @Override
    public List<Post> findAllOrderById() {
        LOG.debug("FIND_ALL_ORDER_BY_ID_STATEMENT = " + FIND_ALL_ORDER_BY_ID_STATEMENT);
        return crudRepository.query(FIND_ALL_ORDER_BY_ID_STATEMENT, Post.class);
    }

    @Override
    public Optional<Post> findById(int id) {
        return crudRepository.optional(
                FIND_BY_ID_STATEMENT, Post.class,
                Map.of(ID, id)
        );
    }

    @Override
    public List<Post> findByUser(User user) {
        return crudRepository.query(
                FIND_BY_USER_STATEMENT, Post.class,
                Map.of(ID, user.getId())
        );
    }

    @Override
    public List<Post> findLastDays(int days) {
        if (days < 1) {
            throw new IllegalArgumentException("days must be more than 0");
        }
        return crudRepository.query(
                FIND_LAST_DAYS, Post.class,
                Map.of(FROM, LocalDateTime.now().minusDays(days))
        );
    }

    @Override
    public List<Post> findBySearchAttributes(List<SearchAttribute> attributes) {
        if (attributes.isEmpty()) {
            return findAllOrderById();
        }
        return execute(formStatement(new ArrayList<>(attributes)));
    }

    /**
     * Запускает поиск объявлений по критериям поиска.
     * @param helper объект Helper, содержащий недостающую часть запроса к БД и карту с параметрами запроса.
     * @return список объявлений
     */
    private List<Post> execute(Helper helper) {
        return crudRepository.query(
                FIND_ALL_STATEMENT + helper.statement, Post.class,
                helper.findAttr
        );
    }

    /**
     * Формирует вторую часть запроса для отбора объявлений по критериям поиска.
     * @param attributes список атрибутов поиска.
     * @return возвращает Helper
     */
    private Helper formStatement(List<SearchAttribute> attributes) {
        Helper helper = new Helper(new StringBuilder(), new HashMap<>());
        helper.statement.append(" where");
        for (int i = 0; i < attributes.size(); i++) {
            if (i == 0) {
                helper.statement.append(attributes.get(i).getSearchAttribute());
                helper.findAttr.put(attributes.get(i).getCharacteristic(), attributes.get(i).getCharactValue());
            } else {
                helper.statement.append(" and");
                helper.statement.append(attributes.get(i).getSearchAttribute());
                helper.findAttr.put(attributes.get(i).getCharacteristic(), attributes.get(i).getCharactValue());
            }
        }
        LOG.debug("helper at formStatement() = " + helper);
        return helper;
    }

    @Override
    public List<Post> findBySubscribedUser(User user) {
        return crudRepository.query(
                FIND_BY_SUBSCRIBED_USER,
                Post.class,
                Map.of(USER_ID, user.getId())
        );
    }

    /**
     * Вспомогательный класс для возможности передачи из метода набора необходимых объектов.
     */
    private class Helper {
        StringBuilder statement;
        Map<String, Object> findAttr;
        Helper(StringBuilder statement, Map<String, Object> query) {
            this.statement = statement;
            this.findAttr = query;
        }

        @Override
        public String toString() {
            return "Helper{"
                    + "statement=" + statement
                    + ", findAttr=" + findAttr
                    + '}';
        }
    }

    /**
     * Очищает таблицу от записей
     */
    public void truncateTable() {
        crudRepository.run(
                TRUNCATE_TABLE,
                new HashMap<>());
    }

    public List<Post> findPuplish(boolean isPublish) {
        String statement = "select t from Post as t where t.publish = " + isPublish;
        LOG.debug("statement at findPuplish() = " + statement);
        return crudRepository.query(statement, Post.class);
    }
}