package shop.mtcoding.blog4.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.blog4.dto.board.BoardReq.SaveReqDto;
import shop.mtcoding.blog4.dto.board.BoardReq.UpdateReqDto;
import shop.mtcoding.blog4.dto.board.BoardResp.BoardDetailRespDto;
import shop.mtcoding.blog4.dto.board.BoardResp.BoardListRespDto;
import shop.mtcoding.blog4.ex.CustomApiException;
import shop.mtcoding.blog4.ex.CustomException;
import shop.mtcoding.blog4.model.Board;
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

    @Transactional
    public void delete(int id, int principalId) {
        Board boardPS = boardRepository.findById(id);
        checkObjectExistApi(boardPS, "해당 게시물을 찾을 수 없습니다.");
        checkAuthorityApi(boardPS.getUserId(), principalId, "권한이 없습니다");
        try {
            boardRepository.deleteById(id);
        } catch (Exception e) {
            throw new CustomApiException("글쓰기 실패", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public void update(int boardId, int principalId, UpdateReqDto updateReqDto) {
        Board boardPS = boardRepository.findById(boardId);
        checkObjectExistApi(boardPS, "해당 게시물을 찾을 수 없습니다.");
        checkAuthorityApi(boardPS.getUserId(), principalId, "권한이 없습니다");
        try {
            boardRepository.updateById(boardPS.getId(), updateReqDto.getTitle(), updateReqDto.getContent(),
                    principalId);
        } catch (Exception e) {
            throw new CustomApiException("글 수정 실패", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void checkAuthorityApi(int BoardUserId, int principalId, String msg) {
        if (BoardUserId != principalId) {
            throw new CustomApiException(msg, HttpStatus.FORBIDDEN);
        }
    }

    private void checkObjectExistApi(Object object, String msg) {
        if (object == null) {
            throw new CustomApiException(msg);
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
