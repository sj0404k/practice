package dbtest.dbtest.service;

import dbtest.dbtest.domain.Board;
import dbtest.dbtest.domain.Image;
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
    public void create(List<MultipartFile> files) {
        String title = "게시글 " + files.size();
        String writer = "작성자 " + files.size();
        String content = "내용 " + files.size();

        Board board = Board.builder()
                .title(title)
                .content(content)
                .writer(writer)
                .build();

        if(!files.isEmpty()) {
            try {
                List<Image> fileList = imageManager.saveImages(files, board);
                board.setImageList(fileList);
            } catch(IOException e) {
                throw new RuntimeException("게시글 생성 오류 발생");
            }
        }

        boardRepository.save(board);
    }

    @Transactional
    public void change(Long id, List<MultipartFile> files){
        Board board = boardRepository.findById(id).get();

        try {
            for(Image image : imageManager.saveImages(files, board)) {
                board.getImageList().add(image);
            }
        } catch(IOException e) {
            throw new RuntimeException("게시글 수정 오류 발생");
        }
    }
}