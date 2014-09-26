package cn.focus.dc.focuswap.model;

import cn.focus.dc.building.model.DictTrafficLine;

import org.apache.commons.lang.StringUtils;


public class DictTrafficLineExt extends DictTrafficLine {

    private static final long serialVersionUID = 2409432258338638734L;

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
        return super.getLineName();
    }

    public void setCondName(String condName) {
        super.setLineName(condName);
    }

    public String getCondValue() {
        return super.getLineId().toString();
    }

    public void setCondValue(String condValue) {
        try{
            super.setLineId(Integer.valueOf(condValue));
        } catch (Exception e) {
            System.out.println("String [" + condValue + "] 转换失败");
        }
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
    
    public DictTrafficLineExt clone(int index){
        DictTrafficLineExt show = new DictTrafficLineExt();
        show.setCondName(this.getCondName());
        String arr[] = this.getLinkUrl().split("_");
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
