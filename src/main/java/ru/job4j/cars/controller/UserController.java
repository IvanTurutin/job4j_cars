package ru.job4j.cars.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.cars.dto.UserDto;
import ru.job4j.cars.model.User;
import ru.job4j.cars.service.TimeZoneService;
import ru.job4j.cars.service.UserService;
import ru.job4j.cars.util.ControllerUtility;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@ThreadSafe
@Controller
@RequestMapping("/users")
@AllArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;
    private final TimeZoneService timeZoneService;

    /**
     * Принимает запрос на отображение вида добавления пользователя
     * @param model модель вида
     * @param session сессия подключения
     * @return назнвание шаблона вида добавления пользователя
     */
    @GetMapping("/formAdd")
    public String addUser(Model model, HttpSession session) {
        model.addAttribute("user", ControllerUtility.checkUser(session));
        model.addAttribute("timeZones", timeZoneService.findAll());
        return "user/add";
    }

    /**
     * Производит добавление пользователя в систему
     * @param user сформированный объект на основе введенных пользователем данных
     * @param session сессия подключения
     * @return перенаправляет на страницу /successRegistration при успешной регистрации,
     * и на страницу /failRegistration при неудачной регистрации
     */
    @PostMapping("/registration")
    public String registration(HttpSession session, Model model, @ModelAttribute User user) {
        model.addAttribute("user", ControllerUtility.checkUser(session));
        boolean regUser = userService.create(user);
        log.debug("user in registration" + user);
        if (!regUser) {
            model.addAttribute("message", "Пользователь с таким логином уже существует.");
            return "message/fail";
        }
        model.addAttribute("message", "Пользователь успешно зарегистрирован.");
        return "message/success";
    }

    /**
     * Принимает запрос на отображение вида добавления пользователя
     * @param model модель вида
     * @param session сессия подключения
     * @return назнвание шаблона вида добавления пользователя
     */
    @GetMapping("/formEdit")
    public String formEdit(Model model, HttpSession session) {
        model.addAttribute("user", ControllerUtility.checkUser(session));
        model.addAttribute("timeZones", timeZoneService.findAll());
        return "user/update";
    }

    /**
     * Производит добавление пользователя в систему
     * @param user сформированный объект на основе введенных пользователем данных
     * @param session сессия подключения
     * @return перенаправляет на страницу /successRegistration при успешной регистрации,
     * и на страницу /failRegistration при неудачной регистрации
     */
    @PostMapping("/edit")
    public String edit(HttpSession session, Model model, @ModelAttribute User user) {
        model.addAttribute("user", ControllerUtility.checkUser(session));
        Optional<User> userOptional = userService.update(user);
        if (userOptional.isEmpty()) {
            model.addAttribute("message", "Данные пользователя не обновлены");
            return "message/fail";
        }
        log.debug("user in edit user = " + userOptional.get());
        session.setAttribute("user", userOptional.get());
        model.addAttribute("user", ControllerUtility.checkUser(session));
        model.addAttribute("message", "Данные пользователя обновлены.");
        return "message/success";
    }



    /**
     * Принимает запрос на отображение вида авторизации пользователя
     * @param model модель вида
     * @param fail Параметр отвечающий за проверку верности введенного логина и пароля, (false - по умолчанию,
     *             true - отображается сообщение о неверно введенном логине или пароле)
     * @param session сессия подключения
     * @return название шаблона для авторизации пользователя
     */
    @GetMapping("/loginPage")
    public String loginPage(Model model, @RequestParam(name = "fail", required = false) Boolean fail, HttpSession session) {
        model.addAttribute("user", ControllerUtility.checkUser(session));
        model.addAttribute("fail", fail != null);
        return "user/login";
    }

    /**
     * Принимает запрос и проводит аутентификацию пользователя
     * @param user сфоримрованный объект User на основе введенных данных пользователем
     * @param req запрос сформированный браузером
     * @return В случае успешной аутентификации предоставляет права пользования сайтом и перенаправляет на вид
     * главной страницы, в случае провала аутентификации переводит параметр fail в значение true и перенаправляет на
     * вид авторизации с отображением сообщения о неверно введенными данными пользователя
     */
    @PostMapping("/login")
    public String login(@ModelAttribute User user, HttpServletRequest req) {
        Optional<User> userDb = userService.findByLoginAndPassword(
                user.getLogin(), user.getPassword()
        );
        if (userDb.isEmpty()) {
            return "redirect:/users/loginPage?fail=true";
        }
        HttpSession session = req.getSession();
        session.setAttribute("user", userDb.get());
        return "redirect:/index";
    }

    /**
     * Принимает запрос на выход пользователя из системы. Очищает сессию от всех атрибутов
     * @param session сессия подключения
     * @return перенаправляет на главную страницу
     */
    @GetMapping ("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/users/loginPage";
    }

    /**
     * Готовит модель вида к отображению данных о продавце
     * @param userId идентификатор продавца
     * @param model модель вида
     * @param session сессия подключения
     * @return ссылку на шаблон вида отображения данных о продавце
     */
    @GetMapping("/showSeller/{userId}")
    public String loginPage(@PathVariable int userId, Model model, HttpSession session) {
        model.addAttribute("user", ControllerUtility.checkUser(session));
        Optional<UserDto> seller = userService.findByIdUserDto(userId);
        if (seller.isEmpty()) {
            model.addAttribute("message", "Такой продавец не найден.");
            return "message/fail";
        }
        model.addAttribute("seller", seller.get());
        return "user/show";
    }

}
