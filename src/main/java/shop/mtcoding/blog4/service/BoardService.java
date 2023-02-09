package shop.mtcoding.blog4.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.blog4.dto.board.BoardReq.SaveReqDto;
import shop.mtcoding.blog4.dto.board.BoardResp.BoardDetailRespDto;
import shop.mtcoding.blog4.dto.board.BoardResp.BoardListRespDto;
import shop.mtcoding.blog4.ex.CustomException;
import shop.mtcoding.blog4.model.BoardRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    @Transactional
    public void save(SaveReqDto saveReqDto, int principalId) {
        try {
            boardRepository.insert(saveReqDto.getTitle(), saveReqDto.getContent(), principalId);
        } catch (Exception e) {
            throw new CustomException("글쓰기 실패", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public List<BoardListRespDto> getBoardList() {
        List<BoardListRespDto> boardDtoList = boardRepository.findAllWithUser();
        return boardDtoList;
    }

    public BoardDetailRespDto getBoardDetail(int id) {
        BoardDetailRespDto boardDto = boardRepository.findByIdWithUser(id);
        checkObjectExist(boardDto, "존재하지 않는 게시물입니다.");
        return boardDto;
    }

    private void checkObjectExist(Object object, String msg) {
        if (object == null) {
            throw new CustomException(msg);
        }
    }
}
