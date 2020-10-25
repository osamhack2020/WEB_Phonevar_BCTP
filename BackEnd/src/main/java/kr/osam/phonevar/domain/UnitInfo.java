package kr.osam.phonevar.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UnitInfo {
    private Integer id;
    private Integer unitCode;
    private String unitName;
    private Boolean isDeleted;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(Integer unitCode) {
        this.unitCode = unitCode;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}
