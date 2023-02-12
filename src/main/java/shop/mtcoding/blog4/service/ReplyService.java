package shop.mtcoding.blog4.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.blog4.dto.reply.ReplyResp.ReplyDetailRespDto;
import shop.mtcoding.blog4.model.ReplyRepository;

@Service
@RequiredArgsConstructor
public class ReplyService {
    private final ReplyRepository replyRepository;

    public List<ReplyDetailRespDto> getReplyList(Integer boardId) {
        List<ReplyDetailRespDto> dtoList = replyRepository.findAllWithUser(boardId);
        return dtoList;
    }

}
