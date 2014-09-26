package cn.focus.dc.focuswap.controllers;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import cn.focus.dc.focuswap.config.AppConstants;
import cn.focus.dc.focuswap.service.IpService;
import cn.focus.dc.focuswap.service.UploadService;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;


/**
 * 上传文件
 * @author zhiweiwen
 *
 */
@Path("upload")
public class UploadController {
	
	public static Log logger = LogFactory.getLog(UploadController.class);
	
	private static final String INNERCALL = "wapupload";
	
	@Autowired
	private UploadService uploadService;
	
	@Autowired
	private IpService ipService;
	
	/**
	 * 
	 * @param auth(jf9e3ynvnvb83295jfknkgjkfjdsJie)
	 * @return
	 */
	@Get("")
	public String index(Invocation inv,@Param("auth") String auth,@Param("env") String env) {
		if(auth==null || !auth.equals(AppConstants.UPLOAD_AUTH)) {
			return "@非法请求！";
		}
		inv.addModel("env", env);
		return "upload";		
	}
	
    @Post("/doUpload")
    public String doUpload(Invocation inv,@Param("file") MultipartFile file,@Param("path") String path,@Param("env") String env) throws IOException {
    	if(null==file) {
    		return "@没有上传文件";
    	}
    	if(!StringUtils.isNotBlank(env) || null==env) {
    		return "@请确认当前环境，生产环境添加env参数，参数值为：product,其他环境请将参数值设置为：test,test_sce";
    	}
    	String filename = file.getOriginalFilename();
    	String endPrefix = filename.substring(filename.indexOf(".")+1);
    	
    	if(!endPrefix.equals("html")) {
    		return "@此文件非法！请上传html文件";
    	}    	   		

    	if(StringUtils.isNotBlank(env) && (env.equals("product")||env.equals("test_sce")) && !inv.getRequest().getServerName().contains("sohuno")) {
    		return "@你没有权限进行词操作！！";
    	}
    	uploadService.uploadFile(inv, file, path,env);
    	
    	//往缓存写数据
		if(env!=null && StringUtils.isNotBlank(env) && (env.equals("product") || env.equals("test_sce"))) {
			//只有product、Test_sce才会往cache写数据
			uploadService.saveFileToCache(path+file.getOriginalFilename(),AppConstants.ZZF_PAGE_KEY);
		}
		
		//如果有必要 通知其他实例更新本地文件
    	boolean flag = true;
    	if(StringUtils.isNotBlank(env) && (env.equals("product") || env.equals("test_sce"))) {
    		flag = uploadService.notifyOtherInstances(inv);
    	}
    	if(flag) {
    		inv.addModel("url", path + file.getOriginalFilename());
    	}
    	return "uploadresult";
    	
    }
    
    /**
     * 通知从redis更新文件
     * @return
     */
    @Post("notify")
    public String notifyLoadFromCache() {
    	
    	return "@" + uploadService.readCacheFiletoLocalCron();

    }
    
    /**
     * 增加白名单
     * @param ip
     * @param auth
     * @return
     */
    @Get("addWhiteip")
    public String addWhiteList(@Param("ip") String ip,@Param("auth") String auth) {
		if(auth==null || !auth.equals(AppConstants.UPLOAD_AUTH)) {
			return "@非法请求！";
		}
		if(ipService.addWhiteListIp(ip)) {
			return "@add to whitelist success";
		}
    	return null;
    }
    
    /**
     * 删除白名单
     * @param ip
     * @param auth
     * @return
     */
    @Get("delWhiteip")
    public String delWhiteList(@Param("ip") String ip,@Param("auth") String auth) {
		if(auth==null || !auth.equals(AppConstants.UPLOAD_AUTH)) {
			return "@非法请求！";
		}
		if(ipService.delWhiteListIp(ip)) {
			return "@delete ip from whitelist success";
		}
    	return "@"+false;
    }
    
    @Get("test1") 
    public String get() {
    	return "test";
    }
    
    @Post("test")
    @Get("test")
    public String doTest(Invocation inv,@Param("file") MultipartFile file,@Param("path") String path,@Param("ip") String ip) throws IOException {
    	String apppath = inv.getServletContext().getRealPath("");
    	String aPath = apppath + path +  file.getOriginalFilename();
        //String flag = uploadService.uploadToOneInstance(file, "http://10.10.40.139", path,aPath);
        return "@";
    }

}
