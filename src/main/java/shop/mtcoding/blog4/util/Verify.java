package shop.mtcoding.blog4.util;

import org.springframework.http.HttpStatus;

import shop.mtcoding.blog4.ex.CustomApiException;
import shop.mtcoding.blog4.ex.CustomException;

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

}
