package cn.focus.dc.focuswap.model;

import cn.focus.dc.commons.model.BaseObject;


public class HomeFocusPic extends BaseObject{

	private static final long serialVersionUID = 42694934603585970L;
	
	private String picUrl;
	private String title;
	private String url;
	private boolean isAbsoluteUrl;
	
	/**
	 * 默认情况下是绝对路径（碎片化的）
	 * 非碎片化资源是相对路径
	 * @author zihaoli
	 */
	public HomeFocusPic() {
	}
	
	public HomeFocusPic(boolean isAbsoluteUrl) {
	    this.isAbsoluteUrl = isAbsoluteUrl;
	}
	
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

    public boolean isAbsoluteUrl() {
        return isAbsoluteUrl;
    }

    public void setAbsoluteUrl(boolean isAbsoluteUrl) {
        this.isAbsoluteUrl = isAbsoluteUrl;
    }

}
