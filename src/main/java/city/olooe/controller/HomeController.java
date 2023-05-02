package city.olooe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String index() {
//        return "index";
//        return "redirect:/member/login";
        return "redirect:/board/list";
    }
}