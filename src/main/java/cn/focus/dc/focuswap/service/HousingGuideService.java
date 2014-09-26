/**
 * 
 */
package cn.focus.dc.focuswap.service;

import cn.focus.dc.focuswap.dao.HousingGuideDAO;
import cn.focus.dc.focuswap.model.HousingGuide;
import cn.focus.dc.focuswap.model.HousingGuideCatagory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @author yunguangwang
 * @2013-8-27 @下午5:00:51
 */
@Component
public class HousingGuideService {

    @Autowired
    private HousingGuideDAO housingGuideDAO;

    public HousingGuide getHousingGuide(int itemId) {
        HousingGuide guide = housingGuideDAO.get(itemId);
        if (guide != null) {
            HousingGuideCatagory hGuideCatagory = housingGuideDAO.getGuideCatagoryById(guide.getCatagoryId());
            if (hGuideCatagory != null) {
                guide.setCatagoryName(hGuideCatagory.getName());
            }
        }
        return guide;

    }

    public List<HousingGuide> getHousingGuideListByCatagoryId(int start,
            int size, int catagoryId) {
        List<HousingGuide> housingGuideList = housingGuideDAO.getListByCatagoryId(start, size,
                catagoryId);
        return housingGuideList;
    }

    public List<HousingGuide> getHousingGuideList(int start, int size) {
        List<HousingGuide> housingGuideList = new ArrayList<HousingGuide>();
        List<HousingGuide> guideList = new ArrayList<HousingGuide>();
        housingGuideList = housingGuideDAO.getList(start, size);
        Map<Integer,String> catagory = new HashMap<Integer,String>();
        if (housingGuideList != null) {
            for (HousingGuide housingGuide : housingGuideList) {                              
                    String name = catagory.get(housingGuide.getCatagoryId());
                    if(StringUtils.isNotBlank(name)){
                        housingGuide.setCatagoryName(name);
                    }else{
                        HousingGuideCatagory housingGuideCatagory = housingGuideDAO.getGuideCatagoryById(housingGuide
                                .getCatagoryId());
                        if(housingGuideCatagory != null){
                            housingGuide.setCatagoryName(housingGuideCatagory.getName());
                            catagory.put(housingGuide
                                .getCatagoryId(), housingGuideCatagory.getName());
                        }
                    }                
                guideList.add(housingGuide);
            }
        }
        return guideList;
    }
}
