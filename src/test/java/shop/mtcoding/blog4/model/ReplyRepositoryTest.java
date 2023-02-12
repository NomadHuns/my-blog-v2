package shop.mtcoding.blog4.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;

import shop.mtcoding.blog4.dto.reply.ReplyResp.ReplyDetailRespDto;

@MybatisTest
public class ReplyRepositoryTest {

    @Autowired
    ReplyRepository replyRepository;

    @Test
    public void findAllWithUser_test() throws Exception {
        // given
        ObjectMapper om = new ObjectMapper();
        int id = 1;

        // when
        List<ReplyDetailRespDto> ReplyDtoList = replyRepository.findAllWithUser(id);
        System.out.println("디버그 : size() : " + ReplyDtoList.size());
        String responseBody = om.writeValueAsString(ReplyDtoList);
        System.out.println("디버그 : " + responseBody);
        // verify
        assertThat(ReplyDtoList.get(0).getId()).isEqualTo(1);
        assertThat(ReplyDtoList.get(0).getUsername()).isEqualTo("ssar");
        assertThat(ReplyDtoList.get(0).getComment()).isEqualTo("댓글1");
    }
}
