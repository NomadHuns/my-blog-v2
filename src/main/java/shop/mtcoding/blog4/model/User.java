package shop.mtcoding.blog4.model;

import java.security.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private Integer id;
    private String username;
    private String password;
    private String email;
    private Timestamp createdAt;
}
