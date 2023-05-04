package ru.job4j.cars.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Модель данных изменения цены
 */
@Entity
@Table(name = "price_history")
@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PriceHistory {
    /**
     * Идентификатор изменения цены
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;
    /**
     * Цена до изменения
     */
    private int before;

    /**
     * Цена после изменения
     */
    private int after;

    /**
     * Дата изменения цены
     */
    private LocalDateTime created = LocalDateTime.now();

    public PriceHistory(int before, int after) {
        this.before = before;
        this.after = after;
    }

}
