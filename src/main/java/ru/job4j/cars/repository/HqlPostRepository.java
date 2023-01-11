package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.model.User;

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

    public static final String DELETE_STATEMENT = String.format(
            "DELETE %s WHERE id = :%s",
            MODEL, ID
    );
    public static final String FIND_ALL_STATEMENT = String.format("from %s", MODEL);
    public static final String FIND_ALL_ORDER_BY_ID_STATEMENT = FIND_ALL_STATEMENT + " order by id";
    public static final String FIND_BY_ID_STATEMENT = FIND_ALL_STATEMENT + String.format(" where id = :%s", ID);
    public static final String FIND_BY_USER_STATEMENT = FIND_ALL_STATEMENT
            + String.format(" where auto_user_id = :%s", ID);
    public static final String TRUNCATE_TABLE = String.format("DELETE FROM %s", MODEL);


    @Override
    public Post create(Post post) {
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

    public void truncate() {
        crudRepository.run(
                TRUNCATE_TABLE,
                new HashMap<>());
    }

}
