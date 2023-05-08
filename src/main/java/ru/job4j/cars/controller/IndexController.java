package ru.job4j.cars.controller;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.cars.model.User;
import ru.job4j.cars.search_attributes.PostIsPublish;
import ru.job4j.cars.service.PostService;
import ru.job4j.cars.util.ControllerUtility;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Контроллер стартовой страницы
 */
@ThreadSafe
@Controller
@AllArgsConstructor
public class IndexController {
    private final PostService postService;

    /**
     * Принимает запрос на отображение стартовой страницы
     * @param model модель вида
     * @param session сессия подключения
     * @return названия шаблона, которое требуется для формирования вида и показа пользователю
     */
    @GetMapping("/index")
    public String index(Model model, HttpSession session) {
        model.addAttribute("user", ControllerUtility.checkUser(session));
        model.addAttribute("posts", postService.findBySearchAttributes(List.of(new PostIsPublish(true)),
                (User) session.getAttribute("user")));
        model.addAttribute("showMode", "all");
        return "index";
    }

}
