package cn.focus.dc.focuswap.model;

import cn.focus.dc.commons.model.BaseObject;

import java.util.List;


/**
 * 爬房团线路信息
 * @author rogantian
 * @date 2013-12-19
 * @email rogantianwz@gmail.com
 */
public class LineInfo extends BaseObject {

    /**
     * 
     */
    private static final long serialVersionUID = -8726799642160412946L;

    private Integer line_id;

    private String title;

    private String sub_title;

    private Integer city_id;

    private String send_date;

    private String close_date;

    private Integer join_nums;

    private Integer surplus_nums;

    private Integer status;

    private List<LineRelatedProject> project;

    public Integer getLine_id() {
        return line_id;
    }

    public void setLine_id(Integer line_id) {
        this.line_id = line_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSub_title() {
        return sub_title;
    }

    public void setSub_title(String sub_title) {
        this.sub_title = sub_title;
    }

    public Integer getCity_id() {
        return city_id;
    }

    public void setCity_id(Integer city_id) {
        this.city_id = city_id;
    }

    public String getSend_date() {
        return send_date;
    }

    public void setSend_date(String send_date) {
        this.send_date = send_date;
    }

    public String getClose_date() {
        return close_date;
    }

    public void setClose_date(String close_date) {
        this.close_date = close_date;
    }

    public Integer getJoin_nums() {
        return join_nums;
    }

    public void setJoin_nums(Integer join_nums) {
        this.join_nums = join_nums;
    }

    public Integer getSurplus_nums() {
        return surplus_nums;
    }

    public void setSurplus_nums(Integer surplus_nums) {
        this.surplus_nums = surplus_nums;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<LineRelatedProject> getProject() {
        return project;
    }

    public void setProject(List<LineRelatedProject> project) {
        this.project = project;
    }
    
    

}
