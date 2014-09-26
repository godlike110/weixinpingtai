package cn.focus.dc.focuswap.service;

import cn.focus.dc.building.model.ProjInfo;
import cn.focus.dc.focuswap.config.AppConstants;
import cn.focus.dc.focuswap.dao.RecommendHouseDAO;
import cn.focus.dc.focuswap.model.BuildingAroundInfo;
import cn.focus.dc.focuswap.model.BuildingInfo;
import cn.focus.dc.focuswap.model.BuildingLayoutPhotoes;
import cn.focus.dc.focuswap.model.EsProjInfoChild;
import cn.focus.dc.focuswap.model.PhotoRealPathGenerable;
import cn.focus.dc.focuswap.model.ProjPhotoesExt;
import cn.focus.dc.focuswap.model.RecommendHouse;
import cn.focus.dc.focuswap.utils.Utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static cn.focus.dc.focuswap.config.AppConstants.*;

/**
 * 具体某个楼盘相关的接口
 * 
 * @author rogantian
 */
@Service
public class BuildingService {

    private static Log logger = LogFactory.getLog(BuildingService.class);

    private static final Pattern PHOTO_PATH_REG = Pattern.compile("^(a_|b_)\\d+?.*");

    private static final Map<Integer, String> IMG_DOMAIN_MAP = new ConcurrentHashMap<Integer, String>();

    /**
     * 缩略图默认宽度
     */
    private static final int DEFAULT_THUMBNAIL_WIDTH = 120;   

    @Autowired
    private InterfaceService interfaceService;

    @Autowired
    private RecommendHouseDAO recommendHouseDao;

    /**
     * 获取楼盘详情
     * 
     * @param groupId
     * @return
     */
    public BuildingInfo getBuildingShowInfo(Integer groupId) {
        BuildingInfo buildingInfo = null;
        if (null != groupId && groupId > 0) {
            try {

                Map<String, Object> urlVariables = new HashMap<String, Object>();
                urlVariables.put(GROUP_ID, groupId);
                
                
                JSONObject jsonObject = interfaceService.getJSONFromInterface(DATACENTER_BUILDING_INFO_URL,
                        urlVariables);

                String data = extractJSONData(jsonObject);
                if (null != data) {
                    buildingInfo = JSON.parseObject(data, BuildingInfo.class);
                    buildingInfo = setBuildingPrice(buildingInfo);
                }
            } catch (Exception e) {
                logger.error("", e);
            }
        }

        return buildingInfo;
    }
    
    /**
     * 楼盘列表价格显示修改价格排序 价格优先级：楼盘均价>最低单价>最高代价>总价
     */
    public List<EsProjInfoChild> setBuildingListPrice(List<EsProjInfoChild> list) {
        for(EsProjInfoChild info:list) {
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
            String priceForShow = AppConstants.BuldingPrice.getPriceForShow(price, type);
            info.setAvgPriceShow(priceForShow);
        }
        return list;
    }
    
    /**
     * 地图楼盘价格列表排序  价格优先级：楼盘均价>最低单价>最高代价>总价
     */
    public List<EsProjInfoChild> setBuildingMapListPrice(List<EsProjInfoChild> list) {
        for(EsProjInfoChild info:list) {
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
            String priceForShow = AppConstants.BuldingPrice.getPriceForShowMap(price, type);
            info.setAvgPriceShow(priceForShow);
        }
        return list;
    }
    
    /**
     * 周边楼盘列表价格显示修改价格排序 价格优先级：楼盘均价>最低单价>最高代价>总价
     */
    public List<BuildingAroundInfo> setArroundBuildingListPrice(List<BuildingAroundInfo> list) {
        for(BuildingAroundInfo info:list) {
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
            String priceForShow = AppConstants.BuldingPrice.getPriceForShow(price, type);
            info.setAvgPriceShow(priceForShow);
        }
        return list;
    }
    
    /**
     * 楼盘详细信息价格排序 价格优先级：楼盘均价>最低单价>最高代价>总价
     * @param info
     * @return
     */
    public BuildingInfo setBuildingPrice(BuildingInfo info) {
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
        String priceForShow = AppConstants.BuldingPrice.getPriceForShow(price, type);
        info.setPriceForShow(priceForShow);
        return info;
    }

    /**
     * 获取楼盘详细信息
     * 
     * @param groupId
     * @return
     */
    public BuildingInfo getBuildingDetailInfo(Integer groupId) {
        BuildingInfo buildingInfo = null;
        if (null != groupId && groupId > 0) {
            try {
                Map<String, Object> urlVariables = new HashMap<String, Object>();
                urlVariables.put(GROUP_ID, groupId);
                JSONObject jsonObject = interfaceService.getJSONFromInterface(DATACENTER_BUILDING_DETAIL_URL,
                        urlVariables);

                String data = extractJSONData(jsonObject);
                if (null != data) {
                    buildingInfo = JSON.parseObject(data, BuildingInfo.class);
                }

            } catch (Exception e) {
                logger.error("", e);
            }
        }
        return buildingInfo;
    }

    /**
     * 获取某个楼盘的周边楼盘信息
     * 
     * @param groupId
     * @param limit 默认最多获取5条
     * @return
     */
    public List<BuildingAroundInfo> getAroundBuildings(Integer groupId, Integer limit) {
        List<BuildingAroundInfo> ret = Collections.emptyList();
        if (null == limit) {
            limit = 5;
        }
        if (null != groupId && groupId > 0) {
            try {
                Map<String, Object> urlVariables = new HashMap<String, Object>();
                urlVariables.put(GROUP_ID, groupId);
                urlVariables.put(LIMIT, limit);
                JSONObject jsonObject = interfaceService.getJSONFromInterface(DATACENTER_BUILDING_AROUND,
                        urlVariables);

                // String data = extractJSONData(jsonObject);

                if (null != jsonObject) {
                    Integer errorCode = jsonObject.getInteger(ERRORCODE);
                    if (null != errorCode && errorCode == 0) {
                        JSONObject data = jsonObject.getJSONObject(DATA);
                        if (null != data) {
                            String projs = data.getString("projList");
                            if (null != projs) {
                                ret = JSON.parseArray(projs, BuildingAroundInfo.class);
                            }
                        }
                    }
                }

            } catch (Exception e) {
                logger.error("", e);
            }
        }
        return ret;
    }

    /**
     * 生成周边楼盘的logo路径
     * 
     * @param infos
     */
    public void decorateAroundBuilding(List<BuildingAroundInfo> infos) {
        if (null == infos) {
            return;
        }

        for (BuildingAroundInfo info : infos) {
            this.genRealPhotoPath(info, true);
        }
    }

    /**
     * 获取楼盘户型图
     * 
     * @param groupId
     * @return
     */
    public List<BuildingLayoutPhotoes> getBuildingPicInfo(Integer groupId) {
        List<BuildingLayoutPhotoes> buildingLayoutPhotoes = Collections.emptyList();
        if (null != groupId && groupId > 0) {
            try {
                Map<String, Object> urlVariables = new HashMap<String, Object>();
                urlVariables.put(GROUP_ID, groupId);
                JSONObject jsonObject = interfaceService.getJSONFromInterface(
                        DATACENTER_BUILDING_LAYOUT_PIC__URL, urlVariables);

                String data = extractJSONData(jsonObject);
                if (null != data) {
                    buildingLayoutPhotoes = JSON.parseArray(data, BuildingLayoutPhotoes.class);
                }
            } catch (Exception e) {
                logger.error("", e);
            }
        }
        return buildingLayoutPhotoes;
    }

    /**
     * 获取其他类型图并且格式化
     * 
     * @param groupId
     * @return
     */
    public List<BuildingLayoutPhotoes> getBuildingPicWithFormat(int groupId, boolean flag, int width) {
        List<BuildingLayoutPhotoes> buildingPhotos = Collections.emptyList();

        List<ProjPhotoesExt> projPhotos = getBuildingPic(groupId);
        buildingPhotos = formatPhotoData(projPhotos, flag, width);
        return buildingPhotos;
        /*
         * List<ProjPhotoesExt> projPhotos = Collections.emptyList(); if (groupId > 0) { Map<String, Object> params =
         * new HashMap<String, Object>(); params.put(GROUP_ID, groupId); params.put(PAGE_NO, "100"); JSONObject object =
         * interfaceService.getJSONFromInterface(DATACENTER_BUILDING_PIC_URL, params); String data =
         * extractJSONData(object); if (null != data) { projPhotos = JSON.parseArray(data, ProjPhotoesExt.class);
         * buildingPhotos = formatPhotoData(projPhotos,flag,width); } }
         */
    }

    /**
     * 获取其他类型图
     * 
     * @param groupId
     * @return
     */
    public List<ProjPhotoesExt> getBuildingPic(int groupId) {
        List<ProjPhotoesExt> projPhotos = Collections.emptyList();
        if (groupId > 0) {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put(GROUP_ID, groupId);
            JSONObject object = interfaceService.getJSONFromInterface(DATACENTER_BUILDING_PIC_URL, params);
            String data = extractJSONData(object);
            if (null != data) {
                projPhotos = JSON.parseArray(data, ProjPhotoesExt.class);
            }
        }
        for(int i=0;i<projPhotos.size();i++) {
            int albumId= projPhotos.get(i).getAlbumId();
            //-20是搜狗VR图  做无用处理 直接删除
            if(-20==albumId) {
                projPhotos.remove(i);
            }
        }
        return projPhotos;
    }

    /**
     * 获取楼盘的主编推荐语（来自新房CMS）本来应该用groupId作为查询条件的，但是由于新房数据库的recommend_house表以cityId和projId做为 联合主键，所以这里先暂时用cityId和projId来查询
     * 
     * @param cityId
     * @param projId
     * @return
     */
    public String getBuildingSpreadWords(int cityId, int projId) {
        RecommendHouse recommendHouse = recommendHouseDao.getRecommendHouseReason(cityId, projId);
        if (null != recommendHouse) {
            return recommendHouse.getReason();
        } else {
            return StringUtils.EMPTY;
        }
    }

    /**
     * @param groupIds 以逗号分隔的多个groupId
     * @return
     */
    public List<BuildingInfo> getBuildingInfoList(String groupIds) {
        if (StringUtils.isBlank(groupIds)) {
            return Collections.emptyList();
        }

        try {
            Map<String, Object> urlVariables = new HashMap<String, Object>();
            urlVariables.put("groupIds", groupIds);
            JSONObject jsonObject = interfaceService.getJSONFromInterface(
                    DATACENTER_BUILDING_LIST_BY_IDS_URL, urlVariables);
            //logger.info(new StringBuilder("根据groupIds[").append(groupIds).append("]获取楼盘信息返回值为：" + jsonObject));

            if (null != jsonObject) {
                Integer errorCode = jsonObject.getInteger(ERRORCODE);
                if (null != errorCode && errorCode == 0) {
                    String buildingArray = jsonObject.getString(DATA);
                    return JSON.parseArray(buildingArray, BuildingInfo.class);
                }
            }

        } catch (Exception e) {
            logger.error("", e);
        }
        return Collections.emptyList();
    }

    /**
     * @param projPhotos
     * @param flag
     * @param width flag=false时 width 任意值
     * @return
     */
    public List<BuildingLayoutPhotoes> formatPhotoData(List<ProjPhotoesExt> projPhotos, boolean flag,
            int width) {
        List<BuildingLayoutPhotoes> buildingPhotos = new ArrayList<BuildingLayoutPhotoes>();
        Map<String, BuildingLayoutPhotoes> photoMap = new HashMap<String, BuildingLayoutPhotoes>();
        String key = "";
        int count = 0;
        for (ProjPhotoesExt photo : projPhotos) {
            key = photo.getAlbumId().toString();
                List<ProjPhotoesExt> list = new ArrayList<ProjPhotoesExt>();
                BuildingLayoutPhotoes buildingPhoto = new BuildingLayoutPhotoes();
                if (flag){
                    photo.setPhotoWidth(width);
                }
                genRealPhotoPath(photo, flag);
                if (null == photoMap.get(key)) {
                    list.add(photo);
                    buildingPhoto.setType(photo.getAlbumId());
                    buildingPhoto.setCount(1);
                    buildingPhoto.setPhotoes(list);
                    photoMap.put(key, buildingPhoto);
                } else {
                    buildingPhoto = photoMap.get(key);
                    list = buildingPhoto.getPhotoes();
                    count = buildingPhoto.getCount();
                    list.add(photo);
                    buildingPhoto.setPhotoes(list);
                    buildingPhoto.setCount(++count);
                }
        }
        if (photoMap.size() > 0) {
            Set<String> enty = photoMap.keySet();
            for (String keyS : enty) {
                BuildingLayoutPhotoes a = photoMap.get(keyS);
                buildingPhotos.add(a);
            }
        }

        return buildingPhotos;
    }

    public void decorateBuildingPics(List<ProjPhotoesExt> photoes, boolean flag, int width, boolean needCut) {
        if (null != photoes && photoes.size() > 0) {
            for (ProjPhotoesExt photo : photoes) {
                if (flag){
                    photo.setPhotoWidth(width);
                }
                if (needCut && photo.getTitle()!=null && photo.getTitle().length() > 15){
                    String title = photo.getTitle();
                    title = title.substring(0, 13) + "...";
                    photo.setTitle(title);
                } 
                if(null == photo.getTitle()) {
                    photo.setTitle("暂无数据");
                }
                genRealPhotoPath(photo, flag);
            }
        }
    }

    /**
     * 根据groupId获取楼盘实体
     * 
     * @param groupId
     * @return
     */
    public BuildingInfo getBuilding(Integer groupId) {
        BuildingInfo info = this.getBuildingShowInfo(groupId);
        return info;
    }

    /**
     * 根据cityId和projId获取楼盘名称
     * 
     * @param cityId
     * @param buildId
     * @return
     */
    public String getBuildingName(int cityId, int buildId) {
        String ret = null;
        if (cityId > 0 && buildId > 0) {
            try {
                Map<String, Object> variables = new HashMap<String, Object>();
                variables.put("cityId", cityId);
                variables.put("projId", buildId);
                JSONObject jsonObject = interfaceService.getJSONFromInterface(
                        DATACENTER_BUILDING_BASE_QUERY_URL, variables);
                String data = extractJSONData(jsonObject);
                if (StringUtils.isNotBlank(data)) {
                    JSONObject jsonData = JSON.parseObject(data);
                    ret = jsonData.getString("projName");
                }
            } catch (Exception e) {
                logger.error("", e);
            }
        }
        return ret;
    }

    /**
     * 根据cityId和projId获取楼盘对象
     * 
     * @param cityId
     * @param buildId
     * @return
     */
    public ProjInfo getBuilding(int cityId, int buildId) {
        ProjInfo projInfo = null;
        if (cityId > 0 && buildId > 0) {
            try {
                Map<String, Object> variables = new HashMap<String, Object>();
                variables.put("cityId", cityId);
                variables.put("projId", buildId);
                JSONObject jsonObject = interfaceService.getJSONFromInterface(
                        DATACENTER_BUILDING_BASE_QUERY_URL, variables);
                String data = extractJSONData(jsonObject);
                if (StringUtils.isNotBlank(data)) {
                    projInfo = JSON.parseObject(data, ProjInfo.class);
                }
            } catch (Exception e) {
                logger.error("", e);
            }
        }
        return projInfo;
    }

    /**
     * 图片路径规则： 原图：如果图片名以a_或b_加数字打头,"imgs.focus.cn", "i1.f.itc.cn", "i2.f.itc.cn"三个域名随机取一个， 图片地址 =
     * 域名+"/upload"+photo_path+photo_alias 否则就以图片所在站点的图片域名（如：bjimg.focus.cn）+photo_path+photo_alias
     * 缩略图：如果图片名以a_或b_加数字打头,"imgs.focus.cn", "i1.f.itc.cn", "i2.f.itc.cn"三个域名随机取一个，图片地址 =
     * 域名+"/thumb/"+photo_width+photo_path+photo_alias
     * 当图片宽度为120时，地址为图片域名（如：bjimg.focus.cn）+photo_path+"tphoto/"+photo_alias
     * 否则地址为图片域名（如：bjimg.focus.cn）+"/thumbnail/"+photo_width+photo_path+photo_alias PS:双引号内为字符串。
     * 缩略图的尺寸也有限制，目前支持120,150,180,240 需要生成真实路径的图片必须实现PhotoRealPathGenerable接口(该接口定义了一系列要生成路径所必须的函数）
     * 
     * @param photo
     * @param thumbnail 是否是缩略图
     */
    public void genRealPhotoPath(PhotoRealPathGenerable photo, boolean thumbnail) {
        try {
            if(photo != null){
                String name = photo.getPhotoAlias();
                if(StringUtils.isNotBlank(name)){
                    String path = photo.getPath();
                    Integer width = photo.getPhotoWidth();
                    boolean specialName = true;
                    specialName = PHOTO_PATH_REG.matcher(name).matches();
                    StringBuilder sb = new StringBuilder("http://");
                    if (specialName) {
                        sb.append(Utils.rondomService());
                        if (thumbnail) {
                            sb.append("/thumb/").append(width);
                        } else {
                            sb.append("/upload");
                        }
                        sb.append(path).append(name);
                    } else {
                        sb.append(getImgDomain(photo.getCityId()));
                        if (thumbnail) {
                            if (DEFAULT_THUMBNAIL_WIDTH == width) {
                                sb.append(path).append("tphoto/");
                            } else {
                                sb.append("/thumbnail/").append(width).append(path);
                            }
                        } else {
                            sb.append(path);
                        }
                        sb.append(name);
                    }
                    photo.setRealPath(sb.toString());
                }
            }
        } catch (Exception e) {
            logger.error("", e);
        }
    }
    
    
    /**
     * 周边楼盘log url获取
     * 
     * @param photo
     * @param thumbnail
     * @return
     */
    public void getBuildingArroundLog(BuildingAroundInfo info, boolean thumbnail) {
        try {
            if (null == info.getProjLogo()) {
                info.setLogUrl("");
            } else {
                String name = info.getProjLogo().getPhotoAlias();
                String path = info.getProjLogo().getPath();
                Integer width = info.getProjLogo().getPhotoWidth();
                boolean specialName = true;
                specialName = PHOTO_PATH_REG.matcher(name).matches();
                StringBuilder sb = new StringBuilder("http://");
                if (specialName) {
                    sb.append(Utils.rondomService());
                    if (thumbnail) {
                        sb.append("/thumb/").append(width);
                    } else {
                        sb.append("/upload");
                    }
                    sb.append(path).append(name);
                } else {
                    sb.append(getImgDomain(info.getCityId()));
                    if (thumbnail) {
                        if (DEFAULT_THUMBNAIL_WIDTH == width) {
                            sb.append(path).append("tphoto/");
                        } else {
                            sb.append("/thumbnail").append(width).append(path);
                        }
                    } else {
                        sb.append(path);
                    }
                    sb.append(name);
                }
                info.setLogUrl(sb.toString());
            }
        } catch (Exception e) {
            logger.error("", e);
            info.setLogUrl("");
        }
    }

    /**
     * 楼盘log url获取
     * 
     * @param photo
     * @param thumbnail
     * @return
     */
    public void genBuildingLog(BuildingInfo info, boolean thumbnail) {
        try {
            if (null == info.getProjLogo()) {
                info.setLogUrl("");
            } else {
                String name = info.getProjLogo().getPhotoAlias();
                String path = info.getProjLogo().getPath();
                Integer width = info.getProjLogo().getPhotoWidth();
                boolean specialName = true;
                specialName = PHOTO_PATH_REG.matcher(name).matches();
                StringBuilder sb = new StringBuilder("http://");
                if (specialName) {
                    sb.append(Utils.rondomService());
                    if (thumbnail) {
                        sb.append("/thumb/").append(width);
                    } else {
                        sb.append("/upload");
                    }
                    sb.append(path).append(name);
                } else {
                    sb.append(getImgDomain(info.getCityId()));
                    if (thumbnail) {
                        if (DEFAULT_THUMBNAIL_WIDTH == width) {
                            sb.append(path).append("tphoto/");
                        } else {
                            sb.append("/thumbnail").append(width).append(path);
                        }
                    } else {
                        sb.append(path);
                    }
                    sb.append(name);
                }
                info.setLogUrl(sb.toString());
            }
        } catch (Exception e) {
            logger.error("", e);
            info.setLogUrl("");
        }
    }
    
    

    /**
     * 获取站点的图片服务域名
     * 
     * @param cityId
     * @return
     */
    public String getImgDomain(int cityId) {
        String domain = IMG_DOMAIN_MAP.get(cityId);
        if (null == domain) {
            domain = getImgDomainFromInterface(cityId);
            if (null != domain) {
                IMG_DOMAIN_MAP.put(cityId, domain);
            }
        }
        return domain;
    }

    private String getImgDomainFromInterface(int cityId) {
        String domain = IMG_DOMAIN_MAP.get(cityId);
        if (null != domain) {
            return domain;
        }
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("cityIds", cityId);
            JSONObject jsonObject = interfaceService.getJSONFromInterface(DATACENTER_BUILDING_CITY_URL,
                    params);
            List<cn.focus.dc.building.model.DictCity> cities = null;

            String data = extractJSONData(jsonObject);
            if (null != data) {
                cities = JSON.parseArray(data, cn.focus.dc.building.model.DictCity.class);
            }
            if (null != cities && cities.size() > 0) {
                domain = cities.get(0).getImgHost();
            }
        } catch (Exception e) {
            logger.error("", e);
        }
        return domain;
    }

    /**
     * 从标准的接口返回值中提取所需数据
     * 
     * @param jsonObject
     * @return
     */
    private String extractJSONData(JSONObject jsonObject) {
        if (null != jsonObject) {
            Integer errorCode = jsonObject.getInteger(ERRORCODE);
            if (null != errorCode && errorCode == 0) {
                return jsonObject.getString(DATA);
            }
        }
        return null;
    }
}
