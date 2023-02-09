package shop.mtcoding.blog4.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.blog4.dto.user.UserReq.JoinReqDto;
import shop.mtcoding.blog4.ex.CustomException;
import shop.mtcoding.blog4.service.UserService;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/joinForm")
    public String joinForm() {
        return "user/joinForm";
    }

    @GetMapping("/loginForm")
    public String loginForm() {
        return "user/loginForm";
    }

    @GetMapping("/user/updateForm")
    public String updateForm() {
        return "user/updateForm";
    }

    @PostMapping("/join")
    public String join(JoinReqDto joinReqDto) {
        validateString(joinReqDto.getUsername(), "유저네임을 입력하세요");
        validateString(joinReqDto.getPassword(), "패스워드를 입력하세요");
        validateString(joinReqDto.getEmail(), "이메일을 입력하세요");
        userService.join(joinReqDto);
        return null;
    }

    private void validateString(String stringData, String msg) {
        if (stringData.isEmpty() || stringData == null) {
            throw new CustomException(msg);
        }
    }
}
