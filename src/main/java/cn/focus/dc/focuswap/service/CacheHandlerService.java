package cn.focus.dc.focuswap.service;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import javax.annotation.Resource;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

@Service
public class CacheHandlerService {

	protected Log logger = LogFactory.getLog(CacheHandlerService.class);

	private boolean isOpenCache = true;

	@Resource(name="memcachedClient")
	private MemcachedClient cacheClient;

	public void removeCache(String key) {
		if (isOpenCache) {
			try {
				cacheClient.deleteWithNoReply(key);
			} catch (Exception e) {
				logger.error("", e);
			}
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> T getCacheValue(String key, Class T) {
		if (isOpenCache) {
			try {
				//return (T) cacheClient.get(key);
				return (T)cacheClient.get(key, 2000L);
			} catch (Exception e) {
				logger.error("", e);
			}
		}
		return null;
	}

	public void setCache(String key, int exp, Object value) {
		if (value == null || "".equals(value.toString())) {
			return;
		}
		if (isOpenCache) {
			try {
				cacheClient.setWithNoReply(key, exp, value);
			} catch (Exception e) {
				logger.error("", e);
			}
		}
	}
	
	public boolean addCache(String key, int exp, Object value){
	    try {
	        return cacheClient.add(key, exp,value);
	    } catch (Exception e) {
            logger.error("", e);
        }
	    return false;
	}
	
	public <T> Map<String, T> getMultyValues(Collection<String> collection) throws TimeoutException, InterruptedException, MemcachedException{
	    return cacheClient.get(collection);
	}
}
