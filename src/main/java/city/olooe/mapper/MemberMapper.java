package city.olooe.mapper;

import city.olooe.domain.MemberVO;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MemberMapper {
//    void insert(MemberVO vo);

/*    @Select("select * from tbl_member")
    List<MemberVO> getList();*/

	MemberVO read(String userid);

/*    @Select("select * from tbl_member where id = #{id} and pw = #{pw}")
    MemberVO login(MemberVO vo);*/
}
