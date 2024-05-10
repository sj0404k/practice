package dbtest.dbtest.dto;

import dbtest.dbtest.domain.Board;
import dbtest.dbtest.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardDto {
    private Long id;
    private String title;
    private String contents;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer check12;
    private Integer majorId12;
    private List<Comment> comments;

    public static BoardDto GetBoardDto(Board board){
        return new BoardDto(
                board.getBoardId(),
                board.getTitle(),
                board.getContents(),
                board.getCreatedAt(),
                board.getUpdatedAt(),
                board.getCheck12(),
                board.getMajorId12(),
                board.getComments()
        );
    }
//    public BoardDto(Board entity) {
//
//        this.id = entity.getId();
//        this.title = entity.getTitle();
//        this.contents = entity.getContents();
//
//    }

}
