package ru.job4j.cars.controller;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.cars.dto.PostDto;
import ru.job4j.cars.model.*;
import ru.job4j.cars.service.*;
import ru.job4j.cars.util.ControllerUtility;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

/**
 * Контроллер задач
 */
@ThreadSafe
@Controller
@RequestMapping("/posts")
@AllArgsConstructor
public class PostController {

    private final PostService postService;
    private final BodyService bodyService;
    private final CarModelService carModelService;
    private final TransmissionService transmissionService;
    private final EngineService engineService;
    private final OwnerService ownerService;

    /**
     * Производит подготовку для формирования вида, отображающего детальные сведения о задаче.
     * @param model модель вида
     * @param session сессия подключения
     * @param id идентификатор задачи
     * @return если задача не найдена переводит на вид с выводом ошибки, если задача найдена, переводит на вид с
     * детальным отображением сведений о задаче
     */
    @GetMapping("/formShow/{id}")
    public String formShowSession(Model model, HttpSession session, @PathVariable("id") int id) {
        model.addAttribute("user", ControllerUtility.checkUser(session));
        Optional<PostDto> post = postService.findById(id, (User) session.getAttribute("user"));
        if (post.isEmpty()) {
            model.addAttribute("message", "Такое объявление не найдено.");
            return "message/fail";
        }
        model.addAttribute("post", post.get());
        return "post/show";
    }

    /**
     * Приозводит подготовку к формированию вида, отображающего выполненные задачи или невыполненные задачи
     * @param model модель вида
     * @param session сессия подключения
     * @param isDone статус задачи
     * @return название шаблона для формирования соответствующего списка задач
     */
/*    @GetMapping("/done/{isDone}")
    public String doneTasks(HttpSession session, Model model, @PathVariable("isDone") boolean isDone) {
        model.addAttribute("user", ControllerUtility.checkUser(session));
        List<Task> tasks = postService.findByDone(isDone);
        model.addAttribute("tasks", tasks);
        return "index";
    }
*/
    /**
     * Обрабатывает запрос на отображение вида добавления новой задачи
     * @param model модель вида
     * @param session сессия подключения
     * @return название шаблона для формирования вида добавления новой задачи
     */
    @GetMapping("/new")
    public String newPost(HttpSession session, Model model) {
        modelAddAttributesAddNew(session, model);
        PostDto post = new PostDto().getEmpty();
        /*System.out.println("post in newPost() = " + post);*/
        model.addAttribute("post", post);
        return "post/addNew";
    }

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
     * Сохраняет объявление
     * @param session сессия подключения
     * @param model модель вида
     * @param post объявление
     * @param mPFiles файлы добавленные в объявление
     * @return сообщение с результатом сохранения
     */
    @RequestMapping(value = "/addPost", method = RequestMethod.POST, params = "action=save")
    public String addPost(HttpSession session, Model model, @ModelAttribute PostDto post,
                             @RequestParam("mPFiles") List<MultipartFile> mPFiles) {
        User user = ControllerUtility.checkUser(session);
        model.addAttribute("user", user);
        /*System.out.println("post at addPost() before form files = " + post);*/

        PostDto formedPost = ControllerUtility.formPostDto(post, session, mPFiles);

        /*System.out.println("post at addPost() = " + formedPost);*/

        if (post.getId() == 0) {
            if (!postService.add(formedPost)) {
                model.addAttribute("message", "Не удалось сохранить объявление.");
                session.removeAttribute("post");
                return "message/fail";
            }
        } else {
            if (!postService.update(formedPost)) {
                model.addAttribute("message", "Не удалось обновить объявление.");
                session.removeAttribute("post");
                return "message/fail";
            }
        }
        session.removeAttribute("post");
        model.addAttribute("message", "Объявление сохранено/обновлено.");
        return "message/success";
    }

    /**
     * Переводит на вид с созданием нового владельца
     * @param session сессия подключения
     * @param model модель вида
     * @param post объявление
     * @param mPFiles файлы добавленные в объявление
     * @return ссылка на вид создания нового владельца
     */
    @RequestMapping(value = "/addPost", method = RequestMethod.POST, params = "action=newOwner")
    public String newOwner(HttpSession session, Model model, @ModelAttribute PostDto post,
                          @RequestParam("mPFiles") List<MultipartFile> mPFiles) {
        model.addAttribute("user", ControllerUtility.checkUser(session));
        model.addAttribute("owner", new Owner());
        PostDto formedPost = ControllerUtility.formPostDto(post, session, mPFiles);
        session.setAttribute("post", formedPost);
        System.out.println("post at newOwner() = " + post);
        return "owner/addNew";
    }

    /**
     * Удаляет фотографию из объявления
     * @param session сессия подключения
     * @param model модель вида
     * @param index индекс фотографии в коллекции объявления
     * @return ссылку на шаблон с созданием/редактированием объявления
     */
    @GetMapping("/deletePhoto/{index}")
    public String deletePhoto(Model model, HttpSession session, @PathVariable("index") int index) {
        PostDto post = (PostDto) session.getAttribute("post");
        post.getFiles().remove(index);
        session.setAttribute("post", post);
        modelAddAttributesAddNew(session, model);
        model.addAttribute("post", post);
        System.out.println("post at deletePhoto() after delete = " + post);
        return "post/addNew";
    }

    /**
     * Готовит вид редактирования объявления
     * @param session сессия подключения
     * @param model модель вида
     * @param id объявления
     * @return ссылку на шаблон с созданием/редактированием объявления
     */
    @GetMapping("/edit/{id}")
    public String editPost(Model model, HttpSession session, @PathVariable("id") int id) {
        modelAddAttributesAddNew(session, model);
        Optional<PostDto> post = postService.findById(id, (User) session.getAttribute("user"));
        if (post.isEmpty()) {
            model.addAttribute("message", "Такое объявление не найдено.");
            return "message/fail";
        }
        model.addAttribute("post", post.get());
        session.setAttribute("post", post.get());
        return "post/addNew";
    }

}
