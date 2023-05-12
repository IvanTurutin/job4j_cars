package ru.job4j.cars.dto;

import lombok.*;
import org.springframework.stereotype.Component;
import ru.job4j.cars.model.Body;
import ru.job4j.cars.model.CarModel;
import ru.job4j.cars.model.Engine;
import ru.job4j.cars.model.Transmission;
import ru.job4j.cars.searchattributes.PostIsPublish;
import ru.job4j.cars.searchattributes.SearchAttribute;

import java.util.ArrayList;
import java.util.List;

@Component
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SearchAttributeDto {
    private Body body;
    private CarModel carModel;
    private Engine engine;
    private Transmission transmission;

    public List<SearchAttribute> getListOfAttributes() {
        List<SearchAttribute> searchAttributes = new ArrayList<>(new ArrayList<>(List.of(body, carModel, engine, transmission)).stream()
                .filter(sa -> (Integer) sa.getCharactValue() != 0)
                .toList());
        searchAttributes.add(new PostIsPublish(true));
        return searchAttributes;
    }
}
