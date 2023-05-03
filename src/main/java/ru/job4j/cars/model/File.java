package ru.job4j.cars.model;

import lombok.*;

import javax.persistence.*;

/**
 * Модель данных файла
 */
@Entity
@Table(name = "files")
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class File implements SearchAttribute {

    /**
     * Название типа аттрибута поиска для клаcса File
     */
    public static final String FILES = "files";

    public File(String name, String path) {
        this.name = name;
        this.path = path;
    }

    /**
     * Идентификатор файла
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    /**
     * Имя файла
     */
    private String name;

    /**
     * Путь к файлу
     */
    private String path;
/*
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id")
    private Post post;
*/
    @Override
    public String getType() {
        return FILES;
    }
}
