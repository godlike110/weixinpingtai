package cn.focus.dc.focuswap.model;

import java.util.HashMap;
import java.util.Map;

public enum LoginType {

	QQ(2,"qq"),
	SINA(1,"sina"),
	PHONE(7,"phone");
	
	public static Map<String,Integer> typeMap = new HashMap<String,Integer>();
	
	static {
		for(LoginType lt:LoginType.values()) {
			typeMap.put(lt.getDes(), lt.getType());
		}
	}
	
	private int type;
	private String des;
	
	LoginType(int type,String des) {
		this.type = type;
		this.des = des;
	}
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	
}
