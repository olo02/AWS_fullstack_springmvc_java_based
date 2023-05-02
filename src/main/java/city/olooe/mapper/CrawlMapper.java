package city.olooe.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

public interface CrawlMapper {
    @Insert("insert into t_cgv (midx, title, href, date, thumb, thumb_alt, age) values (#{midx}, #{title}, #{href}, #{date}, #{thumb}, #{thumb_alt}, #{age})")
    void insert(Map<String, Object> map);

    @Select("select * from t_cgv")
    List<Map<String, Object>> getList();

    @Select("select * from t_cgv where midx = #{midx}")
    Map<String, Object> select(String midx);

    @Insert("insert into t_person(pidx, name, href) values (#{pidx}, #{name}, #{href}) on duplicate key update name = name")
    void insertPerson(Map<String, String> map);

    @Insert("insert into t_actor(pidx, midx) values (#{pidx}, #{midx})")
    void insertActor(Map<String, String> map);

    @Insert("insert into t_director(pidx, midx) values (#{pidx}, #{midx})")
    void insertDirector(Map<String, String> map);

    @Insert("insert into t_cgv_attach(odr, midx) values (#{odr}, #{midx})")
    void insertAttach(Map<String, String> map);

    @Update("update t_cgv set info = #{info}, engtitle = #{engtitle}, genre = #{genre}, runningtime = #{runningtime}, nation = #{nation} where midx = #{midx}")
    void updateCGV(Map<String, String> map);
}
