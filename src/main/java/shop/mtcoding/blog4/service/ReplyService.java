package shop.mtcoding.blog4.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.blog4.dto.reply.ReplyResp.ReplyDetailRespDto;
import shop.mtcoding.blog4.model.Board;
import shop.mtcoding.blog4.model.BoardRepository;
import shop.mtcoding.blog4.model.ReplyRepository;
import shop.mtcoding.blog4.util.Verify;

@Service
@RequiredArgsConstructor
public class ReplyService {
    private final ReplyRepository replyRepository;
    private final BoardRepository boardRepository;

    public List<ReplyDetailRespDto> getReplyList(Integer boardId) {
        List<ReplyDetailRespDto> dtoList = replyRepository.findAllWithUser(boardId);
        return dtoList;
    }

    public void save(String comment, Integer boardId, Integer userId) {
        Board boardPS = boardRepository.findById(boardId);
        Verify.checkObjectExist(boardPS, "존재하지 않는 게시물입니다");
        replyRepository.insert(comment, boardId, userId);
    }

}
