package cn.focus.dc.focuswap.service;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;


public class RedisService {
    private final Logger logger = LoggerFactory.getLogger(RedisService.class);
    @Autowired
    @Qualifier("redisApi")
    private String url = "http://sceapi.apps.sohuno.com/api/redis/release?uid=1207";
    @Autowired
    @Qualifier("redisPassWord")
    private String passwd = "497d3e4a249084c031609379d9bcc48a";
    private String masterHost;
    private String slaveHost;
    private int masterPort;
    private int slavePort;
    private JedisPool wpool = null;
    private JedisPool rpool = null;

    void setRedisInfo() throws IOException {
        HttpClient httpClient=new DefaultHttpClient();
        HttpGet get=new HttpGet(this.url);
        HttpResponse httpResponse=httpClient.execute(get);
        HttpEntity httpEntity=httpResponse.getEntity();
        String content = EntityUtils.toString(httpEntity);
        JSONArray sl = JSON.parseArray(content);
        this.masterHost = sl.getJSONObject(0).getString("ip");
        this.masterPort = sl.getJSONObject(0).getInteger("port");
        this.slaveHost = sl.getJSONObject(1).getString("ip");
        this.slavePort = sl.getJSONObject(1).getInteger("port");
    }

    /**
     * 重新获取redispool
     * @throws IOException
     */
    void getLatestRedis() throws IOException {
        this.wpool.destroy();
        this.wpool=null;
        this.rpool.destroy();
        this.rpool=null;
    }

    /**
     *  获取redis的master连接池
     * @return
     */
    public JedisPool getWritePool(){
        if (wpool == null) {
            JedisPoolConfig config = new JedisPoolConfig();
            try {
                setRedisInfo();
            } catch (IOException e) {
                logger.error(e.getMessage(),e);
            }
            config.setMaxActive(500);
            config.setMaxIdle(200);
            config.setMinIdle(100);
            config.setMaxWait(1000 * 100);
            wpool = new JedisPool(config, this.masterHost, this.masterPort,0,passwd);
        }
        return wpool;
    }
    public JedisPool getReadPool(){
        if (rpool == null) {
            JedisPoolConfig config = new JedisPoolConfig();
            try {
                setRedisInfo();
            } catch (IOException e) {
                logger.error(e.getMessage(),e);
            }
            config.setMaxActive(500);
            config.setMaxIdle(200);
            config.setMinIdle(100);
            config.setMaxWait(1000 * 100);
            rpool = new JedisPool(config, this.slaveHost, this.slavePort,0,passwd);
        }
        return rpool;
    }

    /**
     * 返还redis实例到连接池
     * @param redis
     */
    public void returnReadResource(Jedis redis) {
        if (redis != null) {
            getReadPool().returnResource(redis);
        }
    }
    public void returnReadBroResource(Jedis redis) {
        if (redis != null) {
            getReadPool().returnBrokenResource(redis);
        }
    }
    public void returnWriteResource(Jedis redis) {
        if (redis != null) {
            getWritePool().returnResource(redis);
        }
    }
    public void returnWriteBroResource(Jedis redis) {
        if (redis != null) {
            getWritePool().returnBrokenResource(redis);
        }
    }
    /**
     * 获取具有写权限的客户端
     * @return
     */
    public Jedis getWClient(){
        Jedis j = getWritePool().getResource();
        return j;
    }

    /**
     * 获取具有只读权限的客户端
     * @return
     */
    public Jedis getRClient(){
        Jedis j = getReadPool().getResource();
        return j;
    }

    /**
     * 过期时间如果为0则默认永不过期
     * @param k
     * @param v
     * @param seconds
     */
    public void set(String k,String v,int seconds){
        Jedis j =null;
        try {
            j=getWClient();
            String status =j.set(k,v);
            if (seconds > 0){
                j.expire(k,seconds);
            }
            getWritePool().returnResource(j);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            getWritePool().returnBrokenResource(j);
            try {
                getLatestRedis();
            } catch (Exception e1) {
                logger.error(e1.getMessage(),e1);
            }
        }
    }

    public void set(byte[] k,byte[] v,int seconds){
        Jedis j=null;
        try {
            j =getWClient();
            j.set(k, v);
            if (seconds > 0){
                j.expire(k,seconds);
            }
            getWritePool().returnResource(j);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            getWritePool().returnBrokenResource(j);
            try {
                getLatestRedis();
            } catch (Exception e1) {
                logger.error(e1.getMessage(),e1);
            }
        }

    }

    public void del(byte[] key){
        Jedis j=null;
        try {
            j =getWClient();
            j.del(key);
            getWritePool().returnResource(j);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            getWritePool().returnBrokenResource(j);
            try {
                getLatestRedis();
            } catch (Exception e1) {
                logger.error(e1.getMessage(),e1);
            }
        }
    }
    public void del(String key){
        Jedis j=null;
        try {
            j =getWClient();
            j.del(key);
            getWritePool().returnResource(j);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            getWritePool().returnBrokenResource(j);
            try {
                getLatestRedis();
            } catch (Exception e1) {
                logger.error(e1.getMessage(),e1);
            }
        }
    }

    public String get(String k){
        Jedis j =null;
        String res =null;
        try {
            j = getRClient();
            res = j.get(k);
            getReadPool().returnResource(j);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            getReadPool().returnBrokenResource(j);
            try {
                getLatestRedis();
            } catch (Exception e1) {
                logger.error(e1.getMessage(),e1);
            }
        }
        return res;
    }

    public byte[] get(byte[] k){
        Jedis j =null;
        byte[] res =null;
        try {
            j = getRClient();
            res = j.get(k);
            getReadPool().returnResource(j);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            getReadPool().returnBrokenResource(j);
            try {
                getLatestRedis();
            } catch (Exception e1) {
                logger.error(e1.getMessage(),e1);
            }
        }
        return res;
    }
    
    public List<String> mget(String[] keys){
        Jedis j =null;
        List<String> res =null;
        try {
            j = getRClient();
            res = j.mget(keys);
            getReadPool().returnResource(j);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            getReadPool().returnBrokenResource(j);
            try {
                getLatestRedis();
            } catch (Exception e1) {
                logger.error(e1.getMessage(),e1);
            }
        }
        return res;
    }
    
    public List<byte[]> mget(byte[][] keys){
        Jedis j =null;
        List<byte[]> res =null;
        try {
            j = getRClient();
            res = j.mget(keys);
            getReadPool().returnResource(j);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            getReadPool().returnBrokenResource(j);
            try {
                getLatestRedis();
            } catch (Exception e1) {
                logger.error(e1.getMessage(),e1);
            }
        }
        return res;
    }
    
    public void mset(Map<String, String> keysvalues) {
        Jedis j =null;
        try {
            j=getWClient();
            String[] keysValuesArr = new String[2 * keysvalues.size()];
            Set<Entry<String, String>> set = keysvalues.entrySet();
            Iterator<Entry<String, String>> it = set.iterator();
            int index = 0;
            while(it.hasNext()) {
                Entry<String, String> entry = it.next();
                keysValuesArr[index++] = entry.getKey();
                keysValuesArr[index++] = entry.getValue();
            }
            j.mset(keysValuesArr);
            getWritePool().returnResource(j);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            getWritePool().returnBrokenResource(j);
            try {
                getLatestRedis();
            } catch (Exception e1) {
                logger.error(e1.getMessage(),e1);
            }
        }
    }
    
    public void msetex(Map<String, String> keysvalues, int expireTime) {
        Jedis j =null;
        try {
            j=getWClient();
            String[] keysValuesArr = new String[keysvalues.size()];
            Set<Entry<String, String>> set = keysvalues.entrySet();
            Iterator<Entry<String, String>> it = set.iterator();
            int index = 0;
            while(it.hasNext()) {
                Entry<String, String> entry = it.next();
                keysValuesArr[index++] = entry.getKey();
                keysValuesArr[index++] = entry.getValue();
            }
            j.mset(keysValuesArr);
            if (expireTime > 0) {
                for (int i = 0; i < keysValuesArr.length; i = i + 2) {
                    //jedis没看到msetex和mexpire 所以只能多次rpc
                    j.expire(keysValuesArr[i], expireTime);
                }
            }
            getWritePool().returnResource(j);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            getWritePool().returnBrokenResource(j);
            try {
                getLatestRedis();
            } catch (Exception e1) {
                logger.error(e1.getMessage(),e1);
            }
        }
    }

    public List<String> hmget(String key, String[] fields) {
        Jedis j =null;
        List<String> res =null;
        try {
            j = getRClient();
            res = j.hmget(key, fields);
            getReadPool().returnResource(j);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            getReadPool().returnBrokenResource(j);
            try {
                getLatestRedis();
            } catch (Exception e1) {
                logger.error(e1.getMessage(),e1);
            }
        }
        return res;
    }
    
    public void hmset(String key, Map<String, String> keysvalues) {
        Jedis j =null;
        try {
            j = getWClient();
            j.hmset(key, keysvalues);
            getWritePool().returnResource(j);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            getWritePool().returnBrokenResource(j);
            try {
                getLatestRedis();
            } catch (Exception e1) {
                logger.error(e1.getMessage(),e1);
            }
        }
    }
    
    public void hmsetex(String key, Map<String, String> keysvalues, int expireTime) {
        Jedis j =null;
        try {
            j = getWClient();
            j.hmset(key, keysvalues);
            j.expire(key, expireTime);
            getWritePool().returnResource(j);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            getWritePool().returnBrokenResource(j);
            try {
                getLatestRedis();
            } catch (Exception e1) {
                logger.error(e1.getMessage(),e1);
            }
        }
    }
    
    public Set<byte[]> keys(byte[] pattern) {
        Jedis j = null;
        Set<byte[]> res = null;
        try {
            j = getRClient();
            res = j.keys(pattern);
            getReadPool().returnResource(j);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            getReadPool().returnBrokenResource(j);
            try {
                getLatestRedis();
            } catch (Exception e1) {
                logger.error(e1.getMessage(), e1);
            }
        }
        return res;
    }
    
    
    //测试用的
    public static void main(String[] args) throws IOException {
        RedisService r = new RedisService();
//        r.set("aa","bb",12);
//        r.set("aab","bb22",12);
//        r.set("aac","bb232",12);
//
//        System.out.println(r.get("aa"));
//        System.out.println(r.get("aab"));
//        System.out.println(r.get("aac"));
        
        Jedis jedis = r.getWClient();
//        jedis.sadd("1","a","b");
//        jedis.sadd("2","b","c");
//        jedis.sdiffstore("3","2","1");

//        Map<String, String> hash = new HashMap<String, String>();
//        hash.put("a", "1");
//        hash.put("b", "2");
//        jedis.hmset("kk", hash);
//        hash.put("c", "3");
//        jedis.hmset("kk", hash);
//        jedis.expire("kk", 5);
        
        System.out.println(jedis.hgetAll("client_build"));
//        System.out.println(jedis.smembers("3"));
    }

}

