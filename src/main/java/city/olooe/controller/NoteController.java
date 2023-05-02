package city.olooe.controller;

import city.olooe.domain.NoteVO;
import city.olooe.service.NoteService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j
@RequestMapping("note")
@RestController
@AllArgsConstructor
public class NoteController {
    private NoteService noteService;

    @PostMapping("new")
    public int send(@RequestBody NoteVO vo) {
        log.info(vo);
        return noteService.send(vo);
    }

    @GetMapping("{noteno}")
    public NoteVO getNote(@PathVariable Long noteno) {
        log.info(noteno);
        return noteService.get(noteno);
    }

    @PutMapping("{noteno}")
    public int recieve(@PathVariable Long noteno) {
        log.info(noteno);
        return noteService.receive(noteno);
    }

    @DeleteMapping("{noteno}")
    public int remove(@PathVariable Long noteno) {
        log.info(noteno);
        return noteService.remove(noteno);
    }

    @GetMapping("s/{id}")
    public List<NoteVO> getSendList(@PathVariable String id) { // 세션 여부도 체크해야함 (세션의 아이디와 일치했을 때 보여주기)
        log.info(id);
        return noteService.getSendList(id);
    }

    @GetMapping("r/{id}")
    public List<NoteVO> getReceiveList(@PathVariable String id) { // 세션 여부도 체크해야함 (세션의 아이디와 일치했을 때 보여주기)
        log.info(id);
        return noteService.getRecieveList(id);
    }

    @GetMapping("r/c/{id}")
    public List<NoteVO> getReceiveUncheckedList(@PathVariable String id) { // 세션 여부도 체크해야함 (세션의 아이디와 일치했을 때 보여주기)
        log.info(id);
        return noteService.getReceiveUncheckedList(id);
    }

    @GetMapping("messages/{sender}/{receiver}")
    public List<NoteVO> getMessages(@PathVariable String sender, @PathVariable String receiver) {
        return noteService.getMessages(sender, receiver);
    }
}
