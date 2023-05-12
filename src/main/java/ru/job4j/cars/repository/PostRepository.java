package ru.job4j.cars.repository;

import ru.job4j.cars.model.*;
import ru.job4j.cars.searchattributes.SearchAttribute;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий объявлений
 * @see ru.job4j.cars.model.Post
 */
public interface PostRepository {
    /**
     * Сохраняет объявление в базе.
     * @param post объявление.
     * @return объявление в Optional если успех, и Optional.empty() если неудача
     */
    Optional<Post> add(Post post);

    /**
     * Обновляет в базе объявление.
     * @param post объявление.
     * @return true если обновлен, false если не обновлено
     */
    boolean update(Post post);

    /**
     * Удаляет объявление по id.
     * @param postId ID
     * @return true если удален, false если не удален
     */
    boolean delete(int postId);

    /**
     * Список объявлений отсортированных по id.
     * @return список объявлений.
     */
    List<Post> findAllOrderById();

    /**
     * Найти объявление по ID
     * @return объявление обернутое в Optional если найдено, и Optional.empty() если не найдено.
     */
    Optional<Post> findById(int id);

    /**
     * Список объявлений, принадлежащих пользователю
     * @param user пользователь
     * @return список объявлений.
     */
    List<Post> findByUser(User user);

    /**
     * Осуществляет поиск объявлений за последние дни
     * @param days количество дней, за которые требуется найти объявления
     * @return список объявлений удовлетворяющих требованию
     */
    List<Post> findLastDays(int days);

    /**
     * Осуществляет поиск всех объявлений на которые подписан пользователь
     * @param user пользователь для которого осуществляется поиск
     * @return список объявлений
     */
    List<Post> findBySubscribedUser(User user);

    /**
     * Осуществляет поиск объявлений удовлетворяющих атрибутам поиска
     * @param characts список атрибутов поиска
     * @return список объявлений
     */
    List<Post> findBySearchAttributes(List<SearchAttribute> characts);

}
