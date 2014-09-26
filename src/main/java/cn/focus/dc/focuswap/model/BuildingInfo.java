package cn.focus.dc.focuswap.model;

import cn.focus.dc.building.model.DictArea;
import cn.focus.dc.building.model.DictConstructType;
import cn.focus.dc.building.model.DictProjType;
import cn.focus.dc.building.model.vo.ProjInfoVO;
import cn.focus.dc.focuswap.config.AppConstants;
import cn.focus.dc.focuswap.utils.HtmlDigestUtil;
import cn.focus.dc.focuswap.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class BuildingInfo extends ProjInfoVO implements PhotoRealPathGenerable {

    private static final long serialVersionUID = -2744018290676244543L;
    private static Log logger = LogFactory.getLog(BuildingInfo.class);
    
    /**
     * 说明，重载这些是因为父类ProjInfo的get方法做过substring处理，而这里是不需要的，so...
     */
    private String priceForShow;
    
    private String projName;
    
    private String projType;
    
    //建筑类型
    private String constructType;
    
    //物业公司 
    private String operationCorp;
    //物业费 
    private String operationFee;
    
    //售楼处 
    private String saleLocate;
    
    //土地使用年限 
    private String soilUseYears;
    
    //推广语 
    private String spreadWords;
    
    //
    private String projSpecial;
    
    //微博地址 
    private String weiboUrl;
    
    private String url;
    
    private List<BuildingLayout> layoutsVO;
    
    private String logUrl;
    
    //properties vo
    private String projTypeDetail;
    
    private String decoLevelDetail;
    
    private String constructTypeDetail;
    
    private String areaDetail;
    
    public int getBuildingLayoutCount() {
        if(null!=layoutsVO) {
        return layoutsVO.size();
        } else {
            return 0;
        }
    }
     
    public String collateProjTypeDetail() {
        List<DictProjType> projTypes = super.getProjTypes();
        if (null == projTypes || projTypes.size() == 0) {
            return StringUtils.EMPTY;
        }
        
        int length = projTypes.size();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length -1; i++) {
            sb.append(projTypes.get(i).getTypeName()).append(" ");
        }
        sb.append(projTypes.get(length - 1).getTypeName());
        return sb.toString();
    }

    
    
    public String collateConstructTypeDetail() {
        List<DictConstructType> constructs = super.getConstructs();
        if (null == constructs || constructs.size() == 0) {
           return StringUtils.EMPTY; 
        }
        
        int length = constructs.size();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length - 1; i++) {
            sb.append(constructs.get(i).getTypeName()).append("  ");
        }
        sb.append(constructs.get(length - 1).getTypeName());
        
        return sb.toString();
    }


    public String collateDecoLevelDetail() {
        Integer decoLevel = super.getDecoLevel();
        String levelDetail = AppConstants.DecorateLevel.getLevelName(decoLevel);
        if (StringUtils.isEmpty(levelDetail)) {
            return "其它";
        }
        
        return levelDetail;
    }
    
    public String collateAreaDetail() {
        List<DictArea> areas = super.getAreas();
        if (null == areas || areas.size() == 0) {
            return StringUtils.EMPTY;
        }
        
        int length = areas.size();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < length -1; i++) {
            sb.append(areas.get(i).getAreaName()).append("  ");
        }
        sb.append(areas.get(length - 1).getAreaName());
        
        return sb.toString();
    }
 
  //collate some properties for showing in "buildingDetail" page
    public void collateForDetailPage() {
        this.projTypeDetail = collateProjTypeDetail();
        this.decoLevelDetail = collateDecoLevelDetail();
        this.constructTypeDetail = collateConstructTypeDetail();
        this.setPriceDesc(HtmlDigestUtil.html2Text(getPriceDesc()));
    }
    
    
    //collate some properties for showing in "buildingShowInfo" page
    public void collateFowShowPage() {
        this.projTypeDetail = this.collateProjTypeDetail();
        this.areaDetail = this.collateAreaDetail();
        this.setSaleDateDetail(Utils.subSaleDateStr(super.getSaleDateDetail()));
        this.setMoveInDateDetail(Utils.subSaleDateStr(super.getMoveInDateDetail()));
        this.setPriceDesc(HtmlDigestUtil.html2Text(getPriceDesc()));
        
        List<Map<String, Object>> layoutMaps = super.getLayouts();
        layoutsVO = new ArrayList<BuildingLayout>();
        
        for (Map<String, Object> map : layoutMaps) {
            BuildingLayout layout = new BuildingLayout();
            try {
                layout.setTypeId(map.get("typeId").toString());
                layout.setMinArea((Integer) map.get("minArea"));
                layout.setMaxArea((Integer) map.get("maxArea"));
                layoutsVO.add(layout);
            } catch (Exception e) {
                logger.error("", e);
            }
        }
        
        if (layoutsVO.size() == 0) {
            int minArea = 0;
            int maxArea = 0;
            try {
                minArea = Integer.valueOf(super.getAllLowArea());
                maxArea = Integer.valueOf(super.getAllMaxArea());
            } catch (NumberFormatException e) {
                logger.error("", e);
            }
            if (minArea > 0 || maxArea > 0) {
                BuildingLayout defaultLayout = new BuildingLayout();
                defaultLayout.setTypeId(AppConstants.Layout.getLayoutAllId());
                defaultLayout.setMinArea(minArea);
                defaultLayout.setMaxArea(maxArea);
                layoutsVO.add(defaultLayout);
            }
        }
    }



    public String getProjTypeDetail() {
        return projTypeDetail;
    }



    public String getDecoLevelDetail() {
        return decoLevelDetail;
    }



    public String getConstructTypeDetail() {
        return constructTypeDetail;
    }



    public String getProjName() {
        return projName;
    }



    public void setProjName(String projName) {
        this.projName = projName;
    }



    public String getProjType() {
        return projType;
    }



    public void setProjType(String projType) {
        this.projType = projType;
    }



    public String getConstructType() {
        return constructType;
    }



    public void setConstructType(String constructType) {
        this.constructType = constructType;
    }



    public String getOperationCorp() {
        return operationCorp;
    }



    public void setOperationCorp(String operationCorp) {
        this.operationCorp = operationCorp;
    }



    public String getOperationFee() {
        return operationFee;
    }



    public void setOperationFee(String operationFee) {
        this.operationFee = operationFee;
    }



    public String getSaleLocate() {
        return saleLocate;
    }



    public void setSaleLocate(String saleLocate) {
        this.saleLocate = saleLocate;
    }



    public String getSoilUseYears() {
        return soilUseYears;
    }



    public void setSoilUseYears(String soilUseYears) {
        this.soilUseYears = soilUseYears;
    }



    public String getSpreadWords() {
        return spreadWords;
    }



    public void setSpreadWords(String spreadWords) {
        this.spreadWords = spreadWords;
    }



    public String getProjSpecial() {
        return projSpecial;
    }



    public void setProjSpecial(String projSpecial) {
        this.projSpecial = projSpecial;
    }



    public String getWeiboUrl() {
        return weiboUrl;
    }



    public void setWeiboUrl(String weiboUrl) {
        this.weiboUrl = weiboUrl;
    }



    public void setProjTypeDetail(String projTypeDetail) {
        this.projTypeDetail = projTypeDetail;
    }



    public void setDecoLevelDetail(String decoLevelDetail) {
        this.decoLevelDetail = decoLevelDetail;
    }



    public void setConstructTypeDetail(String constructTypeDetail) {
        this.constructTypeDetail = constructTypeDetail;
    }

    public String getAreaDetail() {
        return areaDetail;
    }
    
    public void setAreaDetail(String areaDetail) {
        this.areaDetail = areaDetail;
    }



    public List<BuildingLayout> getLayoutsVO() {
        return layoutsVO;
    }

    public void setLayoutsVO(List<BuildingLayout> layoutsVO) {
        this.layoutsVO = layoutsVO;
    }

    @Override
    public void setRealPath(String realPath) {
        this.url = realPath;
    }

    @Override
    public String getPhotoAlias() {
        if(this.getProjLogo() != null){
            return this.getProjLogo().getPhotoAlias();            
        }else{
            return "";
        }
    }

    @Override
    public String getPath() {
        if(this.getProjLogo() != null){
            return this.getProjLogo().getPath();            
        }else{
            return "";
        }
    }

    @Override
    public Integer getPhotoWidth() {
        return 240;
    }
 
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }



    public String getLogUrl() {
        return logUrl;
    }



    public void setLogUrl(String logUrl) {
        this.logUrl = logUrl;
    }



    public String getPriceForShow() {
        return priceForShow;
    }



    public void setPriceForShow(String priceForShow) {
        this.priceForShow = priceForShow;
    }
}
