package shop.mtcoding.blog4.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void join_test() throws Exception {
        // given
        String usernameVal = "alss11";
        String passwordVal = "1234";
        String emailVal = "alss11@gmail.com";
        StringBuffer sb = new StringBuffer();
        sb.append("username=");
        sb.append(usernameVal);
        sb.append("&password=");
        sb.append(passwordVal);
        sb.append("&email=");
        sb.append(emailVal);
        String requestBody = sb.toString();

        // when
        ResultActions resultActions = mvc.perform(post("/join")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE));

        // verify
        resultActions.andExpect(status().is3xxRedirection());
    }

}
