package kr.osam.phonevar.mapper;

import kr.osam.phonevar.domain.User;
import kr.osam.phonevar.domain.UserMinified;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Repository("userMapper")
public interface UserMapper {
    @Insert("INSERT INTO phonevar.user (deviceUUID) VALUES (#{deviceUUID})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = int.class)
    User registerDevice(UserMinified userMinified);

    @Select("SELECT * FROM phonevar.user WHERE id = #{id} AND isDeleted = 0")
    User getUserById(@Param("id") int id);

    @Select("SELECT * FROM phonevar.user WHERE serviceNumber = #{serviceNumber} AND name = #{name} AND isDeleted = 0")
    User getUserByServiceNumberAndName(UserMinified userMinified);

    @Update("UPDATE phonevar.user SET serviceNumber = #{serviceNumber}, name = #{name}, organization = #{organization}, phoneNumber = #{phoneNumber}, dischargeDate = #{dischargeDate}, unitId = #{unitId}, statusCode = #{statusCode}, deviceUUID = #{deviceUUID} WHERE id = #{id}")
    void updateUser(User user);
}
