package ru.job4j.cars.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.cars.dto.PostDto;
import ru.job4j.cars.model.*;
import ru.job4j.cars.service.*;
import ru.job4j.cars.util.ControllerUtility;

import javax.servlet.http.HttpSession;

/**
 * Контроллер владельцев
 */
@ThreadSafe
@Controller
@RequestMapping("/owners")
@AllArgsConstructor
@Slf4j
public class OwnerController {

    private final BodyService bodyService;
    private final CarModelService carModelService;
    private final TransmissionService transmissionService;
    private final EngineService engineService;
    private final OwnerService ownerService;

    /**
     * Обрабатывает запрос на отображение вида добавления нового владельца
     * @param model модель вида
     * @param session сессия подключения
     * @return название шаблона для формирования вида добавления нового владельца
     */
    @RequestMapping(value = "/new", method = RequestMethod.POST, params = "action=save")
    public String newOwner(HttpSession session, Model model, @ModelAttribute Owner owner) {
        model.addAttribute("user", ControllerUtility.checkUser(session));
        PostDto post = (PostDto) session.getAttribute("post");
        log.debug("post in newOwner() = " + post);
        if (!ownerService.add(owner)) {
            model.addAttribute("message", "Не удалось добавить нового владельца.");
            return "message/fail";
        }
        modelAddAttributesAddNew(session, model);
        model.addAttribute("post", post);
        return "post/addNew";
    }

    /**
     * Производит добавление аттрибутов в модель
     * @param model модель вида
     * @param session сессия подключения
     * @return модель вида
     */
    private Model modelAddAttributesAddNew(HttpSession session, Model model) {
        model.addAttribute("user", ControllerUtility.checkUser(session));
        model.addAttribute("bodies", bodyService.findAll());
        model.addAttribute("models", carModelService.findAll());
        model.addAttribute("transmissions", transmissionService.findAll());
        model.addAttribute("engines", engineService.findAll());
        model.addAttribute("owners", ownerService.findAll());
        return model;
    }

    /**
     * Производит выход из вида добавления нового владельца без его добавления. Выход осуществляется в вид редактирования
     * объявления
     * @param model модель вида
     * @param session сессия подключения
     * @return ссылку на вид редактирования объявления
     */
    @RequestMapping(value = "/new", method = RequestMethod.POST, params = "action=back")
    public String back(HttpSession session, Model model/*, @ModelAttribute Owner owner*/) {
        model.addAttribute("user", ControllerUtility.checkUser(session));
        PostDto post = (PostDto) session.getAttribute("post");
        log.debug("post in back() = " + post);
        modelAddAttributesAddNew(session, model);
        model.addAttribute("post", post);
        return "post/addNew";
    }

}
