package cn.focus.dc.focuswap.model;

import cn.focus.dc.building.model.DictProjType;

import org.apache.commons.lang.StringUtils;


public class DictProjTypeExt extends DictProjType {

    private static final long serialVersionUID = -18107407000702451L;

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
        return super.getTypeName();
    }

    public void setCondName(String condName) {
        super.setTypeName(condName);
    }

    public String getCondValue() {
        return super.getTypeId().toString();
    }

    public void setCondValue(String condValue) {
        super.setTypeId(Integer.valueOf(condValue));
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
    
    public DictProjTypeExt clone(int index){
        DictProjTypeExt show = new DictProjTypeExt();
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
