package city.olooe.controller;

import city.olooe.domain.ReplyVO;
import city.olooe.service.ReplyService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("replies")
@RestController
@Log4j
@AllArgsConstructor
public class ReplyController {
    private ReplyService replyService;

    @GetMapping({"list/{bno}", "list/{bno}/{rno}"})
    public List<ReplyVO> getList(@PathVariable Long bno, @PathVariable(required = false) Optional<Long> rno) {
        log.info(bno);
        log.info(rno.isPresent() ? rno.get() : 0L);
        log.info(rno.orElse(0L));
        return replyService.getList(bno, rno.orElse(0L));
    }

    @PostMapping("new")
    @PreAuthorize("isAuthenticated()")
    public Long create(@RequestBody ReplyVO vo) {
        log.info(vo);
        replyService.register(vo);
        return vo.getRno();
    }

    @GetMapping("{rno}")
    public ReplyVO get(@PathVariable Long rno) {
        log.info(rno);
        return replyService.get(rno);
    }

    @DeleteMapping("{rno}")
    @PreAuthorize("isAuthenticated() and principal.username eq #vo.replyer")
    public int remove(@PathVariable Long rno, @RequestBody ReplyVO vo) {
        log.info(rno);
        return replyService.remove(rno);
    }

    @PutMapping("{rno}")
    @PreAuthorize("isAuthenticated()")
    public int modify(@PathVariable Long rno, @RequestBody ReplyVO vo) {
        log.info(rno);
        log.info(vo);
        return replyService.update(vo);
    }
}
