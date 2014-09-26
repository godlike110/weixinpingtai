package cn.focus.dc.focuswap.model;

import cn.focus.dc.commons.model.BaseObject;

public class RecommendHouse extends BaseObject{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private Integer groupId;
    
    private String picUrl;
    
    private Long recommendDate;
    
    private String reason;

    public Long getRecommendDate() {
        return recommendDate;
    }

    public void setRecommendDate(Long recommendDate) {
        this.recommendDate = recommendDate;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

}
