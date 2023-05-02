package city.olooe.service;

import city.olooe.domain.ReplyVO;

import java.util.List;

public interface ReplyService {
    int register(ReplyVO vo);

    ReplyVO get(Long rno);

    int remove(Long rno);

    int update(ReplyVO vo);

    List<ReplyVO> getList(Long bno, Long rno);
}
