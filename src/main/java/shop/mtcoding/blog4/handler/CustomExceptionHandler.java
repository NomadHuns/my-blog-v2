package shop.mtcoding.blog4.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import shop.mtcoding.blog4.dto.ResponseDto;
import shop.mtcoding.blog4.ex.CustomApiException;
import shop.mtcoding.blog4.ex.CustomException;
import shop.mtcoding.blog4.util.Script;

@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> customException(CustomException e) {
        if (e.getStatus() == HttpStatus.UNAUTHORIZED) {
            return new ResponseEntity<>(Script.href(e.getMessage(), e.getLocation()), HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(Script.back(e.getMessage()), e.getStatus());
    }

    @ExceptionHandler(CustomApiException.class)
    public ResponseEntity<?> customApiException(CustomApiException e) {
        return new ResponseEntity<>(new ResponseDto<>(-1, e.getMessage(), null), e.getStatus());
    }
}
