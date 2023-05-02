package city.olooe.domain;

import lombok.Data;

import java.util.Date;

@Data
public class NoteVO {
    private Long noteno;
    private String sender;
    private String receiver;
    private Date sdate;
    private Date rdate;
    private String message;
}
