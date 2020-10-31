package kr.osam.phonevar.mapper;

import kr.osam.phonevar.domain.User;
import kr.osam.phonevar.domain.UserMinified;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository("userMapper")
public interface UserMapper {
    @Select("SELECT * FROM phonevar.user WHERE isDeleted = 0")
    List<User> getUserList();

    @Select("SELECT * FROM phonevar.user WHERE unitCode = #{unitCode} AND isDeleted = 0")
    List<User> getUserListByUnitCode(@Param("unitCode") int unitCode);
	
    @Select("SELECT * FROM phonevar.user WHERE id = #{id} AND isDeleted = 0")
    User getUserById(@Param("id") int id);

    @Select("SELECT * FROM phonevar.user WHERE serviceNumber = #{serviceNumber} AND name = #{name} AND isDeleted = 0")
    User getUserByServiceNumberAndName(UserMinified userMinified);

    @Update("UPDATE phonevar.user SET serviceNumber = #{serviceNumber}, name = #{name}, organization = #{organization}, phoneNumber = #{phoneNumber}, dischargeDate = #{dischargeDate}, unitId = #{unitId}, statusCode = #{statusCode}, deviceUUID = #{deviceUUID} WHERE id = #{id}")
    void updateUser(User user);
	
}
