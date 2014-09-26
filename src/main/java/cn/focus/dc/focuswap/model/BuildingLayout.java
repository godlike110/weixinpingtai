package cn.focus.dc.focuswap.model;

import cn.focus.dc.commons.model.BaseObject;
import cn.focus.dc.focuswap.config.AppConstants;

import org.apache.commons.lang.StringUtils;

public class BuildingLayout extends BaseObject {
    
    private static final long serialVersionUID = -2547310628677456750L;

    /**
     * 户型编号
     */
    private String typeId;
    
    /**
     * 最小面积
     */
    private int minArea;
    
    /**
     * 最大面积
     */
    private int maxArea;
    
    public String genDesc() {
        return "";
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public int getMinArea() {
        return minArea;
    }

    public void setMinArea(int minArea) {
        this.minArea = minArea;
    }

    public int getMaxArea() {
        return maxArea;
    }

    public void setMaxArea(int maxArea) {
        this.maxArea = maxArea;
    }

    public String getLayoutDesc() {
        StringBuilder sb = new StringBuilder();
        String layoutName = AppConstants.Layout.getLayoutName(typeId);
        if (StringUtils.isEmpty(layoutName)) {
            sb.append("其它&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
        } else {
            sb.append(layoutName).append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
        }
        
        if (minArea > 0) {
            sb.append(minArea).append("平米");
        }
        
        if (maxArea > 0 && maxArea > minArea) {
            if (minArea > 0) {
                sb.append("  -  ");
            } 
            sb.append(maxArea).append("平米");
        }
        return sb.toString();
    }
    
    
}
