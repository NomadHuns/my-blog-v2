package shop.mtcoding.blog4.dto.board;

import lombok.Getter;
import lombok.Setter;

public class BoardReq {

    @Getter
    @Setter
    public static class SaveReqDto {
        private String title;
        private String content;
    }

    @Getter
    @Setter
    public static class UpdateReqDto {
        private String title;
        private String content;
    }
}
