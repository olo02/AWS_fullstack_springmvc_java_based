package city.olooe.controller;

import city.olooe.domain.MemberVO;
import city.olooe.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Log4j
@RequestMapping("member")
@Controller
@AllArgsConstructor
public class MemberController {
    private MemberService memberService;

    @GetMapping("chat")
    public void chat() {}

    @GetMapping("login")
    public void login() {}

    @GetMapping("result")
    public void result() {}

    @PostMapping("login")
    public String login(MemberVO vo, HttpSession session, RedirectAttributes rttr) {
        log.info(vo);
        MemberVO memberVO = memberService.get(vo.getUserName());

        if (memberVO == null) {
            rttr.addFlashAttribute("msg", "로그인실패");
        } else {
            session.setAttribute("member", memberVO);
            log.info("로그인성공");
        }
        return "redirect:/member/login";
    }

    @RequestMapping(value = "logout")
    public String logout(HttpSession session) {
        log.info("로그아웃~");
        session.invalidate();
        return "redirect:/member/login";
    }

/*    @GetMapping("getList") @ResponseBody
    public List<MemberVO> getList() {
        return memberService.getList();
    }*/
}
