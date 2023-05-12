package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cars.dto.UserDto;
import ru.job4j.cars.model.User;
import ru.job4j.cars.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@ThreadSafe
@Service
@AllArgsConstructor
public class SimpleUserService implements UserService {

    private final UserRepository repository;
    private final TimeZoneService timeZoneService;

    @Override
    public boolean create(User user) {
        user.setTimeZone(timeZoneService.checkTimeZone(user.getTimeZone().getId()));
        return repository.create(user).isPresent();
    }


    @Override
    public Optional<User> update(User user) {
        user.setTimeZone(timeZoneService.checkTimeZone(user.getTimeZone().getId()));
        return repository.update(user) ? repository.findById(user.getId()) : Optional.empty();
    }

    @Override
    public boolean delete(int userId) {
        return repository.findById(userId).isPresent() && repository.delete(userId);
    }

    @Override
    public List<User> findAllOrderById() {
        return repository.findAllOrderById();
    }

    @Override
    public Optional<User> findById(int id) {
        return repository.findById(id);
    }

    @Override
    public Optional<UserDto> findByIdUserDto(int id) {
        Optional<User> user = repository.findById(id);
        return user.map(this::userToUserDto);
    }

    /**
     * Метод преобразует объект User в UserDto
     * @param user объект User
     * @return объект UserDto
     */
    private UserDto userToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setPhone(user.getPhone());
        return userDto;
    }

    @Override
    public List<User> findByLikeLogin(String key) {
        return repository.findByLikeLogin(key);
    }

    @Override
    public Optional<User> findByLoginAndPassword(String login, String password) {
        return repository.findByLoginAndPassword(login, password);
    }
}
