package shop.mtcoding.blog4.dto.board;

import lombok.Getter;
import lombok.Setter;

public class BoardResp {
    
    @Getter
    @Setter
    public static class BoardListRespDto{
        private String title;
        private String content;
        private String username;
    }
}
