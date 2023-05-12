package ru.job4j.cars.dto;

import lombok.*;
import org.springframework.stereotype.Component;

/**
 * DTO для объектов типа User
 */
@Component
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    /**
     * Идентификатор объекта
     */
    @EqualsAndHashCode.Include
    private int id;
    /**
     * Имя файла
     */
    private String name;

    /**
     * Телефон продавца
     */
    private String phone;
}
