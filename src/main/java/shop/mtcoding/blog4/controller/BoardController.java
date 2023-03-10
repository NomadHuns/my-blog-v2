package shop.mtcoding.blog4.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.blog4.dto.ResponseDto;
import shop.mtcoding.blog4.dto.board.BoardReq.SaveReqDto;
import shop.mtcoding.blog4.dto.board.BoardReq.UpdateReqDto;
import shop.mtcoding.blog4.dto.board.BoardResp.BoardDetailRespDto;
import shop.mtcoding.blog4.dto.board.BoardResp.BoardListRespDto;
import shop.mtcoding.blog4.ex.CustomApiException;
import shop.mtcoding.blog4.ex.CustomException;
import shop.mtcoding.blog4.model.User;
import shop.mtcoding.blog4.service.BoardService;
import shop.mtcoding.blog4.util.Verify;

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
        Verify.validateString(saveReqDto.getTitle(), "????????? ???????????????");
        Verify.validateString(saveReqDto.getContent(), "????????? ???????????????");
        boardService.save(saveReqDto, principal.getId());
        return "redirect:/";
    }

    @DeleteMapping("/board/{id}")
    public @ResponseBody ResponseDto<?> delete(@PathVariable("id") int id) {
        User principal = authenticatePrincipalApi();
        boardService.delete(id, principal.getId());
        return new ResponseDto<>(1, "????????? ?????? ??????", null);
    }

    @PutMapping("/board/{id}")
    public @ResponseBody ResponseDto<?> update(@PathVariable("id") int id, @RequestBody UpdateReqDto updateReqDto) {
        User principal = authenticatePrincipal();
        Verify.validateStringApi(updateReqDto.getTitle(), "????????? ???????????????");
        Verify.validateStringApi(updateReqDto.getContent(), "????????? ???????????????");
        boardService.update(id, principal.getId(), updateReqDto);
        return new ResponseDto<>(1, "????????? ?????? ??????", null);
    }

    private User authenticatePrincipalApi() {
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            throw new CustomApiException("?????? ????????? ???????????? ???????????????.", HttpStatus.UNAUTHORIZED);
        }
        return principal;
    }

    private User authenticatePrincipal() {
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            throw new CustomException("?????? ????????? ???????????? ???????????????.", HttpStatus.UNAUTHORIZED);
        }
        return principal;
    }
}
