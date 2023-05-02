package city.olooe.service;

import city.olooe.domain.NoteVO;
import city.olooe.mapper.NoteMapper;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j
@Service
@AllArgsConstructor
@ToString
public class NoteServiceImpl implements NoteService {
    @Setter
    @Autowired
    private NoteMapper noteMapper;

    @Override
    public int send(NoteVO vo) {
        return noteMapper.insert(vo);
    }

    @Override
    public NoteVO get(Long noteno) {
        return noteMapper.selectOne(noteno);
    }

    @Override
    public int receive(Long noteno) {
        return noteMapper.update(noteno);
    }

    @Override
    public int remove(Long noteno) {
        return noteMapper.delete(noteno);
    }

    @Override
    public List<NoteVO> getSendList(String id) {
        return noteMapper.sendList(id);
    }

    @Override
    public List<NoteVO> getRecieveList(String id) {
        return noteMapper.receiveList(id);
    }

    @Override
    public List<NoteVO> getReceiveUncheckedList(String id) {
        return noteMapper.receiveUncheckedList(id);
    }

    @Override
    public List<NoteVO> getMessages(String sender, String receiver) {
        return noteMapper.getMessages(sender, receiver);
    }
}
