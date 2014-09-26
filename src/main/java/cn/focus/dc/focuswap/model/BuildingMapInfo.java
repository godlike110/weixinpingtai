package cn.focus.dc.focuswap.model;

import cn.focus.dc.commons.model.BaseObject;

/**
 * @author qiaowang
 *
 */
public class BuildingMapInfo extends BaseObject {

    private static final long serialVersionUID = 8344542232536187977L;

    private int groupId;
    
    private int cityId;
    
    private int projId;
    
    private String projName;
    
    private String longitude;
    
    private String latitude;
    
    private String avgPrice;

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public int getProjId() {
        return projId;
    }

    public void setProjId(int projId) {
        this.projId = projId;
    }

    public String getProjName() {
        return projName;
    }

    public void setProjName(String projName) {
        this.projName = projName;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(String avgPrice) {
        this.avgPrice = avgPrice;
    }
}
