package ru.job4j.cars.util;

import org.springframework.web.multipart.MultipartFile;
import ru.job4j.cars.dto.PostDto;
import ru.job4j.cars.model.User;

import javax.servlet.http.HttpSession;
import java.util.List;

public final class ControllerUtility {
    private ControllerUtility() {

    }

    private static final String GUEST = "Гость";

    public static User checkUser(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setName(GUEST);
        }
        return user;
    }

    public static PostDto formPostDto(PostDto post, HttpSession session, List<MultipartFile> mPFiles) {
        PostDto sessionPost = (PostDto) session.getAttribute("post");
        if (sessionPost != null) {
            post.addFileDtos(sessionPost.getFiles());
        }
        post.addFiles(mPFiles);
        return post;
    }
}
