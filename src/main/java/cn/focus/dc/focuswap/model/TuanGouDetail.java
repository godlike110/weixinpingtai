package cn.focus.dc.focuswap.model;

import java.io.Serializable;
import java.util.Date;

public class TuanGouDetail implements Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = -2823989207251910985L;

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }


    public String getActive_name() {
        return active_name;
    }

    public void setActive_name(String active_name) {
        this.active_name = active_name;
    }

    public String getActive_desc() {
        return active_desc;
    }

    public void setActive_desc(String active_desc) {
        this.active_desc = active_desc;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPrice_unit() {
        return price_unit;
    }

    public void setPrice_unit(String price_unit) {
        this.price_unit = price_unit;
    }

    public int getApply_num() {
        return apply_num;
    }

    public void setApply_num(int apply_num) {
        this.apply_num = apply_num;
    }

    public int getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(int timeLeft) {
        this.timeLeft = timeLeft;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProj_name() {
        return proj_name;
    }

    public void setProj_name(String proj_name) {
        this.proj_name = proj_name;
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

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public int getActive_id() {
        return active_id;
    }
    

    public void setActive_id(int active_id) {
        this.active_id = active_id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    private int active_id;

    public int getActive_order() {
		return active_order;
	}

	public void setActive_order(int active_order) {
		this.active_order = active_order;
	}
	private String photo;

    private String active_name;// 活动名字

    private String active_desc;// 团购说明

    private String price;
    
    private String price_unit;// 楼盘价格单位（套、平米等）

    private int apply_num;// 报名人数

    private int timeLeft;// 倒计时

    private String address;// 楼盘地址

    private String proj_name;

    private String phone;

    private double lat;

    private double lng;

    private String location;
    
    private Date active_start;// 开团时间

    private Date active_end;
    
    private int group_id;
    
    private int active_order;

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

//	@Override
//	public int compareTo(TuanGouDetail o) {
//		// TODO Auto-generated method stub
//		long ot = o.getActive_end().getTime();
//		long tt = this.getActive_end().getTime();
//		if(ot<tt) {
//			return -1;
//		} else if(ot>tt) {
//			return 1;
//		} else {
//		return 0;
//		}
//	}

}
