package city.olooe.mapper;

import city.olooe.domain.NoteVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface NoteMapper {
    // 작성
    @Insert("insert into tbl_note (noteno, sender, receiver, message) values (seq_note.nextval, #{sender}, #{receiver}, #{message})")
    int insert(NoteVO vo);

    // 단일 조회
    @Select("select * from tbl_note where NOTENO = #{noteno}")
    NoteVO selectOne(Long noteno);

    // 수신 확인
    @Update("update tbl_note set rdate = sysdate where noteno = #{noteno}")
    int update(Long noteno);

    // 노트삭제
    @Delete("delete from tbl_note where noteno = #{noteno}")
    int delete(Long noteno);

    // 보낸거
    @Select("select * from tbl_note where noteno > 0 and sender = #{id} order by 1 desc")
    List<NoteVO> sendList(String sender);

    // 받은거
    @Select("select * from tbl_note where noteno > 0 and receiver = #{id} order by 1 desc")
    List<NoteVO> receiveList(String receiver);

    // 받앗는데 확인 x
    @Select("select * from tbl_note where noteno > 0 and receiver = #{id} and rdate is null order by 1 desc")
    List<NoteVO> receiveUncheckedList(String receiver);

    @Select("select * from tbl_note where noteno > 0 and ((sender = #{sender} and receiver = #{receiver}) or (sender = #{receiver} and receiver = #{sender})) order by 1 desc")
    List<NoteVO> getMessages(String sender, String receiver);
}
