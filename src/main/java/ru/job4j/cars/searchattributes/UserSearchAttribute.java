package ru.job4j.cars.searchattributes;

import ru.job4j.cars.model.User;
import ru.job4j.cars.repository.HqlPostRepository;

public class UserSearchAttribute implements SearchAttribute {
    private final User user;
    private final static String FIELD_NAME = "user";
    private final static String CHARACTERISTIC = "fUserId";

    public UserSearchAttribute(User user) {
        this.user = user;
    }

    @Override
    public String getSearchAttribute() {
        return String.format(" %s.%s.id = :%s", HqlPostRepository.TABLE_ALIAS, FIELD_NAME, CHARACTERISTIC);
    }

    @Override
    public Object getCharactValue() {
        return user.getId();
    }

    @Override
    public String getCharacteristic() {
        return CHARACTERISTIC;
    }
}
