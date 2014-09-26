package cn.focus.dc.focuswap.model;

/**
 * 
 * @author zhiweiwen
 * 2013-11-21
 * 下午2:45:44
 */
public class City extends DictCity{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    //城市状态
    private int cityStatus;
    
    //二手房地址
    private String esfUrl;

    //新房地址
    private String xinfangUrl;
    
    //suggest地址
    private String suggestUrl;
    
    private String buildingListUrl;
    
    
       
    public String getBuildingListUrl() {
        return buildingListUrl;
    }

    public void setBuildingListUrl(String buildingListUrl) {
        this.buildingListUrl = buildingListUrl;
    }

    public String getXinfangUrl() {
        return xinfangUrl;
    }

    public void setXinfangUrl(String xinfangUrl) {
        this.xinfangUrl = xinfangUrl;
    }

    public String getSuggestUrl() {
        return suggestUrl;
    }

    public void setSuggestUrl(String suggestUrl) {
        this.suggestUrl = suggestUrl;
    }

    public int getCityStatus() {
        return cityStatus;
    }

    public void setCityStatus(int cityStatus) {
        this.cityStatus = cityStatus;
    }

    public String getEsfUrl() {
        return esfUrl;
    }

    public void setEsfUrl(String esfUrl) {
        this.esfUrl = esfUrl;
    }
    
    

}
