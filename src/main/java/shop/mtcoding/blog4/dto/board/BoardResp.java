package shop.mtcoding.blog4.dto.board;

import lombok.Getter;
import lombok.Setter;

public class BoardResp {

    @Getter
    @Setter
    public static class BoardListRespDto {
        private Integer id;
        private String title;
        private String thumbnail;
        private String username;
    }

    @Getter
    @Setter
    public static class BoardDetailRespDto {
        private Integer id;
        private String title;
        private String content;
        private String username;
    }
}
