package cn.focus.dc.focuswap.model;

import cn.focus.dc.commons.model.BaseObject;

import java.util.List;
public class BuildingLayoutPhotoes extends BaseObject {

    private static final long serialVersionUID = 4879172440078249552L;

    private Integer type;

    private Integer count;
    
    private String picUrl;

    private List<ProjPhotoesExt> photoes;
    
    private String minMaxArea;
    
    private String minMaxPrice;
    
    private String typeStr;
    
    private String typeClassStr;

    public String getTypeStr() {
		return typeStr;
	}

	public String getTypeClassStr() {
		return typeClassStr;
	}

	public void setTypeClassStr(String typeClassStr) {
		this.typeClassStr = typeClassStr;
	}

	public void setTypeStr(String typeStr) {
		this.typeStr = typeStr;
	}

	public String getMinMaxPrice() {
		return minMaxPrice;
	}

	public void setMinMaxPrice(String minMaxPrice) {
		this.minMaxPrice = minMaxPrice;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<ProjPhotoesExt> getPhotoes() {
        return photoes;
    }

    public void setPhotoes(List<ProjPhotoesExt> photoes) {
        this.photoes = photoes;
    }

    public String getMinMaxArea() {
        return minMaxArea;
    }

    public void setMinMaxArea(String minMaxArea) {
        this.minMaxArea = minMaxArea;
    }
}
