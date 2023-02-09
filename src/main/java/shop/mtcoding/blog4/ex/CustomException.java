package shop.mtcoding.blog4.ex;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomException extends RuntimeException {
    private HttpStatus status;

    public CustomException(String msg, HttpStatus status) {
        super(msg);
        this.status = status;
    }

    public CustomException(String msg) {
        this(msg, HttpStatus.BAD_REQUEST);
    }
}
