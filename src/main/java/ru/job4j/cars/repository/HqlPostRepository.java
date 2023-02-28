package ru.job4j.cars.repository;

import net.jcip.annotations.ThreadSafe;
import ru.job4j.cars.model.*;

import java.time.LocalDateTime;
import java.util.*;

@ThreadSafe
public class HqlPostRepository implements PostRepository {

    private final CrudRepository crudRepository;

    public static final String MODEL = "Post";
    public static final String ID = "fId";
    public static final String FROM = "fFrom";
    public static final String CHARACTERISTIC = "fCharacteristic";
    public static final String USER_ID = "fUserId";

    public static final String QUANTITY = "fQuantity";
    public static final int QUANTITY_VALUE = 0;

    public static final String DELETE_STATEMENT = String.format(
            "DELETE %s WHERE id = :%s",
            MODEL, ID
    );
    public static final String FIND_ALL_STATEMENT = String.format(
            "select t from %s as t",
            MODEL
    );
    public static final String FIND_ALL_ORDER_BY_ID_STATEMENT = FIND_ALL_STATEMENT + " order by t.id";
    public static final String FIND_BY_ID_STATEMENT = FIND_ALL_STATEMENT + String.format(" where t.id = :%s", ID);
    public static final String FIND_LAST_DAYS = FIND_ALL_STATEMENT + String.format(" where t.created > :%s", FROM);
    public static final String FIND_BY_USER_STATEMENT = FIND_ALL_STATEMENT
            + String.format(" where t.user.id = :%s", ID);
    public static final String TRUNCATE_TABLE = String.format("DELETE FROM %s", MODEL);
    public static final String FIND_BY_SUBSCRIBED_USER = FIND_ALL_STATEMENT
            + String.format(" join t.users u where u.id = :%s", USER_ID);

    public HqlPostRepository(CrudRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    @Override
    public Optional<Post> add(Post post) {
        return crudRepository.run(session -> session.save(post))
                ? Optional.of(post)
                : Optional.empty();
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

    private List<Post> execute(Helper helper) {
        return crudRepository.query(
                FIND_ALL_STATEMENT + helper.statement, Post.class,
                helper.findAttr
        );
    }

    private Helper formStatement(List<SearchAttribute> attributes) {
        Helper helper = new Helper(new StringBuilder(), new HashMap<>());
        helper.statement.append(" where");
        for (int i = 0; i < attributes.size(); i++) {
            if (i > 0 && !attributes.get(i).getType().equals(File.FILES)) {
                helper.statement.append(String.format(" and t.car.%s.id = :%s", attributes.get(i).getType(), CHARACTERISTIC));
                helper.findAttr.put(CHARACTERISTIC, attributes.get(i).getId());
            } else if (i == 0 && attributes.get(i).getType().equals(File.FILES)) {
                helper.statement.append(String.format(" t.%s.size > :%s", attributes.get(i).getType(), QUANTITY));
                helper.findAttr.put(QUANTITY, QUANTITY_VALUE);
            } else if (i > 0 && attributes.get(i).getType().equals(File.FILES)) {
                helper.statement.append(String.format(" and t.%s.size > :%s", attributes.get(i).getType(), QUANTITY));
                helper.findAttr.put(QUANTITY, QUANTITY_VALUE);
            } else {
                helper.statement.append(String.format(" t.car.%s.id = :%s", attributes.get(i).getType(), CHARACTERISTIC));
                helper.findAttr.put(CHARACTERISTIC, attributes.get(i).getId());
            }
        }
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

    private class Helper {
        StringBuilder statement;
        Map<String, Object> findAttr;
        Helper(StringBuilder statement, Map<String, Object> query) {
            this.statement = statement;
            this.findAttr = query;
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

}
