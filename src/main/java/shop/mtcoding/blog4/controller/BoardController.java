package shop.mtcoding.blog4.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.blog4.dto.board.BoardReq.SaveReqDto;
import shop.mtcoding.blog4.dto.board.BoardResp.BoardDetailRespDto;
import shop.mtcoding.blog4.dto.board.BoardResp.BoardListRespDto;
import shop.mtcoding.blog4.ex.CustomException;
import shop.mtcoding.blog4.model.User;
import shop.mtcoding.blog4.service.BoardService;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final HttpSession session;

    @GetMapping({ "/", "/board", "/main" })
    public String main(Model model) {
        List<BoardListRespDto> boardList = boardService.getBoardList();
        model.addAttribute("boardList", boardList);
        return "board/list";
    }

    @GetMapping("/board/{id}")
    public String detail(@PathVariable("id") int id, Model model) {
        BoardDetailRespDto board = boardService.getBoardDetail(id);
        model.addAttribute("board", board);
        return "board/detail";
    }

    @GetMapping("/board/writeForm")
    public String writeForm() {
        return "board/writeForm";
    }

    @GetMapping("/board/{id}/updateForm")
    public String updateForm(@PathVariable("id") int id, Model model) {
        BoardDetailRespDto board = boardService.getBoardDetail(id);
        model.addAttribute("board", board);
        return "board/updateForm";
    }

    @PostMapping("/board")
    public String save(SaveReqDto saveReqDto) {
        User principal = authenticatePrincipal();
        validateString(saveReqDto.getTitle(), "제목을 입력하세요");
        validateString(saveReqDto.getContent(), "내용을 입력하세요");
        boardService.save(saveReqDto, principal.getId());
        return "redirect:/";
    }

    private void validateString(String stringData, String msg) {
        if (stringData.isEmpty() || stringData == null) {
            throw new CustomException(msg);
        }
    }

    private User authenticatePrincipal() {
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            throw new CustomException("해당 기능은 로그인이 필요합니다.", HttpStatus.UNAUTHORIZED);
        }
        return principal;
    }
}
