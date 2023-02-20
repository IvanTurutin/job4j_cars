package ru.job4j.cars.repository;

import net.jcip.annotations.ThreadSafe;
import ru.job4j.cars.model.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@ThreadSafe
/*@AllArgsConstructor*/
public class HqlPostRepository implements PostRepository {

    private final CrudRepository crudRepository;

    public static final String MODEL = "Post";
    public static final String ID = "fId";
    public static final String FROM = "fFrom";
    public static final String CAR_MODEL = "fCarName";
    public static final String BODY = "fBody";
    public static final String TRANSMISSION = "fTransmission";
    public static final String FILES = "files";
    private final Map<String, Object> findBy = new HashMap<>();;
    private final StringBuilder selection = new StringBuilder();

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
    public static final String FIND_BY = FIND_ALL_STATEMENT + " where";
/*
    public static final String FIND_CAR_MODEL = FIND_ALL_STATEMENT + String.format(" where t.car.carModel.id = :%s", CAR_MODEL);
    public static final String FIND_BY_BODY = FIND_ALL_STATEMENT + String.format(" where t.car.body.id = :%s", BODY);
    public static final String FIND_BY_TRANSMISSION = FIND_ALL_STATEMENT + String.format(" where t.car.body.id = :%s", TRANSMISSION);
*/
    public static final String FIND_WITH_PHOTO = FIND_ALL_STATEMENT + String.format(" where t.%s.size > 0", FILES);
    public static final String FIND_BY_USER_STATEMENT = FIND_ALL_STATEMENT
            + String.format(" where t.user.id = :%s", ID);
    public static final String TRUNCATE_TABLE = String.format("DELETE FROM %s", MODEL);

    public HqlPostRepository(CrudRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    @Override
    public Post add(Post post) {
        crudRepository.run(session -> session.save(post));
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
    public PostRepository findByCarModel(CarModel carModel) {
        findBy.put(CAR_MODEL, carModel.getId());
        if (selection.isEmpty()) {
            selection.append(String.format(" t.car.carModel.id = :%s", CAR_MODEL));
        } else {
            selection.append(String.format(" and t.car.carModel.id = :%s", CAR_MODEL));
        }
        return this;
    }

    @Override
    public PostRepository findByBody(Body body) {
        findBy.put(BODY, body.getId());
        if (selection.isEmpty()) {
            selection.append(String.format(" t.car.body.id = :%s", BODY));
        } else {
            selection.append(String.format(" and t.car.body.id = :%s", BODY));
        }
        return this;
    }

    @Override
    public PostRepository findByTransmission(Transmission transmission) {
        findBy.put(TRANSMISSION, transmission.getId());
        if (selection.isEmpty()) {
            selection.append(String.format(" t.car.transmission.id = :%s", TRANSMISSION));
        } else {
            selection.append(String.format(" and t.car.transmission.id = :%s", TRANSMISSION));
        }
        return this;
    }

    @Override
    public List<Post> execute() {
        List<Post> posts = crudRepository.query(
                FIND_BY + selection, Post.class,
                findBy
        );
        findBy.clear();
        selection.delete(0, selection.length());
        return posts;
    }

    public List<Post> findBySubscribedUser(User user) {
        return crudRepository.query(
                "select p from Post as p "
                        + "join p.users u "
                        + "where u.id = :fUserId",
                Post.class,
                Map.of("fUserId", user.getId())
        );
/*
                select * from auto_posts as p
                left join posts_users as pu on p.id = pu.post_id
                left join auto_users as u on pu.user_id = u.id
                where u.id = 3
*/
    }

}
