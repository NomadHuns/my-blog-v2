package shop.mtcoding.blog4.dto.board;

import lombok.Getter;
import lombok.Setter;

public class boardReq {

    @Getter
    @Setter
    public static class SaveReqDto {
        private String title;
        private String content;
    }
}
