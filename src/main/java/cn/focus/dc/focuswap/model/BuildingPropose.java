package cn.focus.dc.focuswap.model;

import cn.focus.dc.commons.model.BaseObject;

import java.util.List;

/**
 * 
 * @author kanezheng
 *
 */
public class BuildingPropose extends BaseObject{

    private static final long serialVersionUID = 1L;
    
    private int id;
    
    private int editId;
    
    private String editorName;
    
    private String pic;
    
    //导语
    private String promotion;
    
    private String title;
    
    private String pubDate;
    
    private List<Integer> groupIds;
    
    private List<String> buildNames;
    
    private String content;
    
    private List<BuildingInfo> pros;
    
    private String buildingNameShow;

    public String getBuildingNameShow() {
        return buildingNameShow;
    }

    public void setBuildingNameShow(String buildingNameShow) {
        this.buildingNameShow = buildingNameShow;
    }

    public List<BuildingInfo> getPros() {
        return pros;
    }

    public void setPros(List<BuildingInfo> pros) {
        this.pros = pros;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEditId() {
        return editId;
    }

    public void setEditId(int editId) {
        this.editId = editId;
    }

    public String getEditorName() {
        return editorName;
    }

    public void setEditorName(String editorName) {
        this.editorName = editorName;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getPromotion() {
        return promotion;
    }

    public void setPromotion(String promotion) {
        this.promotion = promotion;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public List<Integer> getGroupIds() {
        return groupIds;
    }

    public void setGroupIds(List<Integer> groupIds) {
        this.groupIds = groupIds;
    }

    public List<String> getBuildNames() {
        return buildNames;
    }

    public void setBuildNames(List<String> buildNames) {
        this.buildNames = buildNames;
    }
    
}
