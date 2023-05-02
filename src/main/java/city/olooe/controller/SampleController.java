package city.olooe.controller;

import city.olooe.domain.SampleVO;
import lombok.extern.log4j.Log4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequestMapping("sample")
@Log4j
public class SampleController {
    @GetMapping(value = "getText", produces = "text/plain; charset=utf-8")
    public String getText() {
        return "안녕하세요";
    }

    @GetMapping("getSample")
    public SampleVO getSample() {
        return new SampleVO(112, "스타", "로드");
    }

    @GetMapping("getList")
    public List<SampleVO> getList() {
        return IntStream.rangeClosed(1, 10).mapToObj(i -> new SampleVO(i, "first " + i, "last " + i)).collect(Collectors.toList());
    }

    @GetMapping("getMap")
    public Map<String, SampleVO> getMap() {
        Map<String, SampleVO> map = new HashMap<>();
        map.put("First", new SampleVO(111, "그루트", "주니어"));
        return map;
    }

    @GetMapping("check")
    public ResponseEntity<SampleVO> check(Double height, Double weight) {
        SampleVO vo = new SampleVO(0, "" + height, "" + weight);
        ResponseEntity<SampleVO> result = null;

        if (height < 150) {
            result = ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(vo);
        } else {
            result = ResponseEntity.status(HttpStatus.OK).body(vo);
        }
        return result;
    }

    @GetMapping("product/{cat}/{pid}")
    public String[] getPath(@PathVariable String cat, @PathVariable String pid) {
        return new String[] {"category: " + cat, "productid: " + pid};
    }

    @GetMapping("product/{cat2}/{pid2}")
    public String[] getPath2(@PathVariable String cat2, @PathVariable String pid2) {
        return new String[] {"category: " + cat2, "productid: " + pid2};
    }

    @GetMapping("sample")
    public SampleVO convert(@RequestBody SampleVO vo) {
        log.info(vo);
        System.out.println(vo);
        return vo;
    }
}
