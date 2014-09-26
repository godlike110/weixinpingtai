package cn.focus.dc.focuswap.model;

import cn.focus.dc.commons.annotation.PrimaryKey;
import cn.focus.dc.commons.model.BaseObject;

import java.util.Date;
public class CMSManager extends BaseObject {

    private static final long serialVersionUID = -8509063875636221463L;

    @PrimaryKey
    private Integer id;

    private String accountId;

    private String email;

    private String password;

    private String name;

    private Integer cityId;

    private String position;
    
    private Integer roleId;

    private String lastLoginIp;

    private Date lastLoginDate;

    private String phoneNumber;

    private Date expiryDate;

    private Integer status;
    
    private String picUrl;

    public void copy(CMSManager cmsManager) {
        this.id = cmsManager.id;
        this.accountId = cmsManager.accountId;
        this.email = cmsManager.email;
        this.password = cmsManager.password;
        this.name = cmsManager.name;
        this.position = cmsManager.position;
        this.lastLoginIp = cmsManager.lastLoginIp;
        this.lastLoginDate = cmsManager.lastLoginDate;
        this.phoneNumber = cmsManager.phoneNumber;
        this.expiryDate = cmsManager.expiryDate;
        this.status = cmsManager.status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((cityId == null) ? 0 : cityId.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        CMSManager other = (CMSManager) obj;
        if (cityId == null) {
            if (other.cityId != null)
                return false;
        } else if (!cityId.equals(other.cityId))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }
}
