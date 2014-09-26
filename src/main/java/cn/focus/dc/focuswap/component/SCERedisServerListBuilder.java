package cn.focus.dc.focuswap.component;

import com.alibaba.fastjson.JSONObject;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

/**
 * SCE的Redis服务获取
 * @author rogantian
 * @date 2014-5-26
 * @email rogantianwz@gmail.com
 */
public class SCERedisServerListBuilder {

    private String sceRedisApiUrl;
    
    public List<RedisServerConfig> getServerList() throws Exception{
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet get=new HttpGet(sceRedisApiUrl);
        HttpResponse httpResponse=httpClient.execute(get);
        HttpEntity httpEntity=httpResponse.getEntity();
        String response = EntityUtils.toString(httpEntity);
        
        //System.out.println(response);
        if (null != response) {
            List<RedisServerConfig> ret = JSONObject.parseArray(response, RedisServerConfig.class);
            return ret;
        }
        return null;
    }

    public void setSceRedisApiUrl(String sceRedisApiUrl) {
        this.sceRedisApiUrl = sceRedisApiUrl;
    }
    
    public static void main(String[] args) throws Exception {
        SCERedisServerListBuilder builder = new SCERedisServerListBuilder();
        builder.setSceRedisApiUrl("http://sceapi.apps.sohuno.com/api/redis/release?uid=1245");
        System.out.println(builder.getServerList());
    }

}
