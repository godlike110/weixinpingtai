package cn.focus.dc.focuswap.model;

import cn.focus.dc.building.model.es.EsProjInfo;

import java.util.Date;


@SuppressWarnings("serial")
public class EsProjInfoChild extends EsProjInfo implements PhotoRealPathGenerable{

    String url = "";
    
    String roomInfo = "";
    
    String roomOne = "";
    
    String roomTwo = "";
    
    String avgPriceShow = "";
    
    String statusShow;    
    
    String buildingUrl;
    
    Date recommendDate;
    
    int recommendPeriods;
    
        
    public String getRoomInfo() {
		return roomInfo;
	}

	public void setRoomInfo(String roomInfo) {
		this.roomInfo = roomInfo;
	}

	public String getBuildingUrl() {
        return buildingUrl;
    }

    public void setBuildingUrl(String buildingUrl) {
        this.buildingUrl = buildingUrl;
    }

    public String getStatusShow() {
        return statusShow;
    }

    // public void setStatusShow() {
    // switch (getSaleStatus()) {
    // case 0:
    // this.statusShow = "<em class='in_sale' data-title=在售></em>";
    // break;
    // case 1:
    // this.statusShow = "<em class='wait_sale' data-title=待售></em>";
    // break;
    // case 2:
    // this.statusShow = "<em class='last_sale' data-title=尾盘></em>";
    // break;
    // case 3:
    // this.statusShow = "<em class='out_sale' data-title=售罄></em>";
    // break;
    // default:
    // break;
    // }
    // }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRoomOne() {
        return roomOne;
    }

    public void setRoomOne(String roomOne) {
        this.roomOne = roomOne;
    }

    public String getRoomTwo() {
        return roomTwo;
    }

    public void setRoomTwo(String roomTwo) {
        this.roomTwo = roomTwo;
    }

    public String getAvgPriceShow() {
        return avgPriceShow;
    }

    public void setAvgPriceShow(String avgPriceShow) {
        this.avgPriceShow = avgPriceShow;
    }

    @Override
    public void setRealPath(String realPath) {
        this.url = realPath;
    }

    @Override
    public String getPhotoAlias() {
        return this.getBasePic().getPhotoAlias();
    }

    @Override
    public String getPath() {
        return this.getBasePic().getPath();
    }

    @Override
    public Integer getPhotoWidth() {
        return 240;
    }

    public Date getRecommendDate() {
        return recommendDate;
    }

    public void setRecommendDate(Date recommendDate) {
        this.recommendDate = recommendDate;
    }

    public int getRecommendPeriods() {
        return recommendPeriods;
    }

    public void setRecommendPeriods(int recommendPeriods) {
        this.recommendPeriods = recommendPeriods;
    } 

}
