package dbtest.dbtest.dto;

import dbtest.dbtest.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private Long id;
    private String contents;
    private LocalDateTime createAt;
    private LocalDateTime updatedAt;
    //    private User userId;


    public static CommentDto getCommentDto(Comment comment) {
        return new CommentDto(
                comment.getCommentId(),
                comment.getContents(),
                comment.getCreatedAt(),
                comment.getUpdatedAt()
        );
    }
}
