package cn.focus.dc.focuswap.model;

/**
 * @author linfangwang
 *
 */
public class BuildingDaoGouContent {

	private String buildAddress;//地址
	private String buildLocation;//位置
	private String buildPic;//图片(推荐楼盘)
	private String buildPicName;//图片名字
	private String buildPrice;//价格
	private String content;
	private int groupId;//楼盘ID号
	private String phone400;
	private String phoneFenji;//fenji
	private String recommend;
	private String buildPicBig;//正文图
	
	public String getPhoneFenji() {
		return phoneFenji;
	}
	public void setPhoneFenji(String phoneFenji) {
		this.phoneFenji = phoneFenji;
	}
	public String getBuildAddress() {
		return buildAddress;
	}
	public void setBuildAddress(String buildAddress) {
		this.buildAddress = buildAddress;
	}
	public String getBuildLocation() {
		return buildLocation;
	}
	public void setBuildLocation(String buildLocation) {
		this.buildLocation = buildLocation;
	}
	public String getBuildPic() {
		return buildPic;
	}
	public void setBuildPic(String buildPic) {
		this.buildPic = buildPic;
	}
	public String getBuildPicName() {
		return buildPicName;
	}
	public void setBuildPicName(String buildPicName) {
		this.buildPicName = buildPicName;
	}
	public String getBuildPrice() {
		return buildPrice;
	}
	public void setBuildPrice(String buildPrice) {
		this.buildPrice = buildPrice;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	public String getPhone400() {
		return phone400;
	}
	public void setPhone400(String phone400) {
		this.phone400 = phone400;
	}
	public String getRecommend() {
		return recommend;
	}
	public void setRecommend(String recommend) {
		this.recommend = recommend;
	}

	public String getBuildPicBig() {
		return buildPicBig;
	}
	public void setBuildPicBig(String buildPicBig) {
		this.buildPicBig = buildPicBig;
	}
}
