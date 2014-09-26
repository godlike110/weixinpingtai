package cn.focus.dc.focuswap.controllers.portal;

import cn.focus.dc.focuswap.model.EsProjInfoChild;
import cn.focus.dc.focuswap.model.Information;
import cn.focus.dc.focuswap.service.BuildingService;
import cn.focus.dc.focuswap.service.BuildingXinWenService;
import cn.focus.dc.focuswap.service.NewsService;
import cn.focus.dc.focuswap.service.SearchService;

import java.util.Iterator;
import java.util.List;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;


@Path("info")
public class RecentInfoController {
    
    @Autowired
    private NewsService newsService;
    
    @Autowired
    private BuildingXinWenService buildingXinWenService;
    
    @Autowired
    private SearchService searchService;
    
    @Autowired
    private BuildingService buildingService;
    
    /**
     * @desc  相关新闻的portal
     * @param inv
     * @param infoId
     * @param pageNo
     * @return
     */
    @Get("infos")
    public String getInfos(Invocation inv, @Param("infoId") int infoId,
            @Param("pageNo") int pageNo, @Param("cityName") String cityName) {
        List<Information> relate_news = null;
        try {
            relate_news = newsService.getRelateNews(infoId, pageNo);
            inv.addModel("relate_news", relate_news);
            inv.addModel("cityName", cityName);
            if(relate_news != null && relate_news.size() > 0){
                return "info/phone/infos";
            }else{
                return null;
            }
        } catch (Exception e) {
            return "";
        }
    }
    
    @Get("buildingList")
    public String getBuildingList(Invocation inv, @Param("limit") int limit,
            @Param("days") int days, @Param("cityName") String cityName) {
        List<Integer> groupIds = null;
        try {
        	
        	/**
        	 * @author linfangwang
        	 * @date 2014-8-5
        	 * 太原楼盘ID为490858的证件有问题，故limit为6
        	 */
        	if(StringUtils.isNotBlank(cityName) && cityName.equals("ty")) {
        		 groupIds = buildingXinWenService.getGroupIds(cityName, 6,7);
        		 
        		 if(groupIds != null && !groupIds.isEmpty()) {
        			 
        			 Iterator<Integer> iterator = groupIds.iterator();

        			 while (iterator.hasNext()) {
						Integer tmp = iterator.next();

						if(tmp.intValue() == 490858 ) {
							iterator.remove();
							break;
						}
					}
        			if(groupIds.size()>5) {
        				groupIds.remove(5);
        			}
        		 }
        	} else {
        		groupIds = buildingXinWenService.getGroupIds(cityName, 5,7);
        	}
        	
            List<EsProjInfoChild> buildingList = searchService.projGroupIdSearch(groupIds);
            if (buildingList != null) {
                for (EsProjInfoChild proj : buildingList) {
                    if(proj != null){
                        // 修改实体里面图片url
                        buildingService.genRealPhotoPath(proj, true);
                        
                        // 打折信息
                        
                        if ("待定".equals(proj.getDiscount().trim()) || "暂无".equals(proj.getDiscount().trim())
                                || "无".equals(proj.getDiscount().trim()) || "0".equals(proj.getDiscount().trim())) {
                            proj.setDiscount("");
                        }
                    }
                }
                buildingService.setBuildingListPrice(buildingList);
            }
            inv.addModel("buildingList", buildingList);
            inv.addModel("cityName", cityName);
            return "info/phone/buildingList";
        } catch (Exception e) {
        	e.printStackTrace();
            return "";
        }
    }
}
