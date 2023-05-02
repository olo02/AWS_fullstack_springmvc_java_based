package city.olooe.mapper;

import city.olooe.domain.BoardVO;
import city.olooe.domain.Criteria;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BoardMapper {
    List<BoardVO> getList();
    List<BoardVO> getListWithPaging(Criteria cri);

    // 글 등록
    void insert(BoardVO vo);
    void insertSelectKey(BoardVO vo);

    // 조회
    BoardVO read(long bno);

    int delete(long bno);

    int update(BoardVO vo);

    int getTotalCnt(Criteria cri);

    void updateReplyCnt(@Param("bno") Long bno, @Param("amount") int amount);
}
