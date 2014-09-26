package cn.focus.dc.focuswap.model;

import cn.focus.dc.commons.model.BaseObject;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: yunguangwang
 * Date: 13-9-11
 * Time: 下午2:50
 * To change this template use File | Settings | File Templates.
 */
public class RecentNews  extends BaseObject {
    /**
     * 
     */
    private static final long serialVersionUID = 7988141004176709010L;

    //private Integer info_id;
    
    private Integer infoid;

    private String infoname;
    
    //private String title;
    
    private String infocontent;
    
    //private String content;

    private String infosummary;

    private String infoauthor;
    
    private String infotime;

    //private String time;

    private List<Integer> group_ids;
    
    private List<BuildingInfo> pros;
    
    
    
    public String getInfotime() {
        return infotime;
    }

    public void setInfotime(String infotime) {
        this.infotime = infotime;
    }

    public Integer getInfoid() {
        return infoid;
    }

    public void setInfoid(Integer infoid) {
        this.infoid = infoid;
    }

    public String getInfocontent() {
        return infocontent;
    }

    public void setInfocontent(String infocontent) {
        this.infocontent = infocontent;
    }

    public String getInfoname() {
        return infoname;
    }

    public void setInfoname(String infoname) {
        this.infoname = infoname;
    }

    public List<BuildingInfo> getPros() {
        return pros;
    }

    public void setPros(List<BuildingInfo> pros) {
        this.pros = pros;
    }

    public List<Integer> getGroup_ids() {
        return group_ids;
    }

    public void setGroup_ids(List<Integer> group_ids) {
        this.group_ids = group_ids;
    }

    public RecentNews() {
    }

    public Integer getInfo_id() {
        return infoid;
    }

    public void setInfo_id(Integer infoid) {
        this.infoid = infoid;
    }

    public String getTitle() {
        return infoname;
    }

    public void setTitle(String title) {
        this.infoname = title;
    }

    public String getContent() {
        return infocontent;
    }

    public void setContent(String infocontent) {
        this.infocontent = infocontent;
    }

    public String getInfoauthor() {
        return infoauthor;
    }

    public void setInfoauthor(String infoauthor) {
        this.infoauthor = infoauthor;
    }

    public String getTime() {
        return infotime;
    }

    public void setTime(String infotime) {
        this.infotime = infotime;
    }

    public String getInfosummary() {
        return infosummary;
    }

    public void setInfosummary(String infosummary) {
        this.infosummary = infosummary;
    }

}
