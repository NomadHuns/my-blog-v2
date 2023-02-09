package shop.mtcoding.blog4.service;

import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
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
import shop.mtcoding.blog4.util.Verify;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    @Transactional
    public void save(SaveReqDto saveReqDto, int principalId) {
        String thumbnail = parseAttrVal(saveReqDto.getContent(), "img", "src", "/images/newjeans.jpg");
        try {
            boardRepository.insert(saveReqDto.getTitle(), saveReqDto.getContent(), thumbnail, principalId);
        } catch (Exception e) {
            throw new CustomException("글쓰기 실패", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public void delete(int id, int principalId) {
        Board boardPS = boardRepository.findById(id);
        Verify.checkObjectExistApi(boardPS, "해당 게시물을 찾을 수 없습니다.");
        Verify.checkAuthorityApi(boardPS.getUserId(), principalId, "권한이 없습니다");
        try {
            boardRepository.deleteById(id);
        } catch (Exception e) {
            throw new CustomApiException("글쓰기 실패", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public void update(int boardId, int principalId, UpdateReqDto updateReqDto) {
        String thumbnail = parseAttrVal(updateReqDto.getContent(), "img", "src", "/images/newjeans.jpg");

        Board boardPS = boardRepository.findById(boardId);
        Verify.checkObjectExistApi(boardPS, "해당 게시물을 찾을 수 없습니다.");
        Verify.checkAuthorityApi(boardPS.getUserId(), principalId, "권한이 없습니다");
        try {
            boardRepository.updateById(boardPS.getId(), updateReqDto.getTitle(), updateReqDto.getContent(), thumbnail,
                    principalId);
        } catch (Exception e) {
            throw new CustomApiException("글 수정 실패", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public List<BoardListRespDto> getBoardList() {
        List<BoardListRespDto> boardDtoList = boardRepository.findAllWithUser();
        return boardDtoList;
    }

    public BoardDetailRespDto getBoardDetail(int id) {
        BoardDetailRespDto boardDto = boardRepository.findByIdWithUser(id);
        Verify.checkObjectExist(boardDto, "존재하지 않는 게시물입니다.");
        return boardDto;
    }

    private String parseAttrVal(String html, String tag, String attr, String defaultVal) {
        String result = "";
        Document doc = Jsoup.parse(html);
        Elements elements = doc.select(tag);
        Element el = elements.first();
        if (el == null) {
            result = defaultVal;
        } else {
            result = el.attr(attr);
        }
        return result;
    }
}
