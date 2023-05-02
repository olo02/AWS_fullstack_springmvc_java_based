package city.olooe.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor @Alias("board")
public class BoardVO {
    private Long bno;
    private String title;
    private String content;
    private String writer;
    private Date regdate;
    private Date updatedate;
    private int replycnt;
    private List<AttachVO> attaches = new ArrayList<>();
}
