package shop.mtcoding.blog4.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.blog4.model.User;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class ReplyControllerTest {

    @Autowired
    private MockMvc mvc;

    private MockHttpSession mockSession;

    @BeforeEach
    private void makeMockPrincipal() {
        User mockPrincipal = new User();
        mockPrincipal.setId(1);
        mockPrincipal.setUsername("ssar");
        mockPrincipal.setPassword("1234");
        mockPrincipal.setEmail("ssar@gmail.com");
        mockSession = new MockHttpSession();
        mockSession.setAttribute("principal", mockPrincipal);
    }

    @Test
    public void sava_test() throws Exception {
        // given
        int id = 5;
        String commentVal = "댓글10";
        StringBuffer sb = new StringBuffer();
        sb.append("comment=");
        sb.append(commentVal);
        String requestBody = sb.toString();

        // when
        ResultActions resultActions = mvc.perform(post("/board/" + id + "/reply")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .session(mockSession));

        // verify
        resultActions.andExpect(status().is3xxRedirection());

    }

    @Test
    public void delete_test() throws Exception {
        // given
        int id = 1;

        // when
        ResultActions resultActions = mvc.perform(delete("/reply/" + id)
                .session(mockSession));

        // verify
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);
        resultActions.andExpect(status().isOk());
    }
}