package cn.focus.dc.focuswap.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import net.rubyeye.xmemcached.MemcachedClient;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.focus.dc.focuswap.config.AppConstants;

import com.alibaba.fastjson.JSONObject;

/**
 * 碎片化数据相关service
 * 
 * @author zihaoli
 * @date 2014-07-16
 * @email zihaoli@sohu-inc.com
 */
@Service
public class HomeJiaoDianTuService {

    protected Log logger = LogFactory.getLog(HomeJiaoDianTuService.class);

    @Autowired
    private InterfaceService interfaceService;

    /**
     * 直接访问碎片化管理接口，获取所需JSON
     * 
     * @author zihaoli
     */
    public JSONObject getHomeJiaoDianTuList(Integer cityId, Integer limit) {
        Map<String, Object> urlVariables = new HashMap<String, Object>();
        urlVariables.put("cityId", cityId);
        urlVariables.put("limit", limit);
        // 只获取状态为“上线”的资源
        urlVariables.put("status", 1);
        JSONObject rt = null;
        try {
            rt = interfaceService.getJSONFromInterface(AppConstants.XIN_FANG_API_V4_HOME_JIAODIANTU_URL, urlVariables);
        } catch (Exception e) {
            logger.error("获取碎片化图片资源失败", e);
        }
        return rt;
    }
}
