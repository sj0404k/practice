package dbtest.dbtest.service;

import dbtest.dbtest.domain.Board;
import dbtest.dbtest.domain.Image;
import dbtest.dbtest.dto.BoardDto;
import dbtest.dbtest.dto.BoardRequestDto;
import dbtest.dbtest.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {
    private final BoardRepository boardRepository;
    private final ImageManager imageManager;
    @Transactional
    public List<BoardDto> getListAll() throws Exception {
        List<Board> boards = boardRepository.findAllByOrderByCreatedAtDesc();
        List<BoardDto> getListDTO = new ArrayList<>();
        boards.forEach(s -> getListDTO.add(BoardDto.GetBoardDto(s)));
        return getListDTO;
    }
    public BoardDto getDetail(BoardRequestDto.DetailDto request) throws Exception {
        Board board = boardRepository.findById(request.getBoardID()).orElseThrow();
        try {
            BoardDto response = BoardDto.GetBoardDto(board);
            return response;
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
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
                    .contents(request.getContents())
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