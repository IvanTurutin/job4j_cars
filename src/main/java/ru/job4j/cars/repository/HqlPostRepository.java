package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.model.User;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@ThreadSafe
@AllArgsConstructor
public class HqlPostRepository implements PostRepository {

    private final CrudRepository crudRepository;

    public static final String MODEL = "Post";
    public static final String ID = "fId";
    public static final String FROM = "fFrom";
    public static final String CAR_NAME = "fCarName";
    public static final String FILES = "files";


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
    public static final String FIND_CAR_NAME = FIND_ALL_STATEMENT + String.format(" where t.car.name = :%s", CAR_NAME);
    public static final String FIND_WITH_PHOTO = FIND_ALL_STATEMENT + String.format(" where t.%s.size > 0", FILES);
    public static final String FIND_BY_USER_STATEMENT = FIND_ALL_STATEMENT
            + String.format(" where t.user.id = :%s", ID);
    public static final String TRUNCATE_TABLE = String.format("DELETE FROM %s", MODEL);


    @Override
    public Post add(Post post) {
        crudRepository.run(session -> session.persist(post));
        return post;
    }

    @Override
    public void update(Post post) {
        crudRepository.run(session -> session.merge(post));
    }

    @Override
    public void delete(int postId) {
        crudRepository.run(
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

    /**
     * Очищает таблицу от записей
     */
    public void truncateTable() {
        crudRepository.run(
                TRUNCATE_TABLE,
                new HashMap<>());
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
    public List<Post> findWithPhoto() {
        return crudRepository.query(FIND_WITH_PHOTO, Post.class);
    }

    @Override
    public List<Post> findCarName(String carName) {
        return crudRepository.query(
                FIND_CAR_NAME, Post.class,
                Map.of(CAR_NAME, carName)
        );
    }
}
