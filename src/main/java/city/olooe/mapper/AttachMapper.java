package city.olooe.mapper;

import city.olooe.domain.AttachFileDTO;
import city.olooe.domain.AttachVO;

import java.util.List;

public interface AttachMapper {
    void insert(AttachVO vo);
    void delete(String uuid);
    List<AttachFileDTO> findBy(Long bno);
    void deleteAll(Long bno);
    List<AttachVO> getOldFiles();
}
