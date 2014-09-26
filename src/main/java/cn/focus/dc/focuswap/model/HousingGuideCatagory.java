package cn.focus.dc.focuswap.model;

import cn.focus.dc.commons.annotation.PrimaryKey;
import cn.focus.dc.commons.model.BaseObject;

public class HousingGuideCatagory extends BaseObject {

    private static final long serialVersionUID = -6801591105668530826L;

    @PrimaryKey
    private Integer id;


    private Integer parentId;

    private String name;

    private String description;

    private Integer editorId;

    private Boolean status;

    private java.util.Date createTime;

    private java.util.Date updateTime;

    public void copy(HousingGuideCatagory housingguidecatagory) {
        this.id = housingguidecatagory.id;
        this.parentId = housingguidecatagory.parentId;
        this.name = housingguidecatagory.name;
        this.description = housingguidecatagory.description;
        this.editorId = housingguidecatagory.editorId;
        this.status = housingguidecatagory.status;
        this.createTime = housingguidecatagory.createTime;
        this.updateTime = housingguidecatagory.updateTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getEditorId() {
        return editorId;
    }

    public void setEditorId(Integer editorId) {
        this.editorId = editorId;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public java.util.Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(java.util.Date createTime) {
        this.createTime = createTime;
    }

    public java.util.Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(java.util.Date updateTime) {
        this.updateTime = updateTime;
    }
}