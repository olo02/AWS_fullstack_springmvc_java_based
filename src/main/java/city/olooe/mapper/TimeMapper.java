package city.olooe.mapper;

import org.apache.ibatis.annotations.Select;

import java.util.Date;

public interface TimeMapper {
    @Select("select sysdate from dual")
    public Date getTime();
}
