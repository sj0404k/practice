package dbtest.dbtest.service;

import dbtest.dbtest.domain.Board;
import dbtest.dbtest.domain.Image;
import dbtest.dbtest.dto.BoardRequestDto;
import dbtest.dbtest.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final ImageManager imageManager;

    @Transactional
    public Boolean save(Board board) throws Exception {
        try {
            boardRepository.save(board);
            return true;

        }catch (Exception e){
            throw new Exception(e);
        }

    }
    public Boolean create(BoardRequestDto.CreateDto request) throws Exception{
        try {
            Board board = Board.builder()
                    .title(request.getTitle())
                    .content(request.getContents())
                    .build();

            boardRepository.save(board);
            return true;
        }catch (Exception e){
            throw new Exception(e);
        }

    }

//    @Transactional
//    public void change(Long id, List<MultipartFile> files){
//        Board board = boardRepository.findById(id).get();
//
//        try {
//            for(Image image : imageManager.saveImages(files, board)) {
//                board.getImageList().add(image);
//            }
//        } catch(IOException e) {
//            throw new RuntimeException("게시글 수정 오류 발생");
//        }
//    }
}