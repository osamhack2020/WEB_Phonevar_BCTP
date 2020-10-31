package kr.osam.phonevar.mapper;

import kr.osam.phonevar.domain.UnitInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("unitInfoMapper")
public interface UnitInfoMapper {
    @Select("SELECT * FROM phonevar.unitinfo WHERE unitName = #{unitName} AND isDeleted = 0")
    UnitInfo getUnitInfoByName(@Param("unitName") String unitName);

    @Select("SELECT unitCode FROM phonevar.unitinfo WHERE id = #{id} AND isDeleted = 0")
    Integer getUnitCodeById(@Param("id") int id);

    @Select("SELECT id FROM phonevar.unitinfo WHERE unitCode = #{unitCode} AND isDeleted = 0")
    Integer getIdByUnitCode(@Param("unitCode") int unitCode);

    @Select("SELECT * FROM phonevar.unitinfo WHERE isDeleted = 0")
    List<UnitInfo> getUnitList();
}
