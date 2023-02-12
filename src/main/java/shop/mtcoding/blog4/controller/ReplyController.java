package shop.mtcoding.blog4.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.blog4.model.User;
import shop.mtcoding.blog4.service.ReplyService;
import shop.mtcoding.blog4.util.Verify;

@Controller
@RequiredArgsConstructor
public class ReplyController {
    private final ReplyService replyService;
    private final HttpSession session;

    @PostMapping("/board/{id}/reply")
    public String save(@PathVariable("id") Integer id, String comment) {
        User principal = Verify.authenticatePrincipal(session);
        replyService.save(comment, id, principal.getId());
        return "redirect:/board/" + id;
    }
}
