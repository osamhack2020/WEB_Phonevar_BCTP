package kr.osam.phonevar.mapper;

import kr.osam.phonevar.domain.UserLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userLogMapper")
public interface UserLogMapper {
    @Select("SELECT * FROM phonevar.userlog WHERE userId = #{id}")
    List<UserLog> getUserLog(@Param("id") int id);

    @Insert("INSERT INTO phonevar.userlog (userId, loggedAt) VALUES (#{userId}, #{loggedAt})")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = int.class)
    void createUserLog(UserLog userLog);
}
