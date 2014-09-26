package cn.focus.dc.focuswap.model;

import cn.focus.dc.commons.annotation.PrimaryKey;
import cn.focus.dc.commons.model.BaseObject;

public class DictCity extends BaseObject {

    private static final long serialVersionUID = 4269764934603585970L;

    @PrimaryKey
    private Integer cityId;

    private String cityName;

    private String cityCoding;

    private String cityPinyin;

    private String cityPinyinAbbr;

    private String domainName;

    private String type;

    private Integer priority;

    private Integer status;

    private Integer wapStatus;

    private String initial;

    public void copy(DictCity dictcity) {
        this.cityId = dictcity.cityId;
        this.cityName = dictcity.cityName;
        this.cityCoding = dictcity.cityCoding;
        this.cityPinyin = dictcity.cityPinyin;
        this.cityPinyinAbbr = dictcity.cityPinyinAbbr;
        this.domainName = dictcity.domainName;
        this.type = dictcity.type;
        this.priority = dictcity.priority;
        this.status = dictcity.status;
        this.wapStatus = dictcity.wapStatus;
        this.initial = dictcity.initial;
    }

    public Integer getWapStatus() {
        return wapStatus;
    }

    public void setWapStatus(Integer wapStatus) {
        this.wapStatus = wapStatus;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityCoding() {
        return cityCoding;
    }

    public void setCityCoding(String cityCoding) {
        this.cityCoding = cityCoding;
    }

    public String getCityPinyin() {
        return cityPinyin;
    }

    public void setCityPinyin(String cityPinyin) {
        this.cityPinyin = cityPinyin;
    }

    public String getCityPinyinAbbr() {
        return cityPinyinAbbr;
    }

    public void setCityPinyinAbbr(String cityPinyinAbbr) {
        this.cityPinyinAbbr = cityPinyinAbbr;
    }

    public String getDomainName() {
        if (domainName.indexOf("?cfrom=mobile") != -1) {
            return domainName;
        } else {
            return domainName + "/?cfrom=mobile";
        }
    }
    
    /**
     * added by roantianwz at 2014-06-17，由于getDomainName()方法被修改，要获取原始domainName时使用此方法
     * @return
     */
    public String getSourceDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getInitial() {
        return initial;
    }

    public void setInitial(String initial) {
        this.initial = initial;
    }

    public static DictCity getInstantce(Integer cityId, String cityName, String cityCoding,
            String cityPinyin, String cityPinyinAbbr, String domainName, String type, Integer priority,
            Integer status, String initial) {
        DictCity dictcity = new DictCity();
        dictcity.setCityId(cityId);
        dictcity.setCityName(cityName);
        dictcity.setCityCoding(cityCoding);
        dictcity.setCityPinyin(cityPinyin);
        dictcity.setCityPinyinAbbr(cityPinyinAbbr);
        dictcity.setDomainName(domainName);
        dictcity.setType(type);
        dictcity.setPriority(priority);
        dictcity.setStatus(status);
        dictcity.setInitial(initial);
        return dictcity;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((cityId == null) ? 0 : cityId.hashCode());
        result = prime * result + ((cityName == null) ? 0 : cityName.hashCode());
        result = prime * result + ((cityPinyin == null) ? 0 : cityPinyin.hashCode());
        result = prime * result + ((cityPinyinAbbr == null) ? 0 : cityPinyinAbbr.hashCode());
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
        DictCity other = (DictCity) obj;
        if (cityId == null) {
            if (other.cityId != null)
                return false;
        } else if (!cityId.equals(other.cityId))
            return false;
        if (cityName == null) {
            if (other.cityName != null)
                return false;
        } else if (!cityName.equals(other.cityName))
            return false;
        if (cityPinyin == null) {
            if (other.cityPinyin != null)
                return false;
        } else if (!cityPinyin.equals(other.cityPinyin))
            return false;
        if (cityPinyinAbbr == null) {
            if (other.cityPinyinAbbr != null)
                return false;
        } else if (!cityPinyinAbbr.equals(other.cityPinyinAbbr))
            return false;
        return true;
    }
}
