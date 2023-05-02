package city.olooe.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import java.util.Date;

@Data @AllArgsConstructor @NoArgsConstructor @Alias("reply")
public class ReplyVO {
    private Long rno;
    private Long bno;

    private String reply;
    private String replyer;
    private Date replyDate;
    private Date updateDate;
}
