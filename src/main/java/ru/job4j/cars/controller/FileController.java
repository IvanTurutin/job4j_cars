package ru.job4j.cars.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.job4j.cars.dto.PostDto;
import ru.job4j.cars.service.FileService;

import javax.servlet.http.HttpSession;

/**
 * Контроллер файлов
 */
@RestController
@RequestMapping("/files")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    /**
     * Выдает изображение из базы данных по запрашиваемому id
     * @param id идентификатор изображения
     * @return ответ в виде ResponseEntity, если файл найден - данные для изображения, не найден - ошибку о том что
     * файл не найден
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        var contentOptional = fileService.findById(id);
        if (contentOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(contentOptional.get().getContent());
    }

    /**
     * Выдает изображение из объекта объявления, находящегося в сессии подключения, по id изображения
     * @param id идентификатор изображения
     * @param session сессия подключиния
     * @return ответ в виде ResponseEntity, если файл найден - данные для изображения, не найден - ошибку о том что
     * файл не найден
     */
    @GetMapping("/fromSession/{id}")
    public ResponseEntity<?> getFromSession(@PathVariable int id, HttpSession session) {
        PostDto post = (PostDto) session.getAttribute("post");
        if (post.getFiles().size() < id) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(post.getFiles().get(id).getContent());
    }
}