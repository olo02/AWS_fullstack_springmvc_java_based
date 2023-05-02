package city.olooe.service;

import city.olooe.domain.MemberVO;
import city.olooe.mapper.MemberMapper;
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
public class MemberServiceImpl implements MemberService {
    @Setter
    @Autowired
    private MemberMapper memberMapper;

/*    @Override
    public List<MemberVO> getList() {
        return memberMapper.getList();
    }
*/
    @Override
    public MemberVO get(String id) {
        return memberMapper.read(id);
    }

/*    @Override
    public MemberVO get(MemberVO vo) {
        return memberMapper.login(vo);
    }*/
}
