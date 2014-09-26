package cn.focus.dc.focuswap.model;

import java.io.Serializable;
import java.util.List;

import cn.focus.dc.commons.model.BaseObject;

/**
 * @author linfangwang
 */
public class BuildingDaoGou implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Integer cityId;

	//编辑作者
	private String editorName;
	
	//编辑头像
	private String editorPic;
	
	//编辑头衔
	private String editorTitle;
	
	//导购ID号
	private Integer id;
	
	//标签
	private List<String> keyWord;
	//显示在页面上的标签数据
	private String tab;
	
	//发布时间
	private String onlineTime;
	
	//导购焦点图
	private String pic;
	
	//浏览人数
	private String totalViews;
	
	//导购标题
	private String title;
	
	//总发布数
	private String totalPublish;
	
	private String content;
	private List<BuildingDaoGouContent> contentList;

	//导购导语
	private String promotion;
	
	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public String getEditorName() {
		return editorName;
	}

	public void setEditorName(String editorName) {
		this.editorName = editorName;
	}

	public String getEditorPic() {
		return editorPic;
	}

	public void setEditorPic(String editorPic) {
		this.editorPic = editorPic;
	}

	public String getEditorTitle() {
		return editorTitle;
	}

	public void setEditorTitle(String editorTitle) {
		this.editorTitle = editorTitle;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<String> getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(List<String> keyWord) {
		this.keyWord = keyWord;
	}

	public String getOnlineTime() {
		return onlineTime;
	}

	public void setOnlineTime(String onlineTime) {
		this.onlineTime = onlineTime;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getTotalViews() {
		return totalViews;
	}

	public void setTotalViews(String totalViews) {
		this.totalViews = totalViews;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTotalPublish() {
		return totalPublish;
	}

	public void setTotalPublish(String totalPublish) {
		this.totalPublish = totalPublish;
	}

	public String getTab() {
		return tab;
	}

	public void setTab(String tab) {
		this.tab = tab;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<BuildingDaoGouContent> getContentList() {
		return contentList;
	}

	public void setContentList(List<BuildingDaoGouContent> contentList) {
		this.contentList = contentList;
	}

	public String getPromotion() {
		return promotion;
	}

	public void setPromotion(String promotion) {
		this.promotion = promotion;
	}

}
