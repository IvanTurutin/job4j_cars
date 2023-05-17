package ru.job4j.cars.repository;

import org.hibernate.Session;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Предоставляет возможность выполнения базовых команд по сохранению, чтению, обновлению, удалению сущностей в БД
 */
public interface CrudRepository {

    /**
     * Обрабатывает JPA команды без параметров (такие как: обновить)
     * @param command сессия подключения с базой данных с примененной в ней командой действия над передаваемым объектом
     * @return true при удачном выполнении команды, false при неудачном выполнении команды
     */
    boolean run(Consumer<Session> command);

    /**
     * Позволяет выполнять запросы HQL/SQL с параметрами
     * @param query шаблон запроса HQL/SQL
     * @param args параметры запроса для формирования запроса к БД
     */
    void run(String query, Map<String, Object> args);

    /**
     * Добавляет объект в БД
     * @param model добавляемый объект
     * @return сохраненный объект обернутый в Optional если сохранение успешное, или Optional.empty если сохранение
     * не удалось
     * @param <T> тип сохраняемого объекта
     */
    <T> Optional<T> add(T model);

    /**
     * Удаляет объект из БД и возвращает
     * @param query запрос к базе данных
     * @param args аргументы для запроса
     * @param model объект, который требуется удалить
     * @return удаленный объект обернутый в Optional если удаление успешное, или Optional.empty если удаление
     * не удалось
     * @param <T> тип удаляемого объекта
     */
    <T> Optional<T> delete(String query, Map<String, Object> args, T model);

    /**
     * Позволяет выполнять запросы HQL/SQL с параметрами, которые предполагают извлечение из базы данных одного объекта
     * @param query шаблон запроса HQL/SQL
     * @param cl имя класса в тип которого необходимо собрать объект
     * @param args параметры запроса для формирования запроса к БД
     * @return Optional сформированного объекта
     * @param <T> тип, в который собирается объект
     */
    <T> Optional<T> optional(String query, Class<T> cl, Map<String, Object> args);

    /**
     * Позволяет выполнять запросы HQL/SQL без параметров, которые предполагают извлечение из базы данных группу объектов
     * @param query шаблон запроса HQL/SQL
     * @param cl имя класса в тип которого необходимо собрать объект
     * @return список объектов удовлетворяющих запросу
     * @param <T> тип, в который собирается объект
     */
    <T> List<T> query(String query, Class<T> cl);

    /**
     * Позволяет выполнять запросы HQL/SQL с параметрами, которые предполагают извлечение из базы данных группы объектов
     * @param query шаблон запроса HQL/SQL
     * @param cl имя класса в тип которого необходимо собрать объект
     * @param args параметры запроса для формирования запроса к БД
     * @return список объектов удовлетворяющих запросу
     * @param <T> тип, в который собирается объект
     */
    <T> List<T> query(String query, Class<T> cl, Map<String, Object> args);

    /**
     * Позволяет выполнять запросы HQL/SQL с параметрами, которые не предполагают извлечение из базы данных объектов
     * @param query шаблон запроса HQL/SQL
     * @param args параметры запроса для формирования запроса к БД
     * @return true при удачном выполнении команды, false при неудачном выполнении команды
     */
    boolean query(String query, Map<String, Object> args);

    /**
     * Запускает транзакцию на выполнение сформированного запроса
     * @param command сессия подключения с базой данных с запросом
     * @return результат выполнения запроса
     * @param <T> тип объекта, возвращаемый в результате выполнения транзакции
     * @throws Exception исключение, возникающее при работе с базой данных
     */
    <T> T tx(Function<Session, T> command) throws Exception;
}
