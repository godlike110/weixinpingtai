package cn.focus.dc.focuswap.model;

import com.alibaba.fastjson.annotation.JSONField;

public class QuestionInfo extends Question {

    private static final long serialVersionUID = 126396203785112411L;

    private String userName;

    private String cityName;

    private String buildName;

    private String editorName;

    private String editorTitle;
    
    private Integer groupId;

    @JSONField(name = "editor")
    private String editorDesc;

    private Integer usefulCount;

    public QuestionInfo() {
        super();
    }
    
    public Integer getGroupId() {
        return groupId;
    }



    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }



    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getBuildName() {
        return buildName;
    }

    public void setBuildName(String buildName) {
        this.buildName = buildName;
    }

    public String getEditorName() {
        return editorName;
    }

    public void setEditorName(String editorName) {
        this.editorName = editorName;
    }

    public String getEditorTitle() {
        return editorTitle;
    }

    public void setEditorTitle(String editorTitle) {
        this.editorTitle = editorTitle;
    }

    public String getEditorDesc() {
        return editorDesc;
    }

    public void setEditorDesc(String editorDesc) {
        this.editorDesc = editorDesc;
    }

    public Integer getUsefulCount() {
        return usefulCount;
    }

    public void setUsefulCount(Integer usefulCount) {
        this.usefulCount = usefulCount;
    }

}