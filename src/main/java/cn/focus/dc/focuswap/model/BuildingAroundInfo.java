package cn.focus.dc.focuswap.model;

import cn.focus.dc.building.model.vo.ProjInfoVO;


public class BuildingAroundInfo extends ProjInfoVO implements PhotoRealPathGenerable {

    private static final long serialVersionUID = -7746978442214376177L;
    
    private String logoUr;
    
    private String logUrl;
    
    private String avgPriceShow;

    @Override
    public void setRealPath(String realPath) {
        this.logoUr = realPath;
    }

    @Override
    public String getPhotoAlias() {
        return super.getProjLogo().getPhotoAlias();
    }

    @Override
    public String getPath() {
        return super.getProjLogo().getPath();
    }

    @Override
    public Integer getCityId() {
        return super.getCityId();
    }

    @Override
    public Integer getPhotoWidth() {
        return 120;
    }

    public String getLogoUr() {
        return logoUr;
    }

    public String getAvgPriceShow() {
        return avgPriceShow;
    }

    public void setAvgPriceShow(String avgPriceShow) {
        this.avgPriceShow = avgPriceShow;
    }

	public String getLogUrl() {
		return logUrl;
	}

	public void setLogUrl(String logUrl) {
		this.logUrl = logUrl;
	}

	public void setLogoUr(String logoUr) {
		this.logoUr = logoUr;
	}

}
