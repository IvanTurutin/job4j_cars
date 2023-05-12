package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cars.dto.PostDto;
import ru.job4j.cars.model.*;
import ru.job4j.cars.repository.PostRepository;
import ru.job4j.cars.searchattributes.SearchAttribute;

import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@ThreadSafe
@Service
@AllArgsConstructor
public class SimplePostService implements PostService {

    private final PostRepository repository;
    private final UserService userService;
    private final BodyService bodyService;
    private final CarModelService carModelService;
    private final TransmissionService transmissionService;
    private final EngineService engineService;
    private final OwnerService ownerService;
    private final FileService fileService;

    @Override
    public boolean add(PostDto postDto) {
        Post post = postDtoToPost(postDto);
        System.out.println("post at add() postService = " + post);
        return repository.add(post).isPresent();
    }

    @Override
    public boolean update(PostDto postDto) {
        Post post = postDtoToPost(postDto);
        System.out.println("post at update() postService = " + post);
        return repository.update(post);
    }

    /**
     * Преобразует PostDto в Post
     * @param postDto исходный PostDto
     * @return сформированный Post
     * @throws IllegalArgumentException !!!!! Откуда берется исключение???
     */
    private Post postDtoToPost(PostDto postDto) throws IllegalArgumentException {
        System.out.println("postDto at postDtoToPost() = " + postDto);
        Post post = new Post();
        Optional<Post> postOptional = repository.findById(postDto.getId());
        if (postOptional.isPresent()) {
            post.setPriceHistory(postOptional.get().getPriceHistory());
            post.setUsers(postOptional.get().getUsers());
        }
        post.setId(postDto.getId());
        post.setText(postDto.getText());
        post.setCreated(postDto.getCreated());
        post.setUser(checkModel(User.class, () -> userService.findById(postDto.getUserId())));
        post.setCar(formCar(postDto.getCar()));
        post.addPrice(postDto.getPrice());
        post.setFiles(fileService.fileDtoListToFileList(postDto.getFiles()));
        post.setPublish(postDto.isPublish());
        /*System.out.println("post at postDtoToPost() after form = " + post);*/
        return post;
    }

    private Car formCar(Car car) throws IllegalArgumentException {
        car.setBody(checkModel(Body.class, () -> bodyService.findById(car.getBody().getId())));
        car.setCarModel(checkModel(CarModel.class, () -> carModelService.findById(car.getCarModel().getId())));
        car.setTransmission(checkModel(Transmission.class, () -> transmissionService.findById(car.getTransmission().getId())));
        car.setEngine(checkModel(Engine.class, () -> engineService.findById(car.getEngine().getId())));
        car.setOwner(checkModel(Owner.class, () -> ownerService.findById(car.getOwner().getId())));
        car.setCarOwners(car.getCarOwners().stream().filter(co -> co.getOwner() != null).collect(Collectors.toList()));
        car.getCarOwners().forEach(co -> {
            co.setCar(car);
            co.setOwner(checkModel(Owner.class, () -> ownerService.findById(co.getOwner().getId())));
        });
        return car;
    }

    private <T> T checkModel(Class<T> model, Supplier<Optional<T>> supplier) {
        Optional<T> optionalModel = supplier.get();
        if (optionalModel.isEmpty()) {
            throw new IllegalArgumentException(model.getSimpleName() + " with specified id isn't find.");
        }
        System.out.println("Model = " + model.getSimpleName() + ". " + optionalModel.get());
        return optionalModel.get();
    }

    @Override
    public boolean delete(int postId) {
        return repository.findById(postId).isPresent() && repository.delete(postId);
    }

    @Override
    public List<PostDto> findAll() {
        return repository.findAllOrderById().stream().map(this::postToPostDtoLight).toList();
    }

    public List<PostDto> findAllActive() {
        return null;
    }

    private PostDto postToPostDto(Post post) {
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setText(post.getText());
        postDto.setCreated(post.getCreated());
        postDto.setUserId(post.getUser().getId());
        postDto.setUserName(post.getUser().getName());
        postDto.setCar(post.getCar());
        if (post.getPriceHistory().size() > 0) {
            postDto.setPrice(post.getPriceHistory().get(post.getPriceHistory().size() - 1).getAfter());
        }
        postDto.setPublish(post.isPublish());
        postDto.setFiles(fileService.fileListToFileDtoList(post.getFiles()));
        postDto.setPublish(post.isPublish());
        return postDto;
    }

    private PostDto postToPostDtoLight(Post post) {
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setUserId(post.getUser().getId());
        postDto.setUserName(post.getUser().getName());
        postDto.setText(post.getText());
        postDto.setCreated(post.getCreated());
        postDto.setCar(post.getCar());
        postDto.setPrice(post.getPriceHistory().get(post.getPriceHistory().size() - 1).getAfter());
        postDto.setPublish(post.isPublish());
        postDto.setFiles(fileService.fileListToFileDtoListLight(post.getFiles()));
        postDto.setPublish(post.isPublish());
        return postDto;
    }

    @Override
    public List<PostDto> findAll(User user) {

        List<PostDto> posts = findAll();
        posts.forEach(p -> setTimeZoneToPost(p, user));
        return posts;
    }

    private void setTimeZone(PostDto post, String zoneId) {
        post.setCreated(
                post.getCreated().atZone(
                                ZoneId.of(TimeZone.getDefault().toZoneId().getId()))
                        .withZoneSameInstant(ZoneId.of(zoneId))
                        .toLocalDateTime()
        );
    }

    private void setDefaultTimeZone(PostDto post) {
        post.setCreated(
                post.getCreated().atZone(TimeZone.getDefault().toZoneId()).toLocalDateTime()
        );
    }

    @Override
    public Optional<PostDto> findById(int id) {
        return repository.findById(id).map(this::postToPostDto);
    }

    @Override
    public Optional<PostDto> findById(int id, User user) {
        Optional<PostDto> postDto = findById(id);
        postDto.ifPresent(p -> setTimeZoneToPost(p, user));
        return postDto;
    }


    @Override
    public List<Post> findByUser(User user) {
        return repository.findByUser(user);
    }

    @Override
    public List<Post> findLastDays(int days) {
        return repository.findLastDays(days);
    }

    @Override
    public List<Post> findBySubscribedUser(User user) {
        return repository.findBySubscribedUser(user);
    }

    @Override
    public List<PostDto> findBySearchAttributes(List<SearchAttribute> characts) {
        return repository.findBySearchAttributes(characts).stream().map(this::postToPostDto).toList();
    }

    @Override
    public List<PostDto> findBySearchAttributes(List<SearchAttribute> attributes, User user) {
        List<PostDto> postDtoList = findBySearchAttributes(attributes);
        postDtoList.forEach(p -> setTimeZoneToPost(p, user));
        return postDtoList;
    }

    /**
     * Устанавливает часовой пояс в объявление
     * @param postDto DTO объявления
     * @param user пользователь, для которого устанавливается часовой пояс
     */
    private void setTimeZoneToPost(PostDto postDto, User user) {
        if (user != null && user.getTimeZone() != null) {
            setTimeZone(postDto, user.getTimeZone().getZoneId());
        } else {
            setDefaultTimeZone(postDto);
        }
    }

}
