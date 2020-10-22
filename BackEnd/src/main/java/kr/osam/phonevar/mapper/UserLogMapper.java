package kr.osam.phonevar.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import kr.osam.phonevar.domain.UserLog;

@Repository("userLogMapper")
public interface UserLogMapper {
    @Select("SELECT * FROM phonevar.userlog")
    public List<UserLog> getUsersLog();
}
