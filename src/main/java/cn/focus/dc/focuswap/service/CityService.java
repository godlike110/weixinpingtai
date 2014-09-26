package cn.focus.dc.focuswap.service;

import cn.focus.dc.focuswap.config.AppConstants;
import cn.focus.dc.focuswap.dao.DictCityDAO;
import cn.focus.dc.focuswap.model.DictCity;
import cn.focus.dc.focuswap.utils.CookieUtil;
import cn.focus.dc.focuswap.utils.FactoryUtil;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import net.paoding.rose.web.Invocation;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CityService {

    // 记录用户城市选择cookie的名字
    public static final String CITY_SELECTED_COOKIE_KEY = "focus_wap_city";
    
    // 记录用户城市选择cookie的名字，但是作用域是在.focus.cn
    public static final String CITY_SELECT_COOKIE_KEY_AT_PINGE = "focus_wap_city_p";

    // 记录用户城市选择cookie的过期时间(秒)
    public static final int CITY_SELECTED_COOKIE_EXPIRE = 3600 * 24 * 90;
    
    private static Log logger = LogFactory.getLog(CityService.class);

    //城市信息缓存
    @Autowired
    private CacheHandlerService cacheHandler;
    
    
    @Autowired
    private DictCityDAO dictCityDao;
    
    @Autowired
    private InterfaceService interfaceService;

    public DictCity getCity(int cityId) {
        if (cityId == AppConstants.CITYID_PY || cityId == AppConstants.CITYID_SY) {
            DictCity py = FactoryUtil.getFactory().getCityById(cityId);
            return py;
        }
        return dictCityDao.get(cityId);
    }

    public DictCity getCity(String domainName) {
    	 DictCity city = null;
    	 
    	 if(StringUtils.isNotBlank(domainName)) {
    		 city = dictCityDao.getCityByDomainname(domainName);
    	 }
    	 return city;
    }
    public DictCity getCityWithDefault(int cityId) {
        DictCity city = null;
        city = getCity(cityId);
        if (null == city) {
            city = getDefaultCity();
        }
        return city;
    }

    public List<DictCity> getCityList() {
        return dictCityDao.getAll();
    }

    /**
     * 按名字或者拼音简写获取城市对象（濮阳除外 需要传入puyang）
     * 
     * @param cityName
     * @return
     */
    public DictCity getCityByNameOrPinYin(String cityNameOrPinYin) {
        DictCity city = null;
        if(!StringUtils.isNotBlank(cityNameOrPinYin)) {
        	return getDefaultCity();
        }
        char c = cityNameOrPinYin.charAt(0);
        int i = (int)c;
        // 如果是字母的话
        if((i>=65&&i<=90)||(i>=97&&i<=122)) {
            city = dictCityDao.getCityByPinYin(cityNameOrPinYin);
        } else {
            city = dictCityDao.getCityIdByName(cityNameOrPinYin);
        }
        
        if (null==city && (AppConstants.CITYNAME_PY.equals(cityNameOrPinYin)
                || AppConstants.CITYNAME_SY.equals(cityNameOrPinYin)
                || AppConstants.CITYPINYIN_PY.equals(cityNameOrPinYin)
                || AppConstants.CITYPINYIN_SY.equals(cityNameOrPinYin))) {
            city = FactoryUtil.getFactory().getCityByNameOrPinyin(cityNameOrPinYin);
        }
        return city;
    }

    public DictCity getCityLocatedFromCookie(Invocation inv) {
        DictCity cityLocated = null;
        // 首先从cookie获取
        String cityIdInCookie = CookieUtil.getCookieValueByName(inv.getRequest(), CITY_SELECTED_COOKIE_KEY);
        if (StringUtils.isNotBlank(cityIdInCookie)) {
            try {
                Integer cityId = Integer.valueOf(cityIdInCookie);
                cityLocated = getCity(cityId);
            } catch (NumberFormatException e) {
                logger.error("", e);
            }
        } 
        return cityLocated;
    }


    public DictCity getCityLocatedInfo(Invocation inv) {
        DictCity cityLocated = null;
        // 首先从cookie获取
        cityLocated = getCityLocatedFromCookie(inv);
        // 如果cookie获取不到，给一个默认的城市
        if (null == cityLocated) {
            cityLocated = getDefaultCity();
        }
        return cityLocated;
    }

    public DictCity getDefaultCity() {
        return getCityByNameOrPinYin(AppConstants.DEFAULT_SELECT_CITY);
    }

    public DictCity getCityLocatedInfo(HttpServletRequest request) {
        DictCity cityLocated = null;
        String cityIdInCookie = CookieUtil.getCookieValueByName(request, CITY_SELECTED_COOKIE_KEY);
        
        if (StringUtils.isNotBlank(cityIdInCookie)) {
            try {
                Integer cityId = Integer.valueOf(cityIdInCookie);
                cityLocated = getCity(cityId);
            } catch (NumberFormatException e) {
                logger.error("", e);
            }
        }

        if (null == cityLocated) {
            cityLocated = getCityByNameOrPinYin(AppConstants.DEFAULT_SELECT_CITY);
        }
        return cityLocated;
    }

    /**
     * 将用户选择的城市信息 放入Cookie中
     */
    public void setCityLocated(Invocation inv, DictCity city) {
        if (null != city) {
            CookieUtil.addCookie(inv.getRequest(), inv.getResponse(), CITY_SELECTED_COOKIE_KEY, city
                    .getCityId().toString(), CITY_SELECTED_COOKIE_EXPIRE);
            CookieUtil.addCommonCookie(inv.getRequest(), inv.getResponse(), CITY_SELECT_COOKIE_KEY_AT_PINGE, String.valueOf(city.getCityId()), ".focus.cn", CITY_SELECTED_COOKIE_EXPIRE, "/");
        }
    }

    public void clearCityLocated(Invocation inv) {
        CookieUtil.addCookie(inv.getRequest(), inv.getResponse(), CITY_SELECTED_COOKIE_KEY, null, 0);
    }
    
    /**
     * 1、从缓存中获取城市信息
     * @param domainName
     * @return
     */
    public DictCity getCityByDomainname(String domainName) {
    	
    	DictCity city = null;
    	
    	if(StringUtils.isNotBlank(domainName)) {
    		Map<String, Object> map = cacheHandler.getCacheValue(AppConstants.CK_CITY_DOMAIN_NAME,Map.class);
    		
    		if(map != null ) {
    			
    			city = (DictCity) map.get(domainName);
    			if(city == null ) {
        			city =  getCity(domainName);
        			map.put(domainName, city);
        			cacheHandler.setCache(AppConstants.CK_CITY_DOMAIN_NAME, AppConstants.CE_CITY_DOMAIN_NAME, map);
        		}
    		} else {
    			
				map = new HashMap<String, Object>();
				city =  dictCityDao.getCityByDomainname(domainName);
    			map.put(domainName, city);
    			cacheHandler.setCache(AppConstants.CK_CITY_DOMAIN_NAME, AppConstants.CE_CITY_DOMAIN_NAME, map);
			}
    	}
    	return city;
    }
    
    /**
     * 获取所有城市，按字母排好序
     * @return
     */
    public TreeMap<String,List<DictCity>> getCityListOrderByPinYin(){
        List<DictCity> cityList = dictCityDao.getAll();
        //额外增加的城市
       // cityList.add(FactoryUtil.getFactory().getCityByNameOrPinyin("sy"));
       // cityList.add(FactoryUtil.getFactory().getCityByNameOrPinyin("puyang"));
        
        TreeMap<String,List<DictCity>> cityMap = new TreeMap<String,List<DictCity>>();
        
        for(DictCity city:cityList) {
            List<DictCity> list = cityMap.get(city.getInitial().toUpperCase());
            if(null==list) {
                list = new ArrayList<DictCity>();
            }
            list.add(city);
            cityMap.put(city.getInitial().toUpperCase(), list);
        }
        
        return cityMap;
        
    }

    /**
     * 获取所有城市，按字母排好序
     * @return
     */
    public TreeMap<String,List<DictCity>> getCityListOrderByPinYin(List<DictCity> cityList){
        //额外增加的城市
       // cityList.add(FactoryUtil.getFactory().getCityByNameOrPinyin("sy"));
       // cityList.add(FactoryUtil.getFactory().getCityByNameOrPinyin("puyang"));
        
        TreeMap<String,List<DictCity>> cityMap = new TreeMap<String,List<DictCity>>();
        
        for(DictCity city:cityList) {
            List<DictCity> list = cityMap.get(city.getInitial().toUpperCase());
            if(null==list) {
                list = new ArrayList<DictCity>();
            }
            list.add(city);
            cityMap.put(city.getInitial().toUpperCase(), list);
        }
        
        return cityMap;
        
    }
    public DictCity getCityByPinYinIgnoringStatus(String pinYin) {
        return dictCityDao.getCityByPinYinIgnoringStatus(pinYin);
    }
    
    public DictCity getCityByIdIgnoringStatus(int id) {
        return dictCityDao.getCityByIdIgnoringStatus(id);
    }
    
    /**
     * 获取城市是信息 按经纬度
     * @param lat
     * @param lgn
     * @return
     */
    public JSONObject getCityInfoObject(String lat,String lgn) {
        return interfaceService.getCityJsonInterface(lat, lgn);
    }
    
}
