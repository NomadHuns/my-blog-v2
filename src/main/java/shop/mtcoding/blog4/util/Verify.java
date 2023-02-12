package shop.mtcoding.blog4.util;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;

import shop.mtcoding.blog4.ex.CustomApiException;
import shop.mtcoding.blog4.ex.CustomException;
import shop.mtcoding.blog4.model.User;

public class Verify {

    public static void validateStringApi(String stringData, String msg) {
        if (stringData.isEmpty() || stringData == null) {
            throw new CustomApiException(msg);
        }
    }

    public static void validateString(String stringData, String msg) {
        if (stringData.isEmpty() || stringData == null) {
            throw new CustomException(msg);
        }
    }

    public static void checkAuthorityApi(int BoardUserId, int principalId, String msg) {
        if (BoardUserId != principalId) {
            throw new CustomApiException(msg, HttpStatus.FORBIDDEN);
        }
    }

    public static void checkObjectExistApi(Object object, String msg) {
        if (object == null) {
            throw new CustomApiException(msg);
        }
    }

    public static void checkObjectExist(Object object, String msg) {
        if (object == null) {
            throw new CustomException(msg);
        }
    }

    public static User authenticatePrincipalApi(HttpSession session) {
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            throw new CustomApiException("해당 기능은 로그인이 필요합니다.", HttpStatus.UNAUTHORIZED);
        }
        return principal;
    }

    public static User authenticatePrincipal(HttpSession session) {
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            throw new CustomException("해당 기능은 로그인이 필요합니다.", HttpStatus.UNAUTHORIZED);
        }
        return principal;
    }
}
