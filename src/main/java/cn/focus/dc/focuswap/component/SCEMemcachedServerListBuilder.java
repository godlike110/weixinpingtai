package cn.focus.dc.focuswap.component;

import com.alibaba.fastjson.JSONObject;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.rubyeye.xmemcached.auth.AuthInfo;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.client.RestTemplate;

public class SCEMemcachedServerListBuilder {

    private String sceZKUrl;

    private String sceUid;

    private String scePassword;

    private RestTemplate restTempate = new RestTemplate();
    
    public void setSceZKUrl(String sceZKUrl) {
        this.sceZKUrl = sceZKUrl;
    }

    public void setScePassword(String scePassword) {
        this.scePassword = scePassword;
    }

    public void setSceUid(String sceUid) {
        this.sceUid = sceUid;
    }

    public Map<InetSocketAddress, AuthInfo> getAuthInfoMap() throws Exception { 
        Map<InetSocketAddress, AuthInfo> ret = new HashMap<InetSocketAddress, AuthInfo>();
        AuthInfo authInfo = AuthInfo.plain(this.sceUid, this.scePassword);
        List<JSONObject> list = getZKserverList();
        for (JSONObject serverObj : list) {
            String ip = serverObj.getString("ip");
            int port = serverObj.getIntValue("port");
            InetSocketAddress addr = new InetSocketAddress(ip,port);
            ret.put(addr,authInfo);
        }
        return ret;
    }
    
    public String getServerListStr() throws Exception {
        List<JSONObject> list = getZKserverList();
        StringBuilder sb = new StringBuilder();
        for (JSONObject serverObj : list) {
            String ip = serverObj.getString("ip");
            int port = serverObj.getIntValue("port");
            sb.append(ip).append(":").append(port).append(" ");
        }
        return StringUtils.trim(sb.toString());
    }
    
    private List<JSONObject> getZKserverList() throws Exception { 
        String md5 = DigestUtils.md5Hex(sceUid + scePassword).toUpperCase();
        Map<String, String> params = new HashMap<String, String>();
        params.put("uid", this.sceUid);
        params.put("key", md5);
        String jsonStr = this.restTempate.getForObject(sceZKUrl, String.class, params);
        return JSONObject.parseArray(jsonStr, JSONObject.class);
    }

}
