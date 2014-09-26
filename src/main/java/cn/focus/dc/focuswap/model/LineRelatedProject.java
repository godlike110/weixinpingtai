package cn.focus.dc.focuswap.model;

import cn.focus.dc.commons.model.BaseObject;

/**
 * 爬房团线路的相关楼盘信息
 * @author rogantian
 * @date 2013-12-19
 * @email rogantianwz@gmail.com
 */
public class LineRelatedProject extends BaseObject {

    /**
     * 
     */
    private static final long serialVersionUID = 4147101942721756649L;

    private Integer proj_id;
    
    private Integer group_id;

    private Integer city_id;

    private String name;

    private String price;

    private String discount;

    private String place;

    private String phone;

    private String pic_path;
    
    private String building_url;

    public Integer getProj_id() {
        return proj_id;
    }

    public void setProj_id(Integer proj_id) {
        this.proj_id = proj_id;
    }

    public Integer getCity_id() {
        return city_id;
    }

    public void setCity_id(Integer city_id) {
        this.city_id = city_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPic_path() {
        return pic_path;
    }

    public void setPic_path(String pic_path) {
        this.pic_path = pic_path;
    }

    public Integer getGroup_id() {
        return group_id;
    }

    public void setGroup_id(Integer group_id) {
        this.group_id = group_id;
    }

    public String getBuilding_url() {
        return building_url;
    }

    public void setBuilding_url(String building_url) {
        this.building_url = building_url;
    }
}
