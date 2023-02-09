package shop.mtcoding.blog4.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.blog4.dto.user.UserReq.JoinReqDto;
import shop.mtcoding.blog4.dto.user.UserReq.LoginReqDto;
import shop.mtcoding.blog4.ex.CustomException;
import shop.mtcoding.blog4.model.User;
import shop.mtcoding.blog4.model.UserRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public void join(JoinReqDto joinReqDto) {
        checkAlreadyHasSameUsername(joinReqDto.getUsername());
        try {
            userRepository.insert(joinReqDto.getUsername(), joinReqDto.getPassword(), joinReqDto.getEmail());
        } catch (Exception e) {
            throw new CustomException("회원가입 실패", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void checkAlreadyHasSameUsername(String username) {
        User principalPS = userRepository.findByUsername(username);
        if (principalPS != null) {
            throw new CustomException("존재하는 유저네임입니다.");
        }
    }

    public User login(LoginReqDto loginReqDto) {
        return null;
    }
}
