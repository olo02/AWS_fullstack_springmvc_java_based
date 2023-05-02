package city.olooe.controller;

import city.olooe.domain.*;
import city.olooe.service.BoardService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller @Log4j @RequestMapping("/board/") @AllArgsConstructor
public class BoardController {
    private BoardService boardService;

    @GetMapping("list")
    public void list(Model model, Criteria cri) {
        log.info("BoardController :: list()");
        model.addAttribute("list", boardService.getList(cri));
        model.addAttribute("page", new PageDto(123, cri));
    }

    @GetMapping({"get", "modify"})
    public void get(Model model, Long bno, @ModelAttribute("cri") Criteria cri) {
        System.out.println("BoardController :: get()");
        model.addAttribute("board", boardService.get(bno));
    }

    @GetMapping("register")
    public void register(){}

    @PostMapping("register")
    @PreAuthorize("isAuthenticated()")
    public String register(BoardVO vo, RedirectAttributes rttr, Criteria cri) {
        System.out.println(vo);
        log.info("registr");
        log.info(vo);
        boardService.register(vo);
        rttr.addFlashAttribute("result", vo.getBno());
        rttr.addAttribute("pageNum", 1);
        rttr.addAttribute("amount", cri.getAmount());
        return "redirect:/board/list";
    }


    @PostMapping("modify")
    @PreAuthorize("isAuthenticated()")
    public String modify(BoardVO vo, RedirectAttributes rttr, Criteria cri) {
        //원본 리스트
        List<AttachVO> originList = boardService.get(vo.getBno()).getAttaches();
        // 수정될 리스트
        List<AttachVO> list = vo.getAttaches();

        log.info("modify");
        log.info(vo);
        log.info(cri);
        if (boardService.modify(vo)) {
            rttr.addFlashAttribute("result", "success");
            originList.stream().filter(v -> {
                boolean flag = false;
                for(AttachVO vo2 : list) {
                    flag = vo2.getUuid().equals(v.getUuid());
                }
                return flag;
            }).forEach(boardService::deleteFile);
        }
//		rttr.addAttribute("pageNum", cri.getPageNum());
//		rttr.addAttribute("amount", cri.getAmount());
//	}
        return "redirect:/board/list" + cri.getFullQueryString();
    }

//    @PostMapping("modify")
//    public String modify(BoardVO vo, RedirectAttributes rttr, Criteria cri) {
//        // 원본 리스트
//        List<AttachVO> originList = boardService.get(vo.getBno()).getAttaches();
//
//        // 수정될 리스트
//        List<AttachVO> list = vo.getAttaches();
//
//        System.out.println("BoardController :: modify()");
//        System.out.println(vo);
//        if (boardService.modify(vo)) {
//            rttr.addFlashAttribute("result", "success");
//            originList.stream().filter(attachVO -> {
//                boolean flag = false;
//
//                for (AttachVO vo2 : list) {
//                    flag = vo2.getUuid().equals(attachVO.getUuid());
//                }
//                return !flag;
//            }).forEach(boardService::deleteFile);
//        }
//        return "redirect:/board/list" + cri.getFullQueryString();
//    }

    @PostMapping("remove")
    @PreAuthorize("isAuthenticated()")
    public String remove(Long bno, RedirectAttributes rttr, Criteria cri) {
        System.out.println("BoardController :: remove()");
        System.out.println(bno);
        List<AttachVO> list = boardService.get(bno).getAttaches();
        if (boardService.remove(bno)) {
            list.forEach(boardService::deleteFile);
//            for (AttachVO vo : list) {
//                boardService.deleteFile(vo);
//            }
            rttr.addFlashAttribute("result", "success");
        }
        return "redirect:/board/list" + cri.getFullQueryString();
    }

    @GetMapping("{bno}") // rest uri 방식
    public String getByPath(Model model, @PathVariable Long bno) {
        System.out.println("get() or modify()");
        model.addAttribute("board", boardService.get(bno));
        return "board/get";
    }

    @GetMapping("getSample") @ResponseBody
    public SampleVO getSample() {
        return new SampleVO(112, "스타", "로드");
    }
}
