package shop.mtcoding.blog4.ex;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomApiException extends RuntimeException {
    private HttpStatus status;

    public CustomApiException(String msg, HttpStatus status) {
        super(msg);
        this.status = status;
    }

    public CustomApiException(String msg) {
        this(msg, HttpStatus.BAD_REQUEST);
    }
}