package shop.mtcoding.blog4.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.blog4.dto.reply.ReplyResp.ReplyDetailRespDto;
import shop.mtcoding.blog4.ex.CustomApiException;
import shop.mtcoding.blog4.ex.CustomException;
import shop.mtcoding.blog4.model.Board;
import shop.mtcoding.blog4.model.BoardRepository;
import shop.mtcoding.blog4.model.Reply;
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
        try {
            replyRepository.insert(comment, boardId, userId);
        } catch (Exception e) {
            throw new CustomException("댓글 작성 실패", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void delete(Integer replyId, Integer principalId) {
        Reply reply = replyRepository.findById(replyId);
        Verify.checkObjectExistApi(reply, "존재하지 않는 댓글입니다");
        if (principalId != reply.getUserId()) {
            throw new CustomApiException("본인 댓글만 지울 수 있습니다", HttpStatus.FORBIDDEN);
        }
        try {
            replyRepository.deleteById(replyId);
        } catch (Exception e) {
            throw new CustomApiException("댓글 삭제 실패", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
