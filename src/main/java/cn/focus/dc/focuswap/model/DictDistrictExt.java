package cn.focus.dc.focuswap.model;

import cn.focus.dc.building.model.DictDistrict;

import org.apache.commons.lang.StringUtils;


public class DictDistrictExt extends DictDistrict {

    private static final long serialVersionUID = 3094745474664475478L;
    
    private String linkUrl;
    
    //pad图片对勾使用
    private String select;
    
    public String getSelect() {
        return select;
    }

    public void setSelect(String select) {
        this.select = select;
    }

    public String getCondName() {
        return super.getDistrictName();
    }

    public void setCondName(String condName) {
        super.setDistrictName(condName);
    }

    public String getCondValue() {
        return super.getDistrictId().toString();
    }

    public void setCondValue(String condValue) {
        super.setDistrictId(Integer.valueOf(condValue));
    }
    
//    @Override
//    public String toString() {
//        return getCondValue() + "@@" + getCondName();
//    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public DictDistrictExt clone(int index){
        DictDistrictExt show = new DictDistrictExt();
        show.setCondName(this.getCondName());
        String[] arr = this.getLinkUrl().split("_");
        arr[index] = arr[index].replace(this.getCondValue(),"");
        String url = StringUtils.join(arr,"_");
        if(url.indexOf("k______/") != -1){
            url = url.replace("k______/", "");
        }
        show.setLinkUrl(url);
        show.setCondValue(this.getCondValue());
        return show;
    }
}
