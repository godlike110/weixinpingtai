package cn.focus.dc.focuswap.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.focus.dc.building.model.es.BasePic;
import cn.focus.dc.focuswap.config.AppConstants;
import cn.focus.dc.focuswap.model.DictCity;
import cn.focus.dc.focuswap.model.EsProjInfoChild;
import cn.focus.dc.focuswap.model.SearchCondition;
import cn.focus.dc.focuswap.model.TuanGouDetail;
import cn.focus.dc.focuswap.service.XinFangCommonApiService.Pafangtuan;
import cn.focus.dc.focuswap.service.XinFangCommonApiService.PftBuildModel;
import cn.focus.dc.focuswap.utils.Utils;

import java.io.Serializable;
import java.text.MessageFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KuaiZhanCopService {

    @Autowired
    private SearchService searchService;
    
    @Autowired
    private CityService cityService;
    
    @Autowired
    private PaFangTuanService pftService;
    
    @Autowired
    private TuanGouService tgService;
    
    @Autowired
    private BuildingService buildingService;
    
    private static final Pattern PHOTO_PATH_REG = Pattern.compile("^(a_|b_)\\d+?.*");
    
    private static Log logger  = LogFactory.getLog(KuaiZhanCopService.class);
    
    private static String PHONE400_PREFIX = "4008882200,";
    
    private static String PHONE400_PREFIX2 = "4006782020";
    
    private static enum SaleStatus2 {
        在售,
        //ONSALE,
        待售,
        //ONWAIT,
        尾盘,
        //ONREMAIN,
        售罄
        //ONOVER
    }
    
    
    @SuppressWarnings("unchecked")
    public Map<String, Object> getBasicLouPanDatasByPage(int cityId, int pageNo, int pageSize) {
        
        Map<String, Object> ret = new HashMap<String, Object>();
        
        SearchCondition condition = new SearchCondition();
        condition.setCityId(cityId);
        condition.setPageNo(pageNo);
        condition.setPageSize(pageSize);
        
        Map<String, Object> objectMap  = searchService.projSearch(condition);
        
        if (null == objectMap) {
            return ret;
        }
        
        DictCity city = cityService.getCity(cityId);
        String cityName = city.getCityName();
        Map<Integer, Integer> pftMap = getPftMap(cityId);
        Map<Integer, Integer> tgMap = getTgMap(cityId);
        
        MessageFormat appItrFormatter = new MessageFormat("{0}位于{1}{2}，交通便利，配套完善，布局合理，自住投资两相宜");
        
        ret.put(AppConstants.HASNEXT, objectMap.get("hasNext"));
        ret.put("total", objectMap.get("total"));
        List<EsProjInfoChild> projList = (List<EsProjInfoChild>) objectMap.get("buildingList");
        
        List<KuaiZhanCopService.BasicLouPanData> dataList = new LinkedList<KuaiZhanCopService.BasicLouPanData>();
        for (EsProjInfoChild proj : projList) {
            KuaiZhanCopService.BasicLouPanData loupan = new BasicLouPanData();
            
            loupan.setGroupId(proj.getGroupId());
            
            String projName = proj.getProjName();
            loupan.setName(projName);
            
            String processedProjName = processAppName(projName);
            if (StringUtils.isNotBlank(processedProjName) && processedProjName.length() > 15) {
                loupan.setAppName(processedProjName.substring(0, 15));
            } else {
                loupan.setAppName(processedProjName);
            }
            
            if (StringUtils.isNotBlank(processedProjName) && processedProjName.length() > 9) {
                loupan.setApplyWords(processedProjName.substring(0, 9));
            } else {
                loupan.setApplyWords(processedProjName);
            }
            
            loupan.setSaleStatus(SaleStatus2.values()[proj.getSaleStatus()].name());
            
            loupan.setPrice(getBuildingPrice(proj));
            
            loupan.setAddress(cityName + proj.getDistrictName());
            
            loupan.setSaleTime(Utils.subSaleDateStr(proj.getSaleDateDetail()));
            
            loupan.setHeadPicUrl(getBuildingLog(proj));
            
            Integer phone400 = proj.getPhone400();
            
            if (null != phone400 && phone400 != 0) {
                loupan.setPhone400(PHONE400_PREFIX + phone400);
            } else {
                loupan.setPhone400(PHONE400_PREFIX2);
            }
            
            Integer pftId = pftMap.get(proj.getGroupId());
            if (null == pftId) {
                pftId = 0;
            }
            loupan.setKftId(pftId);
            
            Integer tgId = tgMap.get(proj.getGroupId());
            if (null == tgId) {
                tgId = 0;
            }
            loupan.setTgId(tgId);
            
            loupan.setCityAbbr(city.getCityPinyinAbbr());
            
            loupan.setCityName(cityName);
            
            loupan.setAppItr(appItrFormatter.format(new String[]{projName, cityName, proj.getProjAddress()}));
            
            dataList.add(loupan);
        }
        
        ret.put(AppConstants.DATA, dataList);
        
        return ret;
    }
    
    /**
     * 直达号名称不能有空格
     * @param projName
     * @return
     */
    private String processAppName(String projName) {
        String ret = projName.replaceAll("\\(.*?\\)", StringUtils.EMPTY);
        ret = ret.replaceAll("\\（.*?\\）", StringUtils.EMPTY);
        return ret.replaceAll("\\p{P}", StringUtils.EMPTY);
    }
    
    public static void main(String[] args) {
        String r = "红杉公馆（靓景明居）";
        System.out.println(r.replaceAll("\\(.*?\\)", StringUtils.EMPTY));
        System.out.println(r.replaceAll("\\（.*?\\）", StringUtils.EMPTY));
        System.out.println(r.replaceAll("\\p{P}", ""));
    }
    
    /**
     * 获取某一城市下所有的团购中的groupId和tgId的映射关系，如果同意个groupId对应多个tgId，则获取优先级最高那个
     * @param cityId
     * @return
     */
    @SuppressWarnings("unchecked")
    private Map<Integer, Integer> getTgMap(int cityId) {
        
        Map<Integer, Integer> ret = new HashMap<Integer, Integer>();
        
        try {
            JSONObject jsonList = tgService.getTuanGouList(cityId, 1, 200);
            String listData = jsonList.getString("data");
            if (StringUtils.isNotBlank(listData)) {
                List<TuanGouDetail> tgList = JSON.toJavaObject(jsonList.getJSONArray("data"), List.class);
                if (null == tgList || tgList.size() == 0) {
                    return ret;
                }
                
                for (TuanGouDetail tg : tgList) {
                    int groupId = tg.getGroup_id();
                    if (ret.containsKey(groupId)){ 
                        continue;
                    }
                    ret.put(tg.getGroup_id(), tg.getActive_id());
                }
            }
        } catch (ParseException e) {
            logger.error("", e);
        }
        
        return ret;
                
    }
    
    /**
     * 获取某城市下所有的爬房团中的groupId和pftId的映射关系，如果同一个groupId对应多个pftId的话则获取时间上最接近的这个
     * @param cityId
     * @return
     */
    @SuppressWarnings("unchecked")
    private Map<Integer, Integer> getPftMap(int cityId) {
        
        Map<Integer, Integer> ret = new HashMap<Integer, Integer>();
        
        Map<String, Object> objectMap = pftService.getPafangtuanList(cityId, 1, 100);
        List<Pafangtuan> pftList = (List<Pafangtuan>) objectMap.get(PaFangTuanService.DATA_LIST);
        
        if (null != pftList && pftList.size() > 0) {
            for (Pafangtuan pft : pftList) {
                Integer pftId = pft.getLineId();
                List<PftBuildModel> builds = pft.getBuilds();
                
                if (null == builds || builds.size() == 0) {
                    continue;
                }
                
                for (PftBuildModel build : builds) {
                    Integer groupId = build.getGroupId();
                    if (ret.containsKey(groupId)) {
                        continue;
                    }
                    
                    ret.put(groupId, pftId);
                }
            }
        }
        
        return ret;
    }
    
    
    
    /**
     * 获取楼盘价格描述得字符串
     * @param info
     * @return
     */
    private String getBuildingPrice(EsProjInfoChild info) {
        int type = -2;
        int price = 0;
        if(info.getAvgPrice()!=null && info.getAvgPrice()>0) {
            type = AppConstants.BuldingPrice.AVGPRICE;
            price = info.getAvgPrice();
        } else {
            if(info.getLowPrice()!=null && info.getLowPrice()>0) {
                type = AppConstants.BuldingPrice.MINPRICE;
                price = info.getLowPrice();
            } else {
                if(info.getMaxPrice()!=null && info.getMaxPrice()>0) {
                    type = AppConstants.BuldingPrice.MAXPRICE;
                    price = info.getMaxPrice();
                } else {
                    if(info.getAllAvgPrice()!=null && info.getAllAvgPrice()>0) {
                        type = AppConstants.BuldingPrice.ALLPRICE;
                        price = info.getAllAvgPrice();
                    }
                }
            }
        }
        
        switch (type) {
        case 1:
            return "均价" + price + "元/平米";
        case 2:
            return "最低" + price + "元/平米";
        case 3:
            return "最高" + price + "元/平米";
        case 4:
            return "总价" + price + "万/套";
        case -2:
            return "价格待定";
        default:
            return "参考价格:待定";
        }
    }
    
    /**
     * 获取楼盘头图地址
     * @param info
     * @param thumbnail
     */
    private String getBuildingLog(EsProjInfoChild info) {
        BasePic basePic = info.getBasePic();
        try {
            String name = basePic.getPhotoAlias();
            String path = basePic.getPath();
            if (StringUtils.isBlank(name)) {
                return StringUtils.EMPTY;
            } else {
//                String name = info.getProjLogo().getPhotoAlias();
//                String path = info.getProjLogo().getPath();
//                Integer width = info.getProjLogo().getPhotoWidth();
                boolean specialName = true;
                specialName = PHOTO_PATH_REG.matcher(name).matches();
                StringBuilder sb = new StringBuilder("http://");
                if (specialName) {
                    sb.append(Utils.rondomService());
                        sb.append("/upload");
                    sb.append(path).append(name);
                } else {
                    sb.append(buildingService.getImgDomain(info.getCityId()));
                        sb.append(path);
                    sb.append(name);
                }
                return sb.toString();
            }
        } catch (Exception e) {
            logger.error("", e);
        }
        
        return StringUtils.EMPTY;
    }
    
    
    
        public static class BasicLouPanData implements Serializable {
        
        private static final long serialVersionUID = 1016394550928417697L;
        
        private Integer groupId;
        
        private String headPicUrl;
        
        private String phone400;
        
        private String name;
        
        private String saleStatus;
        
        private String price;
        
        private String address;
        
        private String saleTime;
        
        private String cityName;
        
        private String cityAbbr;
        
        private Integer kftId;
        
        private Integer tgId;
        
        private String appItr;//应用简介
        
        private String appName;//应用名称
        
        private String applyWords;//申请关键字
        
        
        
        public Integer getGroupId() {
            return groupId;
        }
        
        public void setGroupId(Integer groupId) {
            this.groupId = groupId;
        }
        
        public String getHeadPicUrl() {
            return headPicUrl;
        }
        
        public void setHeadPicUrl(String headPicUrl) {
            this.headPicUrl = headPicUrl;
        }
        
        public String getName() {
            return name;
        }
        
        public void setName(String name) {
            this.name = name;
        }
        
        public String getSaleStatus() {
            return saleStatus;
        }
        
        public void setSaleStatus(String saleStatus) {
            this.saleStatus = saleStatus;
        }
        
        public String getPrice() {
            return price;
        }
        
        public void setPrice(String price) {
            this.price = price;
        }
        
        public String getSaleTime() {
            return saleTime;
        }
        
        public void setSaleTime(String saleTime) {
            this.saleTime = saleTime;
        }
        
        public String getCityName() {
            return cityName;
        }
        
        public void setCityName(String cityName) {
            this.cityName = cityName;
        }
        
        public String getCityAbbr() {
            return cityAbbr;
        }
        
        public void setCityAbbr(String cityAbbr) {
            this.cityAbbr = cityAbbr;
        }
        
        public Integer getKftId() {
            return kftId;
        }
        
        public void setKftId(Integer kftId) {
            this.kftId = kftId;
        }
        
        public Integer getTgId() {
            return tgId;
        }
        
        public void setTgId(Integer tgId) {
            this.tgId = tgId;
        }

        public String getAppItr() {
            return appItr;
        }

        public void setAppItr(String appItr) {
            this.appItr = appItr;
        }

        public String getAppName() {
            return appName;
        }

        public void setAppName(String appName) {
            this.appName = appName;
        }

        public String getApplyWords() {
            return applyWords;
        }

        public void setApplyWords(String applyWords) {
            this.applyWords = applyWords;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPhone400() {
            return phone400;
        }

        public void setPhone400(String phone400) {
            this.phone400 = phone400;
        }
        
        
    }
}

    
