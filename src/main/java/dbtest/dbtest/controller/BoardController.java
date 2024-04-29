package dbtest.dbtest.controller;

import dbtest.dbtest.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/board")
    @ResponseStatus(HttpStatus.OK)
    public void create(@RequestParam("files") List<MultipartFile> files){
        boardService.create(files);
    }

    @PostMapping("/board/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void change(@PathVariable Long id, @RequestParam("files") List<MultipartFile> files) {
        boardService.change(id, files);
    }
}