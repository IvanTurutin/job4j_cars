package ru.job4j.cars.model;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Модель данных Владелец автомобиля
 */
@Entity
@Table(name = "car_owners")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class CarOwner {

    /**
     * id записи в car_owners
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    /**
     * Владелец автомобиля
     */
    @ManyToOne(/*fetch = FetchType.LAZY,*/)
    @JoinColumn(name = "owner_id")
    @EqualsAndHashCode.Include
    private Owner owner;

    /**
     * Автомобиль
     */
    @ManyToOne(fetch = FetchType.LAZY/*, cascade = CascadeType.ALL*/)
    @JoinColumn(name = "car_id")
    @EqualsAndHashCode.Include
    private Car car;


    /**
     * Дата начала владения автомобилем
     */
    @EqualsAndHashCode.Include
    private LocalDateTime startAt = LocalDateTime.now().withNano(0);

    /**
     * Дата окончания владения автомобилем
     */
    @EqualsAndHashCode.Include
    private LocalDateTime endAt = LocalDateTime.now().withNano(0);

    public CarOwner(Owner owner, Car car) {
        this.owner = owner;
        this.car = car;
    }

    @Override
    public String toString() {
        String ownerStr = owner != null ? String.valueOf(owner.getId()) : "null";
        String carStr = car != null ? String.valueOf(car.getId()) : "null";
        return "CarOwner{"
                + "id=" + id
                + ", ownerId=" + ownerStr
                + ", carId=" + carStr
                + ", startAt=" + startAt
                + ", endAt=" + endAt
                + '}';
    }
}
