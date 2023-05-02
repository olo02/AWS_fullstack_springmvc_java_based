package city.olooe.service;

import city.olooe.controller.UploadController;
import city.olooe.domain.AttachFileDTO;
import city.olooe.domain.AttachVO;
import city.olooe.domain.BoardVO;
import city.olooe.domain.Criteria;
import city.olooe.mapper.AttachMapper;
import city.olooe.mapper.BoardMapper;
import city.olooe.mapper.ReplyMapper;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.List;

@Log4j
@Service @AllArgsConstructor @ToString
public class BoardServiceImpl implements BoardService {
    @Setter
    @Autowired
    private BoardMapper mapper;
    private ReplyMapper replyMapper;
    private AttachMapper attachMapper;

    @Override
    @Transactional
    public void register(BoardVO vo) {
        mapper.insertSelectKey(vo);
        Long bno = vo.getBno();
        List<AttachVO> attaches = vo.getAttaches();

        int idx = 0;
        for (AttachVO attach : attaches) {
            attach.setBno(bno);
            attach.setOdr(idx++);
            attachMapper.insert(attach);
        }
    }

    @Override
    public BoardVO get(Long bno) {
        return mapper.read(bno);
    }

    @Override
    @Transactional
    public boolean modify(BoardVO vo) {
        Long bno = vo.getBno();
        List<AttachVO> attaches = vo.getAttaches();

        for (AttachVO attach : attaches) {
            attach.setBno(bno);
            attachMapper.insert(attach);
        }

        return mapper.update(vo) > 0;
    }

    @Override
    public boolean remove(Long bno) {
        replyMapper.deleteByBno(bno);
        attachMapper.deleteAll(bno);
        return mapper.delete(bno) > 0;
    }

    @Override
    public List<BoardVO> getList(Criteria cri) {
        return mapper.getListWithPaging(cri);
    }

    @Override
    public String deleteFile(AttachFileDTO dto) {
        String s = UploadController.getPATH() + "/" + dto.getPath() + "/" + dto.getUuid() + "_" + dto.getName();
        File file = new File(s);
        file.delete();

        if (dto.isImage()) {
            s = UploadController.getPATH() + "/" + dto.getPath() + "/s_" + dto.getUuid() + "_" + dto.getName();
            file = new File(s);
            file.delete();
        }
        return dto.getUuid();
    }
}
