package cn.focus.dc.focuswap.model;

import cn.focus.dc.commons.model.BaseObject;
import cn.focus.dc.focuswap.service.SearchService.SearchType;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class Condition extends BaseObject {

	private static final long serialVersionUID = 1L;

	private String di;

	private String ty;

	private String pr;

	private String ho;

	private String su;

	private String sp;

	private String se;

	public Condition() {

	}

	public Condition(String para) {
		if (StringUtils.isNotBlank(para)) {
			if (para.startsWith("k")) {
				para = para.substring(1, para.length());
			}
			String[] conditions = para.split("_");
			di = putCondition(conditions, 0);

			ty = putCondition(conditions, 1);
			pr = putCondition(conditions, 2);
			ho = putCondition(conditions, 3);
			su = putCondition(conditions, 4);
			sp = putCondition(conditions, 5);
			se = putCondition(conditions, 6);
		}
	}

	public static String verify(String para) {
		if (StringUtils.isNotBlank(para) && para.startsWith("k")) {
			if (StringUtils.countMatches(para, "_") != 6) {
				return null;
			}
			String[] conditions = para.split("_");
			String ho = putCondition(conditions, 3);
			// pc站跳转 热点区域选择
			if (null != ho && ho.length()>3) {
			 //ho = ho.substring(0, 3);
			   para = para.replace(ho,ho.substring(0, 3));
			}
		} else {
			return null;
		}
		return para;
	}

	private static String putCondition(String[] conditions, int index) {
		if (conditions.length > index) {
			return conditions[index];
		}
		return null;
	}

	public String getSe() {
		return se;
	}

	public void setSe(String se) {
		this.se = se;
	}

	public String getDi() {
		return di;
	}

	public void setDi(String di) {
		this.di = di;
	}

	public String getTy() {
		return ty;
	}

	public void setTy(String ty) {
		this.ty = ty;
	}

	public String getPr() {
		return pr;
	}

	public void setPr(String pr) {
		this.pr = pr;
	}

	public String getHo() {
		return ho;
	}

	public void setHo(String ho) {
		this.ho = ho;
	}

	public String getSu() {
		return su;
	}

	public void setSu(String su) {
		this.su = su;
	}

	public String getSp() {
		return sp;
	}

	public void setSp(String sp) {
		this.sp = sp;
	}

	public List<QueryData> combined() {
		List<QueryData> list = new ArrayList<QueryData>();
		putQueryData(SearchType.DISTRICT, di, list);
		putQueryData(SearchType.HOT, ho, list);
		putQueryData(SearchType.PRICE, pr, list);
		putQueryData(SearchType.TYPE, ty, list);
		putQueryData(SearchType.SUBWAY, su, list);
		putQueryData(SearchType.SPECIAL, sp, list);
		return list;
	}

	private void putQueryData(SearchType st, String select, List<QueryData> list) {
		if (StringUtils.isNotBlank(select)) {
			QueryData qd = new QueryData();
			qd.setqSelect(select);
			qd.setQueryType(st);
			list.add(qd);
		}
	}

}
