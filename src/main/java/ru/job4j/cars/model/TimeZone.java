package ru.job4j.cars.model;

import lombok.*;

import javax.persistence.*;

/**
 * Модель данных временной зоны
 */
@Entity
@Table(name = "timezones")
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class TimeZone {
    /**
     * Идентификатор временной зоны
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    /**
     * Обозначение идентификатра временной зоны
     */
    @Column(unique = true)
    private String zoneId;

    public TimeZone(String zoneId) {
        this.zoneId = zoneId;
    }
}
