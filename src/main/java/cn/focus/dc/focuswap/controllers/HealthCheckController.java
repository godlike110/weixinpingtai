package  cn.focus.dc.focuswap.controllers;

import static cn.focus.dc.focuswap.config.AppConstants.XINFANG_TYPE_ID_RELATION_URL;
import cn.focus.dc.focuswap.config.AppConstants;
import cn.focus.dc.focuswap.model.EsProjInfoChild;
import cn.focus.dc.focuswap.model.Information;
import cn.focus.dc.focuswap.model.TipsInfo;
import cn.focus.dc.focuswap.service.BuildingService;
import cn.focus.dc.focuswap.service.BuildingXinWenService;
import cn.focus.dc.focuswap.service.InterfaceService;
import cn.focus.dc.focuswap.service.NewsService;
import cn.focus.dc.focuswap.service.PaFangTuanService;
import cn.focus.dc.focuswap.service.RencentNewsService;
import cn.focus.dc.focuswap.service.SearchService;
import cn.focus.dc.lc.client.component.impl.AsyncStrLcLogSender;

import com.alibaba.fastjson.JSONObject;
import com.sohu.sce.api.model.AppInstance;
import com.sohu.sce.api.model.Credentials;
import com.sohu.sce.api.service.AppService;
import com.sohu.sce.api.service.ServiceFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;
import net.rubyeye.xmemcached.MemcachedClient;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

@Path("health")
public class HealthCheckController {
    
    private static Log log = LogFactory.getLog(HealthCheckController.class);

    private static final String CACHE_KEY = "building:health_check";
    
    private static final Integer CHECK_CITY_ID = 1;
    
    private static final Integer CHECK_BUILD_ID = 1;
    
    private static final Integer CHECK_GROUP_ID = 1;
    
    @Resource(name="memcachedClient")
    private MemcachedClient cacheClient;

    @Autowired
    private HealthCheckDAO healthChekcDao;

    @Autowired
    public InterfaceService interfaceService;
    
    @Autowired
    private SearchService searchService;
    
    @Autowired
    private BuildingService buildingService;
    
    @Autowired
    private RencentNewsService rencentNewsService;

    @Autowired
    private NewsService newsService;
    
    @Autowired
    private PaFangTuanService paFangTuanService;
    
    @Autowired
    private AsyncStrLcLogSender asyncLogSender;
    
    @Autowired
    private BuildingXinWenService buildingXinwenService;
    


    @Get("check")
    @Post("check")
    public String checkHealth(Invocation inv) {
        HttpServletResponse response = inv.getResponse();
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");

        StringBuilder sb = new StringBuilder();
        // 监测数据库
        sb.append(checkDB()).append(" ");
        
        // 监测memcache
        sb.append(checkCache()).append(" ");
        
        // 监测elasticSearch
        sb.append(checkES());
        
        //监测楼盘中心的接口
        sb.append(checkBuildingInterface()).append(" ");
        
        //监测新闻咨询的接口
        sb.append(checkNewsInterface()).append(" ");
        
        //监测动态的接口
        sb.append(checkDynamicsInterface()).append(" ");
        
        //监控爬房团接口
        sb.append(checkPaFangTuanInterface()).append(" ");

        //监控新房typeid关系映射接口
        sb.append(checkxinfangtypeIdInterface()).append(" ");
        
        //新闻中心接口
        sb.append(checkNewsCenterInterface()).append(" ");
        
        //日志队列监控
        checkLogQueueUsage();
        
        //获取n天内最多400电话的楼盘Id
        sb.append(checkGetGroupIdsAboutMost400()).append(" ");
        
        String result = sb.toString().trim();
        return "".equals(result) ? "@ok" : ("@err:" + result);
    }

    private String checkDB() {
        try {
            healthChekcDao.testDB();
            return "";
        } catch (Exception e) {
            log.error("", e);
            return "DB";
        }
    }
    
    private String checkBuildingInterface() {
        try {
            String ret = buildingService.getBuildingName(CHECK_CITY_ID, CHECK_BUILD_ID);
            if (null != ret) {
                return "";
            }
        } catch (Exception e) {
            log.error("", e);
        }
        return "BuildingInterface";
    }

    private String checkCache() {
        try {
            boolean result = cacheClient.set(CACHE_KEY, 30, "healthCheck");
            if (result) {
                cacheClient.get(CACHE_KEY);
            }
            return "";
        } catch (Exception e) {
            log.error("", e);
            return "Cache";
        }
    }

    private String checkES() {
        try {
            List<Integer> param = new ArrayList<Integer>();
            param.add(CHECK_GROUP_ID);
            List<EsProjInfoChild> ret = searchService.projGroupIdSearch(param);
            if (ret != null && ret.size() > 0) {
                return "";
            }
        } catch (Exception e) {
            log.error("", e);
        }
        return "ES";
    }
    
    private String checkNewsInterface(){
        try {
            JSONObject ret = newsService.getNewsFromPHP(38, 1, 10);
            if (null != ret) {
                return "";
            }
        } catch (Exception e) {
            log.error("", e);
        }
        return "NewsInterface";
    }
    
    private String checkDynamicsInterface() {
        try {
            JSONObject ret = rencentNewsService.getRecentNewsFromPHP(CHECK_BUILD_ID, CHECK_CITY_ID, 0, 6);
            if (null != ret) {
                return "";
            }
        } catch (Exception e) {
            log.error("", e);
        }
        return "DynamicsInterface";
    }
    
    private String checkPaFangTuanInterface(){
        try {
            List<TipsInfo> tips = paFangTuanService.getTips(1);
            if(null!=tips && tips.size()!=0) {
                return "";
            }
        } catch (Exception e) {
            log.error("",e);
        }
        return "PaFangTuanInterface";
    }
    
    private String checkxinfangtypeIdInterface() {
        try {
            Map<String, Object> variables = new HashMap<String, Object>();
            variables.put("cityId", 1);
            JSONObject result = interfaceService.getJSONFromInterface(XINFANG_TYPE_ID_RELATION_URL,variables);
            if(result!=null && result.get("data")!=null) {
                 return "";   
            }
        } catch (Exception e) {
            log.error("",e);
        }
        return "xinfangTypeRelationInterface";
    }
    
    private String checkNewsCenterInterface() {
        try {
        Information news = newsService.getNews(4002710, 1,1,"bj");
        if (news != null) {
            return "";
        }
        } catch (Exception e) {
            log.error("",e);
        }
        return "newsCenterInterface";
    }
    
    private String checkGetGroupIdsAboutMost400() {
        try {
            List<Integer> groupId = buildingXinwenService.getGroupIds("bj", 10, 7);
            if (groupId != null) {
                return "";
            }
        } catch (Exception e) {
            log.error("",e);
        }
        return "checkGetGroupIdsAboutMost400";
    }

    
    private String checkLogQueueUsage() {
        try {
            int used = asyncLogSender.getRealQueueSize();
            int capacity = asyncLogSender.getQueueSize();
            log.info("QUEUE USAGE：" + used + "/" + capacity);
        } catch (Exception e) {
            log.error("",e);
        }
        return "";
    }

    /**
     * 检查所有实例的健康状况
     * @return
     */
    @Get("checkAll")
    public String checkAll() {
        
        List<AppInstance> instances = getEntityList();
        StringBuilder ret = new StringBuilder();
        ret.append("@check the ").append(AppConstants.SCE_APP_ID).append(" status:").append("<br/>");
        if (null != instances && instances.size() > 0) {
            for(AppInstance instance : instances) {
                StringBuilder url = new StringBuilder("http://").append(instance.getIp()).append("/health/check");
                String entityRep = interfaceService.getStringFromInterface(url.toString(), null);
                ret.append(instance.getIp()).append(" : ");
                if (null != entityRep) {
                    ret.append(entityRep);
                } else {
                    ret.append("error");
                }
                ret.append("<br/>");
            }
        } else {
            ret.append("can not get the app's ip list");
        }
        return ret.toString();
    }
    
    /**
     * 获取实例的IP列表
     * @return
     */
    public List<AppInstance> getEntityList() {
        List<AppInstance> instances = null;
        try {
            Credentials c = new Credentials(AppConstants.SCE_APP_ID, AppConstants.SCE_APP_SECRET);
            AppService service = ServiceFactory.getInstance();
            service.setEndPoint(AppConstants.SCE_API_ENDPOINT);
            instances = service.findInstances(c, Integer.parseInt(AppConstants.SCE_APP_ID));
        } catch (Exception e) {
            log.error("", e);
        }
        return instances;
    }
}


@DAO(catalog = "xinfang")
interface HealthCheckDAO {

    @SQL("select city_id from dict_city where city_id =1")
    Long testDB();

}
