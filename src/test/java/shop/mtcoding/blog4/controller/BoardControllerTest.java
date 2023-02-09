package shop.mtcoding.blog4.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

import com.fasterxml.jackson.databind.ObjectMapper;

import shop.mtcoding.blog4.dto.board.BoardReq.UpdateReqDto;
import shop.mtcoding.blog4.model.User;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class BoardControllerTest {

    @Autowired
    private MockMvc mvc;

    private MockHttpSession mockSession;
    private ObjectMapper om;

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
        String titleVal = "제목입니다.";
        String contentVal = "내용입니다.";
        StringBuffer sb = new StringBuffer();
        sb.append("title=");
        sb.append(titleVal);
        sb.append("&content=");
        sb.append(contentVal);
        String requestBody = sb.toString();

        // when
        ResultActions resultActions = mvc.perform(post("/board")
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
        ResultActions resultActions = mvc.perform(delete("/board/" + id)
                .session(mockSession));

        // verify
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);
        resultActions.andExpect(status().isOk());
    }

    @Test
    public void update_test() throws Exception {
        // given
        int id = 1;
        String titleVal = "제목입니다.";
        String contentVal = "내용입니다.";
        UpdateReqDto updateReqDto = new UpdateReqDto();
        updateReqDto.setTitle(titleVal);
        updateReqDto.setContent(contentVal);
        om = new ObjectMapper();
        String requestBody = om.writeValueAsString(updateReqDto);

        // when
        ResultActions resultActions = mvc.perform(put("/board/" + id)
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .session(mockSession));

        // verify
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);
        resultActions.andExpect(status().isOk());
    }
}