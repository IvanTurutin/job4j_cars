package ru.job4j.cars.service;

import ru.job4j.cars.dto.PostDto;
import ru.job4j.cars.model.*;
import ru.job4j.cars.search_attributes.SearchAttribute;

import java.util.List;
import java.util.Optional;

/**
 * Сервис объявлений
 * @see Post
 */
public interface PostService {
    /**
     * Обрабатывает запрос на сохранение объявления.
     *
     * @param postDto DTO объект объявления.
     * @return true если успех, и false если неудача
     */
    boolean add(PostDto postDto);

    /**
     * Обрабатывает обновление объявления.
     *
     * @param postDto    объявление.
     * @return true если успех, и false если неудача
     */
    //boolean update(Post post, FileDto fileDto);
    boolean update(PostDto postDto);

    /**
     * Обрабатывает запрос на удаление объявления.
     * @param postId ID
     * @return true если успех, и false если неудача
     */
    boolean delete(int postId);

    /**
     * Обрабатывает запрос на поиск всех объявлений.
     * @return список DTO объектов объявлений.
     */
    List<PostDto> findAll();

    /**
     * Находит все объявления доступные для просмотра пользователю и приводит дату и время публикации объявлений к
     * часовому поясу пользователя
     * @param user пользователь, с установленным часовым поясом
     * @return список всех доступных пользователю объявлений
     */
    List<PostDto> findAll(User user);

    /**
     * Обрабатывает запрос на поиск объявления по ID
     * @return объявление обернутое в Optional если найдено, и Optional.empty() если не найдено.
     */
    Optional<PostDto> findById(int id);

    /**
     * Обрабатывает запрос на поиск объявления по ID учитывает часовой пояс пользователя
     * @param user пользователь для которого требуется установить часовой пояс
     * @return объявление обернутое в Optional если найдено, и Optional.empty() если не найдено.
     */
    Optional<PostDto> findById(int id, User user);


    /**
     * Обрабатывает запрос на поиск списка объявлений, принадлежащих пользователю
     * @param user пользователь
     * @return список объявлений.
     */
    List<Post> findByUser(User user);

    /**
     * Обрабатывает запрос на поиск объявлений за последние дни
     * @param days количество дней, за которые требуется найти объявления
     * @return список объявлений удовлетворяющих требованию
     */
    List<Post> findLastDays(int days);

    /**
     * Обрабатывает запрос на поиск всех объявлений на которые подписан пользователь
     * @param user пользователь для которого осуществляется поиск
     * @return список объявлений
     */
    List<Post> findBySubscribedUser(User user);

    /**
     * Обрабатывает запрос на поиск объявлений удовлетворяющих атрибутам поиска
     * @param attributes список атрибутов поиска
     * @return список объявлений
     */
    List<PostDto> findBySearchAttributes(List<SearchAttribute> attributes);

    /**
     * Обрабатывает запрос на поиск объявлений удовлетворяющих атрибутам поиска, при этом устанавливается часовой пояс
     * по настройкам пользователя
     * @param attributes список атрибутов поиска
     * @param user пользователь, по которому устанавливается часовой пояс
     * @return список объявлений
     */
    List<PostDto> findBySearchAttributes(List<SearchAttribute> attributes, User user);
}
