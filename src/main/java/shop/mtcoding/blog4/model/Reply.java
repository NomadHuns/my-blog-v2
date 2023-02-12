package shop.mtcoding.blog4.model;

import java.security.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Reply {
    private Integer id;
    private String comment;
    private Integer boardId;
    private Integer userId;
    private Timestamp createdAt;
}
