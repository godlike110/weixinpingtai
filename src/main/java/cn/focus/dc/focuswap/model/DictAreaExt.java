package cn.focus.dc.focuswap.model;

import cn.focus.dc.building.model.DictArea;

import org.apache.commons.lang.StringUtils;


public class DictAreaExt extends DictArea {

    private static final long serialVersionUID = -3367159729579379702L;

    private String linkUrl;
    
    //pad图片对勾使用
    private String select;
    
    public String getSelect() {
        return select;
    }

    public void setSelect(String select) {
        this.select = select;
    }
    
    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getCondName() {
        return super.getAreaName();
    }

    public void setCondName(String condName) {
        super.setAreaName(condName);
    }


    public void setCondValue(String condValue) {
        super.setAreaId(Integer.valueOf(condValue));
    }
    public String getCondValue() {
        return super.getAreaId().toString();
    }

//    @Override
//    public String toString() {
//        return getCondValue() + "@@" + getCondName();
//    }
    
    public DictAreaExt clone(int index){
        DictAreaExt show = new DictAreaExt();
        show.setCondName(this.getCondName());
        String[] arr = this.getLinkUrl().split("_");
        arr[index] = "";
        String url = StringUtils.join(arr,"_");
        if(url.indexOf("k______/") != -1){
            url = url.replace("k______/", "");
        }
        show.setLinkUrl(url);
        show.setCondValue(this.getCondValue());
        return show;
    }
}
