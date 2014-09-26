package cn.focus.dc.focuswap.model;

import cn.focus.dc.commons.annotation.PrimaryKey;
import cn.focus.dc.commons.model.BaseObject;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

public class HousingGuide extends BaseObject {


    private static final long serialVersionUID = -8076714186918906291L;
    @PrimaryKey
    private Integer id;

    private String title;

    private Integer catagoryId;

    private String catagoryName;

    private String summary;

    private String content;

    private String picUrl;

    private Boolean isLike = false;

    private Integer likeCount;

    @JSONField(format="yyyy-MM-dd")
    private Date createTime;

    public void copy(HousingGuide housingguide) {
        this.id = housingguide.id;
        this.title = housingguide.title;
        this.catagoryId = housingguide.catagoryId;
        this.catagoryName = housingguide.catagoryName;
        this.summary = housingguide.summary;
        this.content = housingguide.content;
        this.picUrl = housingguide.picUrl;
        this.isLike = housingguide.isLike;
        this.likeCount = housingguide.likeCount;
        this.createTime = housingguide.createTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getCatagoryId() {
        return catagoryId;
    }

    public void setCatagoryId(Integer catagoryId) {
        this.catagoryId = catagoryId;
    }

    public String getCatagoryName() {
        return catagoryName;
    }

    public void setCatagoryName(String catagoryName) {
        this.catagoryName = catagoryName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public Boolean getIsLike() {
        return isLike;
    }

    public void setIsLike(Boolean isLike) {
        this.isLike = isLike;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}