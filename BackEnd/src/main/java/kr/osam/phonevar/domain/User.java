package kr.osam.phonevar.domain;

import java.util.Date;

public class User {
    private Integer id;
    private String serviceNumber;
    private String name;
    private String organization;
    private String phoneNumber;
    private Date dischargeDate;
    private Integer unitCode;
    private Date createdAt;
    private Date updatedAt;
    
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getServiceNumber() {
        return this.serviceNumber;
    }
    
    public void setServiceNumber(String serviceNumber) {
        this.serviceNumber = serviceNumber;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getOrganization() {
        return this.organization;
    }
    
    public void setOrganization(String organization) {
        this.organization = organization;
    }
    
    public String getPhoneNumber() {
        return this.phoneNumber;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    public Date getDischargeDate() {
        return this.dischargeDate;
    }
    
    public void setDischargeDate(Date dischargeDate) {
        this.dischargeDate = dischargeDate;
    }
    
    public Integer getUnitCode() {
        return this.unitCode;
    }
    
    public void setUnitCode(Integer unitCode) {
        this.unitCode = unitCode;
    }
    
    public Date getCreatedAt() {
        return this.createdAt;
    }
    
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    
    public Date getUpdatedAt() {
        return this.updatedAt;
    }
    
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}

/*
CREATE TABLE `phonevar`.`user` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `serviceNumber` VARCHAR(20) NOT NULL,
  `name` VARCHAR(10) NOT NULL,
  `organization` VARCHAR(255) NULL DEFAULT NULL,
  `phoneNumber` VARCHAR(15) NULL DEFAULT NULL,
  `dischargeDate` TIMESTAMP NOT NULL,
  `unitCode` INT(10) NULL DEFAULT NULL,
  `isDeleted` TINYINT(1) NOT NULL DEFAULT '0',
  `createdAt` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updatedAt` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;
*/
