package kr.osam.phonevar.mapper;

import kr.osam.phonevar.domain.UnitInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository("unitInfoMapper")
public interface UnitInfoMapper {
    @Select("SELECT * FROM phonevar.unitinfo WHERE unitName = #{unitName} AND isDeleted = 0")
    UnitInfo getUnitInfoByName(@Param("unitName") String unitName);
}
