package city.olooe.service;

import city.olooe.domain.AttachFileDTO;
import city.olooe.domain.BoardVO;
import city.olooe.domain.Criteria;

import java.util.List;

public interface BoardService {
    void register(BoardVO vo);
    BoardVO get(Long bno);
    boolean modify(BoardVO vo);
    boolean remove(Long bno);
    List<BoardVO> getList(Criteria cri);
    String deleteFile(AttachFileDTO dto);
}
