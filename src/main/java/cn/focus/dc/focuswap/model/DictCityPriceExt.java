package cn.focus.dc.focuswap.model;

import cn.focus.dc.building.model.DictCityPrice;

import org.apache.commons.lang.StringUtils;


public class DictCityPriceExt extends DictCityPrice {

    private static final long serialVersionUID = -3114697711132143181L;

    private String condName;

    private String condValue;
    
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
        return condName;
    }

    public void setCondName(String condName) {
        this.condName = condName;
    }

    public String getCondValue() {
        return condValue;
    }

    public void setCondValue(String condValue) {
        this.condValue = condValue;
    }

//    @Override
//    public String toString() {
//        return condValue + "@@" + condName;
//    }
    
    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }
    
    public DictCityPriceExt clone(int index){
        DictCityPriceExt show = new DictCityPriceExt();
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
