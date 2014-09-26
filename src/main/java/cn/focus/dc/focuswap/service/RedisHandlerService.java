package cn.focus.dc.focuswap.service;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.SortingParams;
import redis.clients.jedis.Transaction;
import cn.focus.dc.focuswap.component.RedisServerConfig;
import cn.focus.dc.focuswap.model.TuanGou;
import cn.focus.dc.focuswap.model.TuanGouDetail;

import com.alibaba.fastjson.JSONObject;

@Service
public class RedisHandlerService implements InitializingBean {

    protected static Log logger = LogFactory.getLog(RedisHandlerService.class);

    @Resource(name = "redisServerConfigList")
    private List<RedisServerConfig> redisServerConfigList;

    @Autowired
    private String sceRedisPwd;

    private JedisPool rPool = null;

    private JedisPool wPool = null;

    private static final int LOCK_TTL = 60 * 60 * 1000;

    @Override
    public void afterPropertiesSet() throws Exception {
        initRedisPool();
    }

    /**
     * 初始化redisPool的方法，在该service初始化完成之后调用该方法或者在需要重构redisPool的时候
     * 
     * @throws Exception
     */
    private synchronized void initRedisPool() throws Exception {
        if (null == redisServerConfigList) {
            throw new ReidsNotEnableException("redis server config list is null");
        }

        for (RedisServerConfig cfg : redisServerConfigList) {
            if (null == cfg) {
                continue;
            }

            if (1 == cfg.getMaster() && null == wPool) {
                genJedisPool(cfg);
            } else if (0 == cfg.getMaster() && null == rPool) {
                genJedisPool(cfg);
            }
        }

        if (null == rPool || null == wPool) {
            throw new ReidsNotEnableException("redis servers are not enabled");
        }
    }

    private void genJedisPool(RedisServerConfig cfg) {
        JedisPoolConfig poolCfg = new JedisPoolConfig();
        poolCfg.setMaxActive(500);
        poolCfg.setMaxIdle(200);
        poolCfg.setMinIdle(100);
        poolCfg.setMaxWait(1000 * 100);
        if (1 == cfg.getMaster()) {
            if (null != wPool) {
                wPool.destroy();
                wPool = null;
            }
            if (StringUtils.isNotBlank(sceRedisPwd)) {
                wPool = new JedisPool(poolCfg, cfg.getIp(), cfg.getPort(), 0, sceRedisPwd);
            } else {
                wPool = new JedisPool(poolCfg, cfg.getIp(), cfg.getPort(), 0);
            }
        } else if (0 == cfg.getMaster()) {
            if (null != rPool) {
                rPool.destroy();
                rPool = null;
            }
            if (StringUtils.isNotBlank(sceRedisPwd)) {
                rPool = new JedisPool(poolCfg, cfg.getIp(), cfg.getPort(), 0, sceRedisPwd);
            } else {
                rPool = new JedisPool(poolCfg, cfg.getIp(), cfg.getPort(), 0);
            }
        }

    }

    /**
     * 缓存字符串，
     * 
     * @param key
     * @param value
     * @param expireInSecond <=0 表示不过期
     */
    @Deprecated
    public void set(String key, String value, int expireInSecond) {
        Jedis w = null;
        try {
            w = wPool.getResource();
            if (expireInSecond > 0) {
                w.setex(key, expireInSecond, value);
            } else {
                w.set(key, value);
            }
        } catch (Exception e) {
            logger.error("", e);
            wPool.returnBrokenResource(w);
        } finally {
            wPool.returnResource(w);
        }
    }

    /**
     * 获取缓存,返回类型为字符串
     * 
     * @param key
     * @return
     */
    @Deprecated
    public String get(String key) {
        Jedis r = null;
        String ret = null;
        try {
            r = rPool.getResource();
            ret = r.get(key);
        } catch (Exception e) {
            logger.error("", e);
            rPool.returnBrokenResource(r);
        } finally {
            rPool.returnResource(r);
        }
        return ret;
    }

    /**
     * 缓存字节数组
     * 
     * @param key
     * @param value
     * @param expireInSecond
     */
    public Long delete(String keys) {
        Jedis w = null;
        Long l = null;

        try {
            w = wPool.getResource();
            l = w.del(keys);
        } catch (Exception e) {
            logger.error("", e);
            wPool.returnBrokenResource(w);
        } finally {
            wPool.returnResource(w);
        }
        return l;
    }

    /**
     * 缓存字节数组
     * 
     * @param key
     * @param value
     * @param expireInSecond
     */
    public void set(byte[] key, byte[] value, int expireInSecond) {
        Jedis w = null;
        try {
            w = wPool.getResource();
            if (expireInSecond > 0) {
                w.setex(key, expireInSecond, value);
            } else {
                w.set(key, value);
            }
        } catch (Exception e) {
            logger.error("", e);
            wPool.returnBrokenResource(w);
        } finally {
            wPool.returnResource(w);
        }

    }

    /**
     * 获取缓存，返回类型为byte[]
     * 
     * @param key
     * @return
     */
    public byte[] get(byte[] key) {
        Jedis r = null;
        byte[] ret = null;
        try {
            r = rPool.getResource();
            ret = r.get(key);
        } catch (Exception e) {
            logger.error("", e);
            rPool.returnBrokenResource(r);
        } finally {
            rPool.returnResource(r);
        }
        return ret;
    }

    /**
     * 自增
     * 
     * @param key
     * @return
     */
    @Deprecated
    public Long incr(String key) {
        Jedis w = null;
        Long ret = null;
        try {
            w = wPool.getResource();
            ret = w.incr(key);
        } catch (Exception e) {
            logger.error("", e);
            wPool.returnBrokenResource(w);
        } finally {
            wPool.returnResource(w);
        }
        return ret;
    }

    /**
     * 自增
     * 
     * @param key
     * @return
     */
    public Long incr(byte[] key) {
        Jedis w = null;
        Long ret = null;
        try {
            w = wPool.getResource();
            ret = w.incr(key);
        } catch (Exception e) {
            logger.error("", e);
            wPool.returnBrokenResource(w);
        } finally {
            wPool.returnResource(w);
        }
        return ret;
    }

    /**
     * 存hashmap
     * 
     * @param key
     * @param value
     */
    public void hmset(String key, Map<String, String> value) {
        Jedis w = null;
        try {
            w = wPool.getResource();

            w.hmset(key, value);
        } catch (Exception e) {
            logger.error("", e);
            wPool.returnBrokenResource(w);
        } finally {
            wPool.returnResource(w);
        }
    }

    /**
     * 存一个单个map条目进入redis
     * 
     * @param key
     * @param field
     * @param value
     */
    public void hset(String key, String field, String value) {
        Jedis w = null;
        try {
            w = wPool.getResource();

            w.hset(key, field, value);
        } catch (Exception e) {
            logger.error("", e);
            wPool.returnBrokenResource(w);
        } finally {
            wPool.returnResource(w);
        }
    }

    /**
     * 拿一个单个map条目
     * 
     * @param key
     * @param field
     * @param value
     */
    public String hget(String key, String field) {
        Jedis r = null;
        String l = null;
        try {
            r = rPool.getResource();

            l = r.hget(key, field);
        } catch (Exception e) {
            logger.error("", e);
            rPool.returnBrokenResource(r);
        } finally {
            rPool.returnResource(r);
        }
        return l;
    }

    /**
     * 拿一个单个map
     * 
     * @param key
     * @param field
     * @param value
     */
    public Map<String, String> hgetAll(String key) {
        Jedis r = null;
        Map<String, String> m = null;
        try {
            r = rPool.getResource();

            m = r.hgetAll(key);
        } catch (Exception e) {
            logger.error("", e);
            rPool.returnBrokenResource(r);
        } finally {
            rPool.returnResource(r);
        }
        return m;
    }

    /**
     * 存一个javabean进入redis
     * 
     * @param key
     * @param value
     */
    public void hmset(String key, Object o) {
        Jedis w = null;
        try {
            w = wPool.getResource();

            Map<String, String> map = JavaBeanParseRedisMap(o);
            w.hmset(key, map);
        } catch (Exception e) {
            logger.error("", e);
            wPool.returnBrokenResource(w);
        } finally {
            wPool.returnResource(w);
        }
    }

    /**
     * 取hashmap
     * 
     * @param key
     * @return
     */
    public Map<String, String> hmget(String key) {
        Jedis r = null;
        Map<String, String> ret = null;
        try {
            r = rPool.getResource();
            ret = r.hgetAll(key);
        } catch (Exception e) {
            logger.error("", e);
            rPool.returnBrokenResource(r);
        } finally {
            rPool.returnResource(r);
        }

        return ret;
    }

    /**
     * 講一個json直接存入redis的hash類型中
     * 
     * @param key
     * @return
     */
    public void hmset(String key, JSONObject json) {
        Jedis w = null;

        Map<String, String> map = new HashMap<String, String>();
        for (String k : json.keySet()) {
            String v = (String) json.get(k);
            map.put(k.toString(), v);
        }
        try {
            w = wPool.getResource();
            w.hmset(key, map);
        } catch (Exception e) {
            logger.error("", e);
            wPool.returnBrokenResource(w);
        } finally {
            wPool.returnResource(w);
        }
    }

    public void lpush(String key, String value) {
        Jedis w = null;
        try {
            w = wPool.getResource();

            w.lpush(key, value);
        } catch (Exception e) {
            logger.error("", e);
            wPool.returnBrokenResource(w);
        } finally {
            wPool.returnResource(w);
        }
    }

    public String lpop(String key) {
        Jedis r = null;
        String s = null;
        try {
            r = rPool.getResource();
            s = r.lpop(key);
        } catch (Exception e) {
            logger.error("", e);
            rPool.returnBrokenResource(r);
        } finally {
            rPool.returnResource(r);
        }
        return s;
    }

    public List<String> lrange(String key, int start, int end) {
        Jedis r = null;
        List<String> l = null;
        try {
            r = rPool.getResource();

            l = r.lrange(key, start, end);
        } catch (Exception e) {
            logger.error("", e);
            rPool.returnBrokenResource(r);
        } finally {
            rPool.returnResource(r);
        }
        return l;
    }

    public long llen(String key) {
        Jedis r = null;
        long l = 0;
        try {
            r = rPool.getResource();

            l = r.llen(key);
        } catch (Exception e) {
            logger.error("", e);
            rPool.returnBrokenResource(r);
        } finally {
            rPool.returnResource(r);
        }
        return l;
    }

    public void lrem(String key, long count, String value) {
        Jedis w = null;
        try {
            w = wPool.getResource();

            w.lrem(key, count, value);
        } catch (Exception e) {
            logger.error("", e);
            wPool.returnBrokenResource(w);
        } finally {
            wPool.returnResource(w);
        }
    }

    private class ReidsNotEnableException extends Exception {

        private static final long serialVersionUID = 1L;

        public ReidsNotEnableException(String error) {
            super(error);
        }

    }

    public void setSceRedisPwd(String sceRedisPwd) {
        this.sceRedisPwd = sceRedisPwd;
    }

    public void setRedisServerConfigList(List<RedisServerConfig> redisServerConfigList) {
        this.redisServerConfigList = redisServerConfigList;
    }

    public void flushDB() {
        Jedis w = null;
        try {
            w = wPool.getResource();

            w.flushDB();
        } catch (Exception e) {
            logger.error("", e);
            wPool.returnBrokenResource(w);
        } finally {
            wPool.returnResource(w);
        }
    }

    public void flushAll() {
        Jedis w = null;
        try {
            w = wPool.getResource();

            w.flushAll();
        } catch (Exception e) {
            logger.error("", e);
            wPool.returnBrokenResource(w);
        } finally {
            wPool.returnResource(w);
        }
    }

    public void select(int index) {
        Jedis w = null;
        try {
            w = wPool.getResource();

            w.select(index);
        } catch (Exception e) {
            logger.error("", e);
            wPool.returnBrokenResource(w);
        } finally {
            wPool.returnResource(w);
        }
    }

    public List<String> sort(String key, SortingParams sortingParameters) {
        Jedis w = null;
        List<String> l = null;
        try {
            w = wPool.getResource();

            l = w.sort(key, sortingParameters);
        } catch (Exception e) {
            logger.error("", e);
            wPool.returnBrokenResource(w);
        } finally {
            wPool.returnResource(w);
        }
        return l;
    }

    public boolean acquireLock(String lock) {
        // 1. 通过SETNX试图获取一个lock
        boolean success = false;
        Jedis jedis = wPool.getResource();
        long value = System.currentTimeMillis() + LOCK_TTL + 1;
        long acquired = jedis.setnx(lock, String.valueOf(value));
        // SETNX成功，则成功获取一个锁
        if (acquired == 1)
            success = true;
        // SETNX失败，说明锁仍然被其他对象保持，检查其是否已经超时
        else {
            long oldValue = Long.valueOf(jedis.get(lock));

            // 超时
            if (oldValue < System.currentTimeMillis()) {
                String getValue = jedis.getSet(lock, String.valueOf(value));
                // 获取锁成功
                if (Long.valueOf(getValue) == oldValue)// 这句话是为了防止在此段时间别的jvm已经获得锁了
                    success = true;
                // 已被其他进程捷足先登了
                else
                    success = false;
            }
            // 未超时，则直接返回失败
            else
                success = false;
        }
        wPool.returnResource(jedis);
        return success;
    }

    /**
     * 将一个javabean转化为redis中hash类型中（key,Map）中的map
     * 
     * @param key
     * @return
     */
    public Map<String, String> JavaBeanParseRedisMap(Object o) {
        Map<String, String> map = new HashMap<String, String>();
        Class<? extends Object> clazz = o.getClass();
        Field[] fields = o.getClass().getDeclaredFields();// 获得属性
        for (Field field : fields) {
            // 序列化号不存入redis
            if (field.getName().equals("serialVersionUID"))
                continue;
            PropertyDescriptor pd = null;
            try {
                pd = new PropertyDescriptor(field.getName(), clazz);
            } catch (IntrospectionException e1) {
                e1.printStackTrace();
            }
            Method getMethod = pd.getReadMethod();// 获得get方法
            String str = null;// 获取属性值
            try {
                str = String.valueOf(getMethod.invoke(o));
            } catch (Exception e) {
                e.printStackTrace();
            }
            // 执行get方法返回一个Object
            if (StringUtils.isNotBlank(str))
                map.put(field.getName(), str);

        }
        return map;
    }

    /**
     * 将list中的TuanGou存入redis key: "tuangou:${cityID}:${activeId}"
     * 
     * @author zihaoli
     */
    public void updateTuanGouObject(List<TuanGou> list,int cityid) {
    	if(null==list || list.size()==0) {
    		return;
    	}
        String id;
        StringBuffer sb = new StringBuffer("");
        list = getRightTgList(list);
        for (TuanGou tuanGou : list) {
        	sb.append(tuanGou.getCity_id() + "_" + tuanGou.getGroup_id()).append(";");
            id = "tuangou:" + cityid + ":" + tuanGou.getActvie_id();
            hmset(id, tuanGou);
        }
        set("tuangou:"+ String.valueOf(cityid)+":groupid",sb.toString(),-1);
        System.out.println("geng xin tuangou goupids:" + cityid );
    }
    
    //get the right tuangou list  and push it into redis
    public List<TuanGou> getRightTgList(List<TuanGou> list) {
    	Collections.sort(list);
    	List<TuanGou> orderList = new ArrayList<TuanGou>();
    	List<TuanGou> timeList = new ArrayList<TuanGou>();
    	Iterator it = list.iterator();
    	long timeNow = System.currentTimeMillis();
    	while(it.hasNext()) {
    		TuanGou td = (TuanGou) it.next();
    		if(td.getActive_end().getTime()<timeNow) {
    			it.remove();
    		} else {
    			if(td.getActive_order()!=null && Integer.parseInt(td.getActive_order())<999) {
    				orderList.add(td);
    			} else {
    				timeList.add(td);
    			}

    		}
    		
    	}
    	Collections.sort(orderList, new Comparator<TuanGou>(){

			@Override
			public int compare(TuanGou o1, TuanGou o2) {
				// TODO Auto-generated method stub
				if(Integer.parseInt(o1.getActive_order())>Integer.parseInt(o2.getActive_order())) {
					return 1;
				} else if(Integer.parseInt(o1.getActive_order())<Integer.parseInt(o2.getActive_order())) {
					return -1;
				} else {
					return 0;
				}
			}
    		
    	});
//    	Collections.sort(timeList, new Comparator<TuanGou>(){
//
//			@Override
//			public int compare(TuanGou o1,TuanGou o2) {
//				// TODO Auto-generated method stub
//				if(o1.getActive_end().getTime()>o2.getActive_end().getTime()) {
//					return -1;
//				} else if(o1.getActive_end().getTime()<o2.getActive_end().getTime()) {
//					return 1;
//				} else {
//					return 0;
//				}
//			}});
    	orderList.addAll(timeList);
    	return orderList;
    }

    /**
     * 生成一个所有tuangou:${cityID}:${activeID}的索引列表：tuangou:${cityID}:list
     * 
     * @author zihaoli
     */
    public void updateTuanGouList(List<TuanGou> list, int cityId) {
    	if(null==list || list.size()==0) {
    		return;
    	}
    	list = getRightTgList(list);
        Jedis w = wPool.getResource();
        // 重建基础列表
        Transaction t = w.multi();
        t.del("tuangou:" + cityId + ":list");
        Collections.reverse(list);
        for (TuanGou tuanGou : list) {
            t.lpush("tuangou:" + cityId + ":list", String.valueOf(tuanGou.getActvie_id()));
        }
        t.exec();
        wPool.returnResource(w);
        System.out.println(cityId + "团购基础列表已更新");
    }

    /**
     * 生成按照applynum大小从高到低排序的列表，并存入redis
     */
    public void updateApplyNumSortedList(List<TuanGou> list, int cityId) {
    	if(null==list || list.size()==0) {
    		return;
    	}
        Jedis w = wPool.getResource();
        // 首先获得排序结果
        SortingParams sortingParameters = new SortingParams();
        sortingParameters.by("tuangou:" + cityId + ":*->apply_num");
        List<String> result = w.sort("tuangou:" + cityId + ":list", sortingParameters);
        // 根据结果重建排序列表
        Transaction t = w.multi();
        t.del("tuangou:" + cityId + ":apply_list");
        for (String item : result)
            t.lpush("tuangou:" + cityId + ":apply_list", item);
        t.exec();
        wPool.returnResource(w);
        System.out.println("报名人数排序列表已生成");
    }

    /**
     * 生成按照发起时间最近排序的列表，并存入redis
     * 
     * @author zihaoli
     */
    public void updateStartTimeSortedList(List<TuanGou> list, int cityId) {
        Jedis w = wPool.getResource();
        Collections.sort(list, new Comparator<TuanGou>() {
            @Override
            public int compare(TuanGou o1, TuanGou o2) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                try {
                    Date d1 = o1.getActive_start();
                    Date d2 = o2.getActive_start();
                    return d1.after(d2) ? 1 : -1;
                } catch (Exception e) {
                    System.out.println("日期比较失败" + e.getMessage());
                    return 0;
                }
            }
        });
        // 根据结果重建排序列表
        Transaction t = w.multi();
        t.del("tuangou:" + cityId + ":start_list");
        for (TuanGou item : list)
            t.lpush("tuangou:" + cityId + ":start_list", String.valueOf(item.getActvie_id()));
        t.exec();
        wPool.returnResource(w);
        System.out.println("发起时间最近排序列表已生成");
    }
}
