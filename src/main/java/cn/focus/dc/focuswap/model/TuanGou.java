package cn.focus.dc.focuswap.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 每一单团购的详细信息
 */
public class TuanGou implements Serializable,Comparable<TuanGou> {
    /**
		 * 
		 */
    private static final long serialVersionUID = -5000759014791329169L;

    public int getActvie_id() {
        return actvie_id;
    }

    public void setActvie_id(int actvie_id) {
        this.actvie_id = actvie_id;
    }

    public String getActvie_name() {
        return actvie_name;
    }

    public void setActvie_name(String actvie_name) {
        this.actvie_name = actvie_name;
    }

    public String getActvie_desc() {
        return actvie_desc;
    }

    public void setActvie_desc(String actvie_desc) {
        this.actvie_desc = actvie_desc;
    }

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public String getProj_name() {
        return proj_name;
    }

    public void setProj_name(String proj_name) {
        this.proj_name = proj_name;
    }

    public Date getActive_start() {
        return active_start;
    }

    public void setActive_start(Date active_start) {
        this.active_start = active_start;
    }

    public Date getActive_end() {
        return active_end;
    }

    public void setActive_end(Date active_end) {
        this.active_end = active_end;
    }

    public int getApply_num() {
        return apply_num;
    }

    public void setApply_num(int apply_num) {
        this.apply_num = apply_num;
    }

    public String getSale_status() {
        return sale_status;
    }

    public void setSale_status(String sale_status) {
        this.sale_status = sale_status;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getLat() {
        return lat;
    }

    public String getActive_order() {
		return active_order;
	}

	public void setActive_order(String active_order) {
		this.active_order = active_order;
	}

	public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
    
    

    private int actvie_id;// 团购ID

    private String actvie_name;// 活动名字

    private String actvie_desc;// 团购说明

    private int city_id;// 所在城市

    private int group_id;

    private String proj_name;

    private Date active_start;// 开团时间

    private Date active_end;

    private int apply_num;// 报名人数

    private String sale_status;

    private String price;// 楼盘价格

    private String address;// 楼盘地址

    private String location;// 朝阳海淀

    private String photo;//

    private String phone;

    private double lat;

    private double lng;
    
    private String active_order;
    
	@Override
	public int compareTo(TuanGou o) {
		// TODO Auto-generated method stub
		long ot = o.getActive_end().getTime();
		long tt = this.getActive_end().getTime();
		if(ot<tt) {
			return 1;
		} else if(ot>tt) {
			return -1;
		} else {
		return 0;
		}
	}

}
