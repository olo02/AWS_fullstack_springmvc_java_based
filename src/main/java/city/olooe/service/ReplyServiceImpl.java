package city.olooe.service;

import city.olooe.domain.ReplyVO;
import city.olooe.mapper.BoardMapper;
import city.olooe.mapper.ReplyMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service @AllArgsConstructor
public class ReplyServiceImpl implements ReplyService {
    private BoardMapper boardMapper;
    private ReplyMapper mapper;

    @Override @Transactional
    public int register(ReplyVO vo) {
        boardMapper.updateReplyCnt(vo.getBno(), 1);
        return mapper.insert(vo);
    }

    @Override
    public ReplyVO get(Long rno) {
        return mapper.read(rno);
    }

    @Override @Transactional
    public int remove(Long rno) {
        boardMapper.updateReplyCnt(get(rno).getBno(), -1);
        return mapper.delete(rno);
    }

    @Override
    public int update(ReplyVO vo) {
        return mapper.update(vo);
    }

    @Override
    public List<ReplyVO> getList(Long bno, Long rno) {
        return mapper.getList(bno, rno);
    }
}
