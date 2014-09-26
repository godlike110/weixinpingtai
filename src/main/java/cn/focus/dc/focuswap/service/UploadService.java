package cn.focus.dc.focuswap.service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.paoding.rose.web.Invocation;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import cn.focus.dc.focuswap.config.AppConstants;
import cn.focus.dc.focuswap.utils.ObjectUtil;

import com.sohu.sce.api.model.AppInstance;
import com.sohu.sce.repackaged.net.rubyeye.xmemcached.utils.ByteUtils;

/**
 * 上传服务
 * @author zhiweiwen
 *
 */
@Service
public class UploadService {

	public static Log logger = LogFactory.getLog(UploadService.class);
	
	
	@Autowired
	private IpService ipService;
	
	@Autowired
	private InterfaceService interfaceService;
	
	@Autowired
	private RedisHandlerService redisHandlerService;
	
	private static String fileByteKey = "filebyte";
	private static String filePathKey = "filepath";
	private static int byteLength = 0;
	
	
	private boolean saveFile(MultipartFile Mfile ,String path) throws IOException {
		try {
		File file = new File(path);
		if(file.exists()) {
			file.delete();
			logger.info("delete file:" + path);
		} else {
			file.createNewFile();
		}
		InputStream is = Mfile.getInputStream();
		FileOutputStream os = new FileOutputStream(path);
		byte[] buffer = new byte[4048];
		while((is.read(buffer))!=-1) {
			
			os.write(buffer);
			buffer = new byte[4048];
		}
		is.close();
		//os.flush();
		os.close();

		return true;
		} catch(Exception e) {
			logger.error("", e);
			return false;
		}
		
	}
	
	/**
	 * 保存文件
	 * @param Mfile
	 * @param path
	 * @return
	 * @throws IOException
	 */
	private boolean saveFile(byte[] bytefile ,String path) throws IOException {
		try {
		File file = new File(path);
		if(file.exists()) {
			file.delete();
			logger.info("delete file:" + path);;
		} else {
			file.createNewFile();
		}
		InputStream is = new ByteArrayInputStream(bytefile);
		FileOutputStream os = new FileOutputStream(path);
		byte[] buffer = new byte[4048];
		while((is.read(buffer))!=-1) {			
			os.write(buffer);
			buffer = new byte[4048];
		}
		is.close();
		os.flush();
		os.close();

		return true;
		} catch(Exception e) {
			logger.error("", e);
			return false;
		}
		
	}
	
	/**
	 * 上传文件
	 * @param Mfile
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public String uploadFile(Invocation inv,MultipartFile file,String path,String env) throws IOException {
    	String apppath = inv.getServletContext().getRealPath("");
    	String aPath = apppath + path +  file.getOriginalFilename();
    	if(saveFile(file, aPath)) {
    		return "success";
    	}
    	
    	return "false";
	}
	
	/**
	 * 通知其他服务器更�?	 * @param inv
	 * @return
	 * @throws UnknownHostException
	 */
	public boolean notifyOtherInstances(Invocation inv) throws UnknownHostException {
    	String thisIp = inv.getRequest().getServerName();
    	String profile = "";
		try {
		List<AppInstance> instances = ipService.getEntityList();
		for(AppInstance appi:instances) {
			String ip = appi.getIp();
			if(thisIp.equals(ip)) {
				continue;
			}
			notifyOneInstance(ip);
		}
		   return true;
		} catch(Exception e) {
			logger.error("", e);
			return false;
		}
		
	}
	
	/**
	 * 向一台服务发送更新提�?	 * @param ip
	 * @return
	 */
	public String notifyOneInstance(String ip) {
		String result = interfaceService.postObjectForString("http://" + ip +"/upload/notify/",null);
		return result;
	}
	
	/**
	 * 读取�?��文件保存到本�?	 */
    //@Scheduled(cron="0 0/1 * * * ?")
	public String readCacheFiletoLocalCron() {
		Map<String,Object> fileMap = null;
		try {
			fileMap = getFileMap(AppConstants.ZZF_PAGE_KEY);
			if(null==fileMap) {
				return "false";
			}
			saveCacheFileToLocal(fileMap);
			logger.info("recover file to local: " + (String) fileMap.get("filepath"));
			return "success";
		} catch(Exception e) {
			logger.error("", e);
		}
		return "false";
	}
	
    public void uploadJob() {
    	readCacheFiletoLocalCron();
    }
    
	public void saveCacheFileToLocal(Map<String,Object> fileMap) throws IOException {
		byte[] bytefile = new byte[byteLength];
		bytefile = (byte[]) fileMap.get(fileByteKey);
 		String path = (String) fileMap.get(filePathKey);
    	String apath = System.getProperty("appxinfang.root");

    	if(apath.endsWith(File.separator)) {
    		apath = apath.substring(0, apath.length()-1);
    	}
    	path = apath + path;
		saveFile(bytefile, path);
	}
	
	/**
	 * 存储文件到cache
	 * @param path
	 * @param file
	 * @param key
	 */
	public void saveFileToCache(String path,String key) {
    	String apath = System.getProperty("appxinfang.root");
    	if(path.endsWith(File.separator)) {
    		apath = apath.substring(0, apath.length()-1);
    	}
    	String pathvalue = path;
    	path = apath + path;
		File file = new File(path);
		byte[] bytevalue = ObjectUtil.filetoBytes(file);
		try {
			redisHandlerService.set(AppConstants.ZZF_PAGE_PATH_KEY, pathvalue, AppConstants.FILE_SAVE_TIME);
			redisHandlerService.set(ByteUtils.getBytes(AppConstants.ZZF_PAGE_KEY), bytevalue, AppConstants.FILE_SAVE_TIME);
		} catch(Exception e) {
			logger.error("", e);
		}
	}
	
	/**
	 * 获取文件map
	 * @param key
	 * @return
	 */
	public Map<String,Object> getFileMap(String key) {

		Map<String,Object> fileMap = new HashMap<String,Object>();
		try {
			byte[] filebyte = redisHandlerService.get(ByteUtils.getBytes(AppConstants.ZZF_PAGE_KEY));
			if(null==filebyte) {
				return null;
			}
			String path = redisHandlerService.get(AppConstants.ZZF_PAGE_PATH_KEY);
			if(null==path) {
				return null;
			}
			fileMap.put(fileByteKey, filebyte);
			fileMap.put(filePathKey, path);
		} catch (Exception e) {
			logger.error("", e);
		}
		return fileMap;
		
	}
	
}