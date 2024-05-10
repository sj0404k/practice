package dbtest.dbtest.controller;

import dbtest.dbtest.domain.Board;
import dbtest.dbtest.dto.BoardRequestDto;
import dbtest.dbtest.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/boards")
public class BoardController {

    private final BoardService boardService;
    @GetMapping
    public ResponseEntity<List> getList() throws Exception{
        return  new ResponseEntity<>(boardService.getListAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Board board) throws Exception {
        return new ResponseEntity<Boolean>(boardService.save(board), HttpStatus.OK);
    }
//    @PostMapping
//    public ResponseEntity<Boolean> create(BoardRequestDto.CreateDto request) throws Exception{
//        return new ResponseEntity<Boolean>(boardService.create(request),HttpStatus.OK);
//    }


//    @PostMapping
//    @ResponseStatus(HttpStatus.OK)
//    public void create(@RequestParam("files") List<MultipartFile> files){
//        boardService.create(files);
//    }
//
//    @PostMapping
//    @ResponseStatus(HttpStatus.OK)
//    public void change(@PathVariable Long id, @RequestParam("files") List<MultipartFile> files) {
//        boardService.change(id, files);
//    }


}