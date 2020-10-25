package kr.osam.phonevar.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {
    private Integer id;
    private String serviceNumber;
    private String name;
    private String organization;
    private String phoneNumber;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Date dischargeDate;
    private Integer unitId;
    private Integer statusCode;
    private String deviceUUID;
    private Boolean isDeleted;
    @JsonIgnore
    private Date createdAt;
    @JsonIgnore
    private Date updatedAt;
    // JWT Token
    private String token;
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getServiceNumber() {
        return serviceNumber;
    }
    
    public void setServiceNumber(String serviceNumber) {
        this.serviceNumber = serviceNumber;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getOrganization() {
        return organization;
    }
    
    public void setOrganization(String organization) {
        this.organization = organization;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    public Date getDischargeDate() {
        return dischargeDate;
    }
    
    public void setDischargeDate(Date dischargeDate) {
        this.dischargeDate = dischargeDate;
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getDeviceUUID() {
        return deviceUUID;
    }

    public void setDeviceUUID(String deviceUUID) {
        this.deviceUUID = deviceUUID;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Date getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    
    public Date getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void update(User user) {
        if (user.serviceNumber != null) {
            this.serviceNumber = user.serviceNumber;
        }
        if (user.name != null) {
            this.name = user.name;
        }
        if (user.organization != null) {
            this.organization = user.organization;
        }
        if (user.phoneNumber != null) {
            this.phoneNumber = user.phoneNumber;
        }
        if (user.dischargeDate != null) {
            this.dischargeDate = user.dischargeDate;
        }
        if (user.unitId != null) {
            this.unitId = user.unitId;
        }
        if (user.statusCode != null) {
            this.statusCode = user.statusCode;
        }
    }

    public void update(UserMinified userMinified) {
        if (userMinified.getServiceNumber() != null) {
            this.serviceNumber = userMinified.getServiceNumber();
        }
        if (userMinified.getName() != null) {
            this.name = userMinified.getName();
        }
        if (userMinified.getDeviceUUID() != null) {
            this.deviceUUID = userMinified.getDeviceUUID();
        }
    }
}