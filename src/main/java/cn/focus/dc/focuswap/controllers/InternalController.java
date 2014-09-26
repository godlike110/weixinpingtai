package cn.focus.dc.focuswap.controllers;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.annotation.processing.FilerException;
import javax.servlet.ServletException;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestUtils;

import cn.focus.dc.focuswap.config.AppConstants;
import cn.focus.dc.focuswap.model.DictAreaExt;
import cn.focus.dc.focuswap.model.DictCity;
import cn.focus.dc.focuswap.model.DictDistrictExt;
import cn.focus.dc.focuswap.model.EsProjInfoChild;
import cn.focus.dc.focuswap.model.SearchCondition;
import cn.focus.dc.focuswap.service.BuildingSearchService;
import cn.focus.dc.focuswap.service.CityService;
import cn.focus.dc.focuswap.service.InterfaceService;
import cn.focus.dc.focuswap.service.InternalService;
import cn.focus.dc.focuswap.service.SearchService.SearchType;

import com.sohu.sce.api.model.AppInstance;
import com.sohu.sce.api.model.Credentials;
import com.sohu.sce.api.service.AppService;
import com.sohu.sce.api.service.ServiceFactory;

/**
 * 内部接口
 * @author rogantian
 * @date 2014-4-23
 * @email rogantianwz@gmail.com
 */
@Path("internal")
public class InternalController {

    protected static final Log logger = LogFactory.getLog(InternalController.class);
    
    private final static String INTERNAL_INTERFACE_AUTH = "287dd18d58e368a48311c4449c3db969";
    
    private final static String APP_INTERNAL_AUTH = "d1f16dc78006aa2663c41f5311b6993e";
    
    @Autowired
    private InternalService internalService; 
    
    @Autowired
    public InterfaceService interfaceService;
    
    @Autowired
    private CityService cityService;
    
    @Autowired
    private BuildingSearchService buildingSearchService;
    
    /**
     * 生成楼盘首页的开放适配文件
     * @param inv
     * @return
     * @throws Exception
     */
    @Get("kfsp/remote/")
    public String genRemoteSiteMap(Invocation inv) throws Exception {
        if (! checkAuth(inv, false)) {
            return "e:404";
        }
        
        List<AppInstance> instances = getEntityList();
        if (null != instances && instances.size() > 0) {
            for (AppInstance app : instances) {
                StringBuilder url = new StringBuilder("http://").append(app.getIp()).append("/internal/kfsp/remote/real/?auth=d1f16dc78006aa2663c41f5311b6993e");
                String entityRep = interfaceService.getStringFromInterface(url.toString(), null);
                if (StringUtils.isNotBlank(entityRep)) {
                    return "@" + entityRep.replaceAll("\\{ip\\}", app.getIp());
                }
            }
        } 
        
        return "@error";
        
        
        
    }
    /**
     * 供实例之间调用，真正生成数据的地方
     * @param inv
     * @return
     * @throws Exception
     */
    @Get("kfsp/remote/real")
    public String genRemoteSiteMapReal(Invocation inv) throws Exception {
        if (! checkAuth(inv, true)) {
            return "e:404";
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append(inv.getServletContext().getRealPath("/")).append(File.separatorChar).append("static").append(File.separatorChar).append("kfsp").append(File.separatorChar);
        String root = sb.toString();
        
        File rootDir = new File(root);
        
        StringBuilder ret = new StringBuilder();
        
        File[] newFiles = rootDir.listFiles();
        if (null != newFiles && newFiles.length > 0) {
            for(File file : newFiles) {
                ret.append("http://{ip}/static/kfsp/").append(file.getName()).append(System.getProperty("line.separator", "\r\n"));
            }
        }
        
        return "@" + ret.toString();
    }
    
    /**
     * 获取实例的IP列表
     * @return
     */
    private List<AppInstance> getEntityList() {
        List<AppInstance> instances = null;
        try {
            Credentials c = new Credentials(AppConstants.SCE_APP_ID, AppConstants.SCE_APP_SECRET);
            AppService service = ServiceFactory.getInstance();
            service.setEndPoint(AppConstants.SCE_API_ENDPOINT);
            instances = service.findInstances(c, Integer.parseInt(AppConstants.SCE_APP_ID));
        } catch (Exception e) {
            logger.error("", e);
        }
        return instances;
    }
    
    /**
     * 测试
     * @return
     * @throws Exception 
     * @throws  
     */
    @Get("kfsp/test/")
    public String test(Invocation inv) throws Exception {
        if (! checkAuth(inv, false)) {
            return "e:404";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(inv.getServletContext().getRealPath("/")).append(File.separatorChar).append("static").append(File.separatorChar).append("sitemap").append(File.separatorChar);
        String root = sb.toString();
        
        File rootDir = new File(root);
        File[] files = rootDir.listFiles();
        if (null != files && files.length > 0) {
            for (File file : files) {
                if (!file.delete()) {
                    throw new FilerException("can not delete file :" + file.getName());
                }
            }
        }
        
        String indexPath = root + "index.xml";
        System.out.println("索引文件路径为：" + indexPath);
        internalService.genLocalSiteMap(indexPath);
        return "@ok";
    }
    
    @Get({ "getallarea" })
    public String getAllArea(Invocation inv) throws ServletException{
        if (! checkAuth(inv, false)) {
            return "e:404";
        }
        List<DictCity> citys = cityService.getCityList();
        for(DictCity city : citys){
            Map<SearchType, Object> conditions = buildingSearchService.getConditions(city.getCityId(), true);
            List<DictDistrictExt> list = (List)conditions.get(SearchType.DISTRICT);
            for(DictDistrictExt d : list){
                System.out.println(d.getDistrictName()+","+ "http://m.focus.cn/"+city.getCityPinyinAbbr()+"/loupan/k"+d.getDistrictId()+"______/"+","+city.getCityName());
            }
        }
        return "@ok";
    }
    
    @Get({ "getallhot" })
    public String getAllHot(Invocation inv) throws ServletException{
        if (! checkAuth(inv, false)) {
            return "e:404";
        }
        List<DictCity> citys = cityService.getCityList();
        for(DictCity city : citys){
            Map<SearchType, Object> conditions = buildingSearchService.getConditions(city.getCityId(), true);
            List<DictAreaExt> dae = (List<DictAreaExt>) conditions.get(SearchType.HOT); 
            for(DictAreaExt d : dae){
                System.out.println(d.getAreaName()+","+ "http://m.focus.cn/"+city.getCityPinyinAbbr()+"/loupan/k___"+d.getAreaId()+"___/"+","+city.getCityName());  
            }
        }
        return "@ok";
    }
    
    @Get({ "getloupan" })
    public String getloupan(Invocation inv) throws ServletException{
        if (! checkAuth(inv, false)) {
            return "e:404";
        }
        List<DictCity> cityList = cityService.getCityList();
        int i = 0;
        for(DictCity d : cityList){            
            SearchCondition searchConditions =  new SearchCondition(null, 0, 10000, d.getCityId());
            //searchConditions.setSaleStatus(SaleStatus.ONSALE);
            Map<String, Object> buildingList = buildingSearchService.filterBuilding(searchConditions);
            List<EsProjInfoChild> epj = (List<EsProjInfoChild>) buildingList.get("buildingList");
            for(EsProjInfoChild c : epj){
                //if(c.getPhone400() != null && c.getPhone400() !=0){
                    i++;
                    Integer status = c.getSaleStatus();
                    String statusName = "";
                    switch (status) {
                    case 0:
                        statusName = "在售";
                        break;
                    case 1:
                        statusName = "待售";
                        break;
                    case 2:
                        statusName = "尾盘";
                        break;
                    case 3:
                        statusName = "售罄";
                        break;
                    default:
                        break;
                    }
                    System.out.println(d.getCityName()+"," + c.getProjName()+","+ "http://m.focus.cn/"+d.getCityPinyinAbbr()+"/loupan/"+c.getGroupId()+"/"+","+c.getDistrictName()+","+c.getPhone400()+","+statusName);
                //}
            }
        }
        System.out.println("total " + i);
        return null;
    }
    
    /**
     * 
     * @param inv
     * @param appInternal 是否是app之间的接口通讯
     * @return
     * @throws ServletException
     */
    private boolean checkAuth(Invocation inv, boolean appInternal) throws ServletException {
        String auth = ServletRequestUtils.getStringParameter(inv.getRequest(), "auth");
        if (appInternal) {
            return StringUtils.equals(APP_INTERNAL_AUTH, auth); 
        }
        return StringUtils.equals(INTERNAL_INTERFACE_AUTH, auth);
    }

}
