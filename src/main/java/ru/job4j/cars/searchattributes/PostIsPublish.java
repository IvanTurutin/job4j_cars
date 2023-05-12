package ru.job4j.cars.searchattributes;

import lombok.ToString;
import ru.job4j.cars.repository.HqlPostRepository;

@ToString
public class PostIsPublish implements SearchAttribute {

    private final boolean isPublish;
    private final static String FIELD_NAME = "publish";
    private final static String CHARACTERISTIC = "fIsPublish";

    public PostIsPublish(boolean isPublish) {
        this.isPublish = isPublish;
    }

    @Override
    public String getSearchAttribute() {
        return String.format(" %s.%s = :%s", HqlPostRepository.TABLE_ALIAS, FIELD_NAME, CHARACTERISTIC);
    }

    @Override
    public Object getCharactValue() {
        return isPublish;
    }

    @Override
    public String getCharacteristic() {
        return CHARACTERISTIC;
    }
}
