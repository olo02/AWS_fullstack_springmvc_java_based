package city.olooe.service;

import city.olooe.domain.NoteVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NoteService {
    int send(NoteVO vo);
    NoteVO get(Long noteno);
    int receive(Long noteno);
    int remove(Long noteno);
    List<NoteVO> getSendList(String id);
    List<NoteVO> getRecieveList(String id);
    List<NoteVO> getReceiveUncheckedList(String id);
    List<NoteVO> getMessages(@Param("sender") String sender, @Param("receiver") String receiver);
}
