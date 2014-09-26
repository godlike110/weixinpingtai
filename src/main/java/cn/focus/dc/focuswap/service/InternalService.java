package cn.focus.dc.focuswap.service;

import cn.focus.dc.focuswap.config.AppConstants;
import cn.focus.dc.focuswap.config.QuestionStatusConstants;
import cn.focus.dc.focuswap.config.StatusConstants;
import cn.focus.dc.focuswap.model.DictCity;
import cn.focus.dc.focuswap.model.EsProjInfoChild;
import cn.focus.dc.focuswap.model.SearchCondition;
import cn.focus.dc.focuswap.service.InternalService.GQ;
import cn.focus.dc.focuswap.service.InternalService.HD;
import cn.focus.dc.focuswap.service.InternalService.ProposePM;

import com.alibaba.fastjson.util.IOUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.processing.FilerException;
import javax.servlet.ServletContext;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

import org.apache.commons.io.FileExistsException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ServletContextAware;

@Service
public class InternalService implements ServletContextAware{
    
    private static final Log logger = LogFactory.getLog(InternalService.class);
    
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
    private static String PLACE_HOLDER_FILE_NAME = "placeHolder";
    
    private static int MAX_RECORD_PER_FILE = 40000;
    
    private static String BUILDING_HOME_FILE_PREFIX = "loupan";
    
    //private static String BUILDING_PROPOSE_FILE_PREFIX = "daogou";
    
    private static String QUESTION_FILE_PREFIX = "zixun";
    
    private static String QUESTION_LIST_FILE_PREFIX = "zixunlist";
    
    private static String BAODIAN_FILE_PREFIX = "baodian";
    
    private static String KFSP_INDEX_PREFIX = "<sitemapindex  xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\">\r\n";
    
    private static String KFSP_INDEX_SUFFIX = "</sitemapindex>";
    
    private static String REG_FILE_PREFIX = "regpage";
    
    private static String XML_FILE_PREFIX = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n";
    
    private static String SITEMAP_CONTENT_PREFIX = "<urlset>\r\n";
    
    private static String SITEMAP_CONTENT_PREFIX2 = "<urlset xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\" xmlns:mobile=\"http://www.baidu.com/schemas/sitemap-mobile/1/\">\r\n";
    
    private static String SITEMAP_CONTENT_SUFFIX = "</urlset>";
    
    private MessageFormat buildingHomeMsgFormatter = new MessageFormat(
            "<url>\r\n" +
    		"<loc><![CDATA[http://{0}.focus.cn/votehouse/{1}.html]]></loc>\r\n" +
    		"<data>\r\n" +
    		"<display>\r\n" +
    		"<html5_url><![CDATA[http://" + AppConstants.SITE_MAP_HOST_NAME + "/{2}/loupan/{3}/]]></html5_url>\r\n" +
    		"</display>\r\n" +
    		"</data>\r\n" +
    		"</url>\r\n");
    
    private MessageFormat buildingProposeListMsgFormatter = new MessageFormat(
            "<url>\r\n" +
            "<loc><![CDATA[http://{0}.focus.cn/daogou/]]></loc>\r\n" +
            "<data>\r\n" +
            "<display>\r\n" +
            "<html5_url><![CDATA[http://" + AppConstants.SITE_MAP_HOST_NAME + "/{1}/daogou/]]></html5_url>\r\n" +
            "</display>\r\n" +
            "</data>\r\n" +
            "</url>\r\n");
    
    private MessageFormat buildingProposeMsgFormatter = new MessageFormat(
            "<url>\r\n" +
            "<loc><![CDATA[http://{0}.focus.cn/daogou/{1}.html]]></loc>\r\n" +
            "<data>\r\n" +
            "<display>\r\n" +
            "<html5_url><![CDATA[http://" + AppConstants.SITE_MAP_HOST_NAME + "/{2}/daogou/{3}/]]></html5_url>\r\n" +
            "</display>\r\n" +
            "</data>\r\n" +
            "</url>\r\n");
    
    private MessageFormat regFormatter = new MessageFormat(
            "<url>\r\n" +
            "<loc><![CDATA[http://{0}.focus.cn/]]></loc>\r\n" +
            "<data>\r\n" +
            "<display>\r\n" +
            "<pc_url_pattern><![CDATA[http://{0}.focus.cn/votehouse/(\\d+)/xiangqing/]]></pc_url_pattern>\r\n" +
            "<html5_url_pattern><![CDATA[http://" + AppConstants.SITE_MAP_HOST_NAME + "/{1}/loupan/$'{1}'/xiangxi/]]></html5_url_pattern>\r\n" +
            "</display>\r\n" +
            "</data>\r\n" +
            "</url>\r\n" +

            "<url>\r\n" +
            "<loc><![CDATA[http://{0}.focus.cn/]]></loc>\r\n" +
            "<data>\r\n" +
            "<display>\r\n" +
            "<pc_url_pattern><![CDATA[http://{0}.focus.cn/votehouse/(\\d+)/tu-11/]]></pc_url_pattern>\r\n" +
            "<html5_url_pattern><![CDATA[http://" + AppConstants.SITE_MAP_HOST_NAME + "/{1}/loupan/$'{1}'/huxingtu/-1/]]></html5_url_pattern>\r\n" +
            "</display>\r\n" +
            "</data>\r\n" +
            "</url>\r\n" +
            
            "<url>\r\n" +
            "<loc><![CDATA[http://{0}.focus.cn/]]></loc>\r\n" +
            "<data>\r\n" +
            "<display>\r\n" +
            "<pc_url_pattern><![CDATA[http://{0}.focus.cn/votehouse/(\\d+)/tu-10/]]></pc_url_pattern>\r\n" +
            "<html5_url_pattern><![CDATA[http://" + AppConstants.SITE_MAP_HOST_NAME + "/{1}/loupan/$'{1}'/tupian/-1/]]></html5_url_pattern>\r\n" +
            "</display>\r\n" +
            "</data>\r\n" +
            "</url>\r\n" +
            
            "<url>\r\n" +
            "<loc><![CDATA[http://{0}.focus.cn/]]></loc>\r\n" +
            "<data>\r\n" +
            "<display>\r\n" +
            "<pc_url_pattern><![CDATA[http://{0}.focus.cn/votehouse/(\\d+)/map/]]></pc_url_pattern>\r\n" +
            "<html5_url_pattern><![CDATA[http://" + AppConstants.SITE_MAP_HOST_NAME + "/{1}/loupan/$'{1}'/ditu/]]></html5_url_pattern>\r\n" +
            "</display>\r\n" +
            "</data>\r\n" +
            "</url>\r\n" +
            
            "<url>\r\n" +
            "<loc><![CDATA[http://{0}.focus.cn/]]></loc>\r\n" +
            "<data>\r\n" +
            "<display>\r\n" +
            "<pc_url_pattern><![CDATA[http://{0}.focus.cn/votehouse/(\\d+)/dongtai/]]></pc_url_pattern>\r\n" +
            "<html5_url_pattern><![CDATA[http://" + AppConstants.SITE_MAP_HOST_NAME + "/{1}/loupan/$'{1}'/dongtai/]]></html5_url_pattern>\r\n" +
            "</display>\r\n" +
            "</data>\r\n" +
            "</url>\r\n" +
            
            "<url>\r\n" +
            "<loc><![CDATA[http://{0}.focus.cn/]]></loc>\r\n" +
            "<data>\r\n" +
            "<display>\r\n" +
            "<pc_url_pattern><![CDATA[http://{0}.focus.cn/news/(\\d+)-(\\d+)-(\\d+)/(\\d+).html]]></pc_url_pattern>\r\n" +
            "<html5_url_pattern><![CDATA[http://" + AppConstants.SITE_MAP_HOST_NAME + "/{1}/zixun/$'{4}'/]]></html5_url_pattern>\r\n" +
            "</display>\r\n" +
            "</data>\r\n" +
            "</url>\r\n");
    
    private static MessageFormat indexFormatter = new MessageFormat(
            "<sitemap>\r\n" +
            "<loc>http://focus.cn/xml/{0}</loc>\r\n" +
            "<lastmod>{1}</lastmod>\r\n" +
            "</sitemap>\r\n");
    
    private MessageFormat questionFormatter = new MessageFormat(
            "<url>\r\n" + 
            "<loc>http://" + AppConstants.SITE_MAP_HOST_NAME + "/{0}/loupan/{1}/zixun/{2}/</loc>\r\n" +
            "<mobile:mobile type=\"mobile\"/>\r\n" +
            "<lastmod>{3}</lastmod>\r\n" +
            "</url>\r\n");
    
    private MessageFormat questionListFormatter = new MessageFormat(
            "<url>\r\n" + 
            "<loc>http://" + AppConstants.SITE_MAP_HOST_NAME + "/{0}/loupan/{1}/zixun/</loc>\r\n" +
            "<mobile:mobile type=\"mobile\"/>\r\n" +
            "<lastmod>{2}</lastmod>\r\n" +
            "</url>\r\n");
    
    private MessageFormat housingGuideFormatter = new MessageFormat(
            "<url>\r\n" + 
            "<loc>http://" + AppConstants.SITE_MAP_HOST_NAME + "/bj/baodian/{0}/</loc>\r\n" +
            "<mobile:mobile type=\"mobile\"/>\r\n" +
            "<lastmod>{1}</lastmod>\r\n" +
            "</url>\r\n");
    
    private MessageFormat housingGuideListFormatter = new MessageFormat(
            "<url>\r\n" + 
            "<loc>http://" + AppConstants.SITE_MAP_HOST_NAME + "/bj/baodian/</loc>\r\n" +
            "<mobile:mobile type=\"mobile\"/>\r\n" +
            "<lastmod>{0}</lastmod>\r\n" +
            "</url>\r\n");
    
    private MessageFormat localIndexFormatter = new MessageFormat(
            "<sitemap>\r\n" +
            "<loc>http://" + AppConstants.SITE_MAP_HOST_NAME + "/static/sitemap/{0}</loc>\r\n" +
            "<lastmod>{1}</lastmod>\r\n" +
            "</sitemap>\r\n");
    
    @Autowired
    private SearchService searchService;
    
    @Autowired
    private CityService cityService;
    
    @Autowired
    private BuildingProposeService buildingProposeService;
    
    @Autowired
    private InternalServiceDAO internalServiceDao;
    
    private ServletContext servletContext;
    
    @Override
    public void setServletContext(ServletContext servletContext) {
        // TODO Auto-generated method stub
        this.servletContext = servletContext;
    }
    
    /**
     * 生成sitemap的定时任务入口，该入口仅在应用启动时调用一次
     */
    public void cronJobOfSiteMap() {
        cronJobOfLocalSiteMap();
        cronJobOfGenRemoteSiteMap();
    }
    
    /**
     * 生成remote的sitemap
     * @throws Exception 
     */
    @Scheduled(cron="0 0 2 * * ?")
    public void cronJobOfGenRemoteSiteMap() {
        //System.out.println("开始生成remote的sitemap");
        logger.info("开始生成remote的sitemap");
        Long begin = System.currentTimeMillis();
        String webDir = servletContext.getRealPath("/");
        StringBuilder rootBuilder = new StringBuilder();
        rootBuilder.append(webDir).append(File.separatorChar).append("static").append(File.separatorChar).append("kfsp").append(File.separatorChar);
        String root = rootBuilder.toString();
        try {
            File rootDir = new File(root);
            File[] files = rootDir.listFiles();
            if (null != files && files.length > 0) {
                for (File file : files) {
                    if (file.getName().contains(PLACE_HOLDER_FILE_NAME)) {
                        continue;
                    }
                    if (!file.delete()) {
                        throw new FilerException("can not delete dir :" + file.getName());
                    }
                }
            }
            genRemoteSiteMap(root + "index.xml");
            //System.out.println("index.xml文件地址为：" + root + "index.xml");
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            //System.out.println("生成remote的sitemap耗时：" + (System.currentTimeMillis() - begin));
            logger.info("生成remote的sitemap耗时：" + (System.currentTimeMillis() - begin));
        }
    }
    
    /**
     * 生成local的sitemap
     */
    @Scheduled(cron="0 30 2 * * ?")
    public void cronJobOfLocalSiteMap() {
        //System.out.println("开始生成local的sitemap");
        logger.info("开始生成local的sitemap");
        Long begin = System.currentTimeMillis();
        String webDir = servletContext.getRealPath("/");
        StringBuilder rootBuilder = new StringBuilder();
        rootBuilder.append(webDir).append(File.separatorChar).append("static").append(File.separatorChar).append("sitemap").append(File.separatorChar);
        String root = rootBuilder.toString();
        try {
            File rootDir = new File(root);
            File[] files = rootDir.listFiles();
            if (null != files && files.length > 0) {
                for (File file : files) {
                    if (file.getName().contains(PLACE_HOLDER_FILE_NAME)) {
                        continue;
                    }
                    if (!file.delete()) {
                        throw new FilerException("can not delete dir :" + file.getName());
                    }
                }
            }
            genLocalSiteMap(root + "index.xml");
            //System.out.println("index.xml文件地址为：" + root + "index.html");
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            //System.out.println("生成local的sitemap耗时：" + (System.currentTimeMillis() - begin));
            logger.info("生成local的sitemap耗时：" + (System.currentTimeMillis() - begin));
        }
    }
    
    /**
     * 调用此接口前要确保file所在目录已经清空。
     * @param file
     * @return
     * @throws Exception
     */
    public String genRemoteSiteMap(String file) throws Exception {
        File f = new File(file);
        if (f.exists()) {
            throw new FileExistsException(f.getCanonicalPath());
        }
        
        String root  = f.getCanonicalFile().getParent();
        if (!root.endsWith(File.separator)){
            root = root + File.separator;
        }
        
        String[] buildingHomeFiles = genBuildingHomeSiteMap(root + BUILDING_HOME_FILE_PREFIX).split(",");
        //String[] buildingProposeFiles = genBuildingProposeSiteMap(root + BUILDING_PROPOSE_FILE_PREFIX).split(",");
        //String[] regPageFiles = genRegSiteMap(root + REG_FILE_PREFIX).split(",");
        genRegSiteMap(root + REG_FILE_PREFIX).split(",");
        
        String lastmod = sdf.format(new Date());
        
        BufferedWriter bw = new BufferedWriter(new FileWriter(f));
        try {
            bw.write(XML_FILE_PREFIX);
            bw.write(KFSP_INDEX_PREFIX);
            
            for (String buildingHomeFile : buildingHomeFiles) {
                if (StringUtils.isNotBlank(buildingHomeFile)) {
                    String paragraph = indexFormatter.format(new String[] {buildingHomeFile, lastmod});
                    bw.write(paragraph);
                }
            }
            bw.flush();
            
            /*for (String buildingProposeFile : buildingProposeFiles) {
                if (StringUtils.isNotBlank(buildingProposeFile)) {
                    String paragraph = indexFormatter.format(new String[] {buildingProposeFile, lastmod});
                    bw.write(paragraph);
                }
            }
            bw.flush();*/
            
            /*for (String regPageFile : regPageFiles) {
                if (StringUtils.isNotBlank(regPageFile)) {
                    String paragraph = indexFormatter.format(new String[] {regPageFile, lastmod});
                    bw.write(paragraph);
                }
            }*/
            bw.write(KFSP_INDEX_SUFFIX);
            bw.flush();
        } finally  {
            IOUtils.close(bw);
        }
        
        return f.getName();
    }
    
    /**
     * 生成本地的sitemap，调用此接口前确保file所在目录已清空
     * @param file
     * @return
     * @throws Exception 
     * @throws FileExistsException 
     */
    public String genLocalSiteMap(String file) throws FileExistsException, Exception {
        File f = new File(file);
        if (f.exists()) {
            throw new FileExistsException(f.getCanonicalPath());
        }
        
        String root  = f.getCanonicalFile().getParent();
        if (!root.endsWith(File.separator)){
            root = root + File.separator;
        }
        
        Map<Integer, String> cityMap = this.getCityMap();
        List<GQ> gqs = this.genQuestionSiteMapData();
        
        String[] questionsFiles = this.genQuestionsSiteMap(root + QUESTION_FILE_PREFIX, cityMap, gqs).split(",");
        String[] questionListFiles = this.genQuestionListSiteMap(root + QUESTION_LIST_FILE_PREFIX, cityMap, gqs).split(",");
        String[] baodianFiles = this.genBaoDiansSiteMap(root + BAODIAN_FILE_PREFIX).split(",");
        
        String lastmod = sdf.format(new Date());
        BufferedWriter bw = new BufferedWriter(new FileWriter(f));
        try {
            bw.write(XML_FILE_PREFIX);
            bw.write(KFSP_INDEX_PREFIX);
            for (String questionsFile : questionsFiles) {
                if (StringUtils.isNotBlank(questionsFile)) {
                    String paragraph = localIndexFormatter.format(new String[]{questionsFile, lastmod});
                    bw.write(paragraph);
                }
            }
            
            for (String questionListFile : questionListFiles) {
                if (StringUtils.isNotBlank(questionListFile)) {
                    String paragraph = localIndexFormatter.format(new String[]{questionListFile, lastmod});
                    bw.write(paragraph);
                }
            }
            
            for (String baodianFile : baodianFiles) {
                if (StringUtils.isNotBlank(baodianFile)) {
                    Date lastUpdateTime = internalServiceDao.getNewestTimeOfHousingGuide();
                    String paragraph = localIndexFormatter.format(new String[]{baodianFile, sdf.format(lastUpdateTime)});
                    bw.write(paragraph);
                }
            }
            bw.write(KFSP_INDEX_SUFFIX);
            bw.flush();
        } finally {
            IOUtils.close(bw);
        }
        
        return f.getName();
    }
    
    /**
     * 生成楼盘的开放适配文件
     * @return 返回文件路径
     * @throws Exception 
     */
    public String genBuildingHomeSiteMap(String file) throws Exception {
        StringBuilder sb = new StringBuilder();
        
        int index = 1;
        
        File f = new File(file + "-" + index);
        /*if (f.exists()) {
            f = new File(file + "-" + RandomUtils.nextInt() + "-" + index);
        }*/
        
        index ++;
        
        List<DictCity> citys = cityService.getCityList();
        Map<DictCity, List<GB>> gbs = new HashMap<DictCity, List<GB>>();
        for(DictCity city : citys) {
            //if (city.getCityId() == 46) {
                gbs.put(city, genBuildingHomeSiteMapByCity(city));
            //}
        }

        BufferedWriter bw = new BufferedWriter(new FileWriter(f.getCanonicalPath() + ".xml"));
        try {
            bw.write(XML_FILE_PREFIX);
            bw.write(SITEMAP_CONTENT_PREFIX);
            int count = 0;
            int paraRecord = 0;
            Set<Entry<DictCity, List<GB>>> entrySet = gbs.entrySet();
            for (Entry<DictCity, List<GB>> entry : entrySet) {
                List<GB> gbList = entry.getValue();
                String cityAbbr = entry.getKey().getCityPinyinAbbr();
                String pcCityAbbr = entry.getKey().getCityPinyinAbbr();
                if (StringUtils.equals("bj", pcCityAbbr)) {
                    pcCityAbbr = "house";
                }
                if (null != gbList && gbList.size() > 0) {
                    Iterator<GB> it = gbList.iterator();
                    while(it.hasNext()) {
                        count ++;
                        paraRecord ++;
                        GB gb = it.next();
                        String paragraph = buildingHomeMsgFormatter.format(new String[]{pcCityAbbr, String.valueOf(gb.getBuildId()), cityAbbr, String.valueOf(gb.getGroupId())});
                        bw.write(paragraph);
                        bw.flush();
                        if (paraRecord >= MAX_RECORD_PER_FILE) {
                            bw.write(SITEMAP_CONTENT_SUFFIX);
                            bw.flush();
                            IOUtils.close(bw);
                            String fName = f.getName() + ".xml";
                            sb.append(fName).append(",");
                            String fPath = f.getCanonicalPath();
                            String nextFPath = fPath.substring(0, fPath.lastIndexOf("-")) + "-" + index++;
                            f = new File(nextFPath);
                            bw = new BufferedWriter(new FileWriter(f.getCanonicalPath() + ".xml"));
                            bw.write(XML_FILE_PREFIX);
                            bw.write(SITEMAP_CONTENT_PREFIX);
                            paraRecord = 0;
                        }
                    }
                }
                
            }

            if (paraRecord > 0) {
                bw.write(SITEMAP_CONTENT_SUFFIX);
                bw.flush();
                sb.append(f.getName() + ".xml");
            }
            logger.info("楼盘首页sitemap生成，共计：" + count + " 条记录");
        } finally {
            IOUtils.close(bw);
        }
        
        return sb.toString();
    }
    
    /**
     * 生成导购的sitemap文件
     * @param file
     * @return
     * @throws Exception
     */
    public String genBuildingProposeSiteMap(String file) throws Exception {
        StringBuilder sb = new StringBuilder();
        
        int index = 1;
        File f = new File(file + "-" + index);
        /*if (f.exists()) {
            f = new File(file + "-" + RandomUtils.nextInt() + "-" + index);
        }*/
        
        index ++;
        
        List<DictCity> citys = cityService.getCityList();
        Map<DictCity, List<ProposePM>> pms = new HashMap<DictCity, List<ProposePM>>();
        for (DictCity city : citys) {
            //if (1 == city.getCityId()) {
            pms.put(city, genBuildingProposeSiteMapByCity(city));
            //}
        }
        
        BufferedWriter bw = new BufferedWriter(new FileWriter(f.getCanonicalPath() + ".xml"));
        try {
            bw.write(XML_FILE_PREFIX);
            bw.write(SITEMAP_CONTENT_PREFIX);
            int count = 0;
            int paraRecord = 0;
            Set<Entry<DictCity, List<ProposePM>>> entrySet = pms.entrySet();
            for (Entry<DictCity, List<ProposePM>> entry : entrySet) {
                List<ProposePM> pmList = entry.getValue();
                if (null != pmList && pmList.size() > 0) {
                    String cityAbbr = entry.getKey().getCityPinyinAbbr();
                    String pcCityAbbr = entry.getKey().getCityPinyinAbbr();
                    if (StringUtils.equals("bj", pcCityAbbr)) {
                        pcCityAbbr = "house";
                    }
                    String listParagraph = buildingProposeListMsgFormatter.format(new String[]{pcCityAbbr, cityAbbr});
                    bw.write(listParagraph);
                    Iterator<ProposePM> it = pmList.iterator();
                    while (it.hasNext()) {
                        count ++;
                        paraRecord ++;
                        ProposePM pm = it.next();
                        String paragraph = buildingProposeMsgFormatter.format(new String[]{pcCityAbbr, String.valueOf(pm.getPcId()), cityAbbr, String.valueOf(pm.getId())});
                        bw.write(paragraph);
                        bw.flush();
                        if (paraRecord >= MAX_RECORD_PER_FILE) {
                            bw.write(SITEMAP_CONTENT_SUFFIX);
                            bw.flush();
                            IOUtils.close(bw);
                            String fName = f.getName() + ".xml";
                            sb.append(fName).append(",");
                            String fPath = f.getCanonicalPath();
                            String nextFPath = fPath.substring(0, fPath.lastIndexOf("-")) + "-" + index++;
                            f = new File(nextFPath);
                            bw = new BufferedWriter(new FileWriter(f.getCanonicalPath() + ".xml"));
                            bw.write(XML_FILE_PREFIX);
                            bw.write(SITEMAP_CONTENT_PREFIX);
                            paraRecord = 0;
                        }
                    }
                }
            }
            if (paraRecord > 0) {
                bw.write(SITEMAP_CONTENT_SUFFIX);
                bw.flush();
                sb.append(f.getName() + ".xml");
            }
            logger.info("导购sitemap生成，共计：" + count + " 条记录");
        } finally {
            IOUtils.close(bw);
        }
        
        return sb.toString();
    }
    
    /**
     * 生成PC和WAP正则匹配的适配文件
     * @param file
     * @return
     * @throws Exception 
     */
    public String genRegSiteMap(String file) throws Exception {
        File f = new File(file + "-1");
        /*if (f.exists()) {
            f = new File(file + "-" + RandomUtils.nextInt() + "-1");
        }*/
        
        List<DictCity> citys = cityService.getCityList();
        
        BufferedWriter bw = new BufferedWriter(new FileWriter(f.getCanonicalPath() + ".xml"));
        try {
            bw.write(XML_FILE_PREFIX);
            bw.write(SITEMAP_CONTENT_PREFIX);
            int count = 0;
            for (DictCity city : citys) {
                String cityAbbr = city.getCityPinyinAbbr();
                String pcCityAbbr = city.getCityPinyinAbbr();
                if (StringUtils.equals("bj", pcCityAbbr)) {
                    pcCityAbbr = "house";
                }
                count ++;
                String paragraph = regFormatter.format(new String[]{pcCityAbbr, cityAbbr});
                bw.write(paragraph);
                bw.flush();
            }
            bw.write(SITEMAP_CONTENT_SUFFIX);
            bw.flush();
            logger.info("正则匹配生成：" + count + " 个城市的记录");
        } finally {
            IOUtils.close(bw);
        }
        return f.getName() + ".xml";
    }
    
    /**
     * 生成问答的sitemap
     * @param file
     * @param cityMap
     * @param gqs
     * @return
     * @throws Exception
     */
    public String genQuestionsSiteMap(String file, Map<Integer, String> cityMap, List<GQ> gqs) throws Exception {
        StringBuilder sb = new StringBuilder();
        
        int index = 1;
        File f = new File(file + "-" + index);
        index ++;
        
        if (null == cityMap) {
            cityMap = getCityMap();
        }
        
        //List<GQ> gqs = genQuestionSiteMapData();
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        BufferedWriter bw = new BufferedWriter(new FileWriter(f.getCanonicalPath() + ".xml"));
        try {
            bw.write(XML_FILE_PREFIX);
            bw.write(SITEMAP_CONTENT_PREFIX2);
            int count = 0;
            int paraRecord = 0;
            Iterator<GQ> it = gqs.iterator();
            while(it.hasNext()) {
                GQ gq = it.next();
                if (!cityMap.containsKey(gq.getCityId()) || null == gq.getGroupId()){
                    continue;
                }
                count ++;
                paraRecord ++;
                String paragraph = questionFormatter.format(new String[]{cityMap.get(gq.getCityId()), String.valueOf(gq.getGroupId()), String.valueOf(gq.getId()), sdf.format(gq.getUpdateTime())});
                bw.write(paragraph);
                bw.flush();
                if (paraRecord >= MAX_RECORD_PER_FILE) {
                    bw.write(SITEMAP_CONTENT_SUFFIX);
                    bw.flush();
                    IOUtils.close(bw);
                    String fName = f.getName() + ".xml";
                    sb.append(fName).append(",");
                    String fPath = f.getCanonicalPath();
                    String nextFPath = fPath.substring(0, fPath.lastIndexOf("-")) + "-" + index++;
                    f = new File(nextFPath);
                    bw = new BufferedWriter(new FileWriter(f.getCanonicalPath() + ".xml"));
                    bw.write(XML_FILE_PREFIX);
                    bw.write(SITEMAP_CONTENT_PREFIX2);
                    paraRecord = 0;
                }
            }
            if (paraRecord > 0) {
                bw.write(SITEMAP_CONTENT_SUFFIX);
                bw.flush();
                sb.append(f.getName() + ".xml");
            }
            logger.info("咨询sitemap生成，共计：" + count + " 条记录");
        } finally {
            IOUtils.close(bw);
        }
        return sb.toString();
    }
    
    /**
     * 生成问答列表的sitemap
     * @param file
     * @param cityMap
     * @param gqs
     * @return
     * @throws Exception
     */
    public String genQuestionListSiteMap(String file, Map<Integer, String> cityMap, List<GQ>gqs) throws Exception{
        StringBuilder sb = new StringBuilder();
        
        int index = 1;
        File f = new File(file + "-" + index);
        index ++;
        
        if (null == cityMap) {
            cityMap = getCityMap();
        }
        
        Map<Integer, GQ> gqMap = new HashMap<Integer, GQ>();
        for(GQ gq : gqs) {
            GQ oldGQ = gqMap.get(gq.getGroupId());
            if (null != oldGQ) {
                Date oldDate = oldGQ.getUpdateTime();
                if (gq.getUpdateTime().compareTo(oldDate) > 0) {
                    gqMap.put(gq.getGroupId(), gq);
                }
            } else {
                gqMap.put(gq.getGroupId(), gq);
            }
        }
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        BufferedWriter bw = new BufferedWriter(new FileWriter(f.getCanonicalPath() + ".xml"));
        try {
            bw.write(XML_FILE_PREFIX);
            bw.write(SITEMAP_CONTENT_PREFIX2);
            int count = 0;
            int paraRecord = 0;
            Set<Entry<Integer, GQ>> entrys = gqMap.entrySet();
            Iterator<Entry<Integer, GQ>> it = entrys.iterator();
            while (it.hasNext()) {
                GQ gq = it.next().getValue();
                if (!cityMap.containsKey(gq.getCityId()) || null == gq.getGroupId()){
                    continue;
                }
                count ++;
                paraRecord ++;
                String paragraph = questionListFormatter.format(new String[]{cityMap.get(gq.getCityId()), String.valueOf(gq.getGroupId()), sdf.format(gq.getUpdateTime())});
                bw.write(paragraph);
                bw.flush();
                if (paraRecord >= MAX_RECORD_PER_FILE) {
                    bw.write(SITEMAP_CONTENT_SUFFIX);
                    bw.flush();
                    IOUtils.close(bw);
                    String fName = f.getName() + ".xml";
                    sb.append(fName).append(",");
                    String fPath = f.getCanonicalPath();
                    String nextFPath = fPath.substring(0, fPath.lastIndexOf("-")) + "-" + index++;
                    f = new File(nextFPath);
                    bw = new BufferedWriter(new FileWriter(f.getCanonicalPath() + ".xml"));
                    bw.write(XML_FILE_PREFIX);
                    bw.write(SITEMAP_CONTENT_PREFIX2);
                    paraRecord = 0;
                } 
            }
            if (paraRecord > 0) {
                bw.write(SITEMAP_CONTENT_SUFFIX);
                bw.flush();
                sb.append(f.getName() + ".xml");
            }
            logger.info("咨询列表sitemap生成，共计：" + count + " 条记录");
        } finally {
            IOUtils.close(bw);
        }
        
        return sb.toString();
    }
    
    public String genBaoDiansSiteMap(String file) throws Exception {
        StringBuilder sb = new StringBuilder();
        
        int index = 1;
        File f = new File(file + "-" + index);
        index ++;
        
        List<HD> housingGuideList = genHousingGuideSiteMapData();
        Date newestDate = internalServiceDao.getNewestTimeOfHousingGuide();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        BufferedWriter bw = new BufferedWriter(new FileWriter(f.getCanonicalPath() + ".xml"));
        try {
            bw.write(XML_FILE_PREFIX);
            bw.write(SITEMAP_CONTENT_PREFIX2);
            String listParagraph = housingGuideListFormatter.format(new String[]{sdf.format(newestDate)});
            bw.write(listParagraph);
            int count = 1;
            int paraRecord = 1;
            Iterator<HD> it = housingGuideList.iterator();
            while (it.hasNext()) {
                HD hd = it.next();
                count ++;
                paraRecord ++;
                String paragraph = housingGuideFormatter.format(new String[] {String.valueOf(hd.getId()), sdf.format(hd.getCreateTime())});
                bw.write(paragraph);
                bw.flush();
                if (paraRecord >= MAX_RECORD_PER_FILE) {
                    bw.write(SITEMAP_CONTENT_SUFFIX);
                    bw.flush();
                    IOUtils.close(bw);
                    String fName = f.getName() + ".xml";
                    sb.append(fName).append(",");
                    String fPath = f.getCanonicalPath();
                    String nextFPath = fPath.substring(0, fPath.lastIndexOf("-")) + "-" + index++;
                    f = new File(nextFPath);
                    bw = new BufferedWriter(new FileWriter(f.getCanonicalPath() + ".xml"));
                    bw.write(XML_FILE_PREFIX);
                    bw.write(SITEMAP_CONTENT_PREFIX2);
                    paraRecord = 0;
                }
            }
            if (paraRecord > 0) {
                bw.write(SITEMAP_CONTENT_SUFFIX);
                bw.flush();
                sb.append(f.getName() + ".xml");
            }
            logger.info("宝典列表sitemap生成，共计：" + count + " 条记录");
        } finally {
            IOUtils.close(bw);
        }
        
        
        
        return sb.toString();
    }
    
    /**
     * 返回以cityId为key，cityPinyinAbbr为value的map
     * @return
     */
    private Map<Integer, String> getCityMap() {
        Map<Integer, String> ret = new HashMap<Integer, String>();
        List<DictCity> citys = cityService.getCityList();
        for (DictCity city : citys) {
            ret.put(city.getCityId(), city.getCityPinyinAbbr());
        }
        return ret;
    }
    
    /**
     * 生成某一个城市的groupId和buildingId对应表,分页获取，每次获取100条
     * @param city
     * @return
     */
    @SuppressWarnings("unchecked")
    private List<GB> genBuildingHomeSiteMapByCity(DictCity city) {
        
        List<GB> ret = new LinkedList<GB>();
        
        boolean hasNext = true;
        int pageNo = 0;
        do {
            pageNo ++;
            SearchCondition conditon = new SearchCondition(null ,pageNo, 100, city.getCityId());
            Map<String, Object> result = searchService.projSearch(conditon);
            List<EsProjInfoChild> epjs = (List<EsProjInfoChild>)result.get("buildingList");
            hasNext = (Boolean) result.get("hasNext");
            for(EsProjInfoChild prj : epjs) {
                ret.add(new GB(prj.getGroupId(), prj.getProjId()));
            }
        } while(hasNext);
        return ret;
    }
    
    /**
     * groupId和buildId对应关系
     * @author rogantian
     * @date 2014-5-5
     * @email rogantianwz@gmail.com
     */
    public static class GB {
        private Integer groupId;
        
        private Integer buildId;
        
        public GB(Integer groupId, Integer buildId) {
            super();
            this.groupId = groupId;
            this.buildId = buildId;
        }

        public Integer getGroupId() {
            return groupId;
        }

        public void setGroupId(Integer groupId) {
            this.groupId = groupId;
        }

        public Integer getBuildId() {
            return buildId;
        }

        public void setBuildId(Integer buildId) {
            this.buildId = buildId;
        }
        
    }
    
    /**
     * 生成某一城市的导购的pcId和mobileId对应关系
     * @param city
     * @return
     */
    private List<ProposePM> genBuildingProposeSiteMapByCity(DictCity city) {
        
        List<ProposePM> ret = new LinkedList<ProposePM>();
        
        boolean hasNext = true;
        int pageNo = 0;
        do {
            pageNo ++;
            //一次查100条，暂定
            List<ProposePM> queryResult = internalServiceDao.getProposeMap(city.getCityId(), (pageNo - 1) * 100, 100);
            if (null != queryResult && queryResult.size() != 0) {
                ret.addAll(queryResult);
            } else {
                hasNext = false;
            }
            
        } while (hasNext);
        //System.out.println(city.getCityName() + "(" + city.getCityId() + ")有映射关系" + ret.size() + "条数据");
        return ret;
    }
    
    /**
     * pc的导购Id和mobile的导购Id对应关系
     * @author rogantian
     * @date 2014-5-5
     * @email rogantianwz@gmail.com
     */
    public static class ProposePM {
        
        private Integer pcId;
        
        private Integer id;

        public ProposePM() {
        }

        public ProposePM(Integer pcId, Integer id) {
            this.pcId = pcId;
            this.id = id;
        }

        public Integer getPcId() {
            return pcId;
        }

        public void setPcId(Integer pcId) {
            this.pcId = pcId;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }
    
    }
    
    private List<GQ> genQuestionSiteMapData() {
        //Integer total = questionDao.getTotal();
        List<GQ> ret = new LinkedList<GQ>();
        boolean hasNext = true;
        int pageNo = 0;
        do {
            pageNo ++;
            //一次查300条，暂定
            List<GQ> queryResult = internalServiceDao.getSimpleList((pageNo - 1) * 300, 300);
            if (null != queryResult && queryResult.size() != 0) {
                ret.addAll(queryResult);
            } else {
                hasNext = false;
            }
        } while (hasNext);
        
        return ret;
    }
    
    /**
     * 楼盘ID和问答ID关系
     * @author rogantian
     * @date 2014-5-9
     * @email rogantianwz@gmail.com
     */
    public static class GQ {
        private Long id;
        
        private Integer groupId;
        
        private Integer cityId;
        
        private Date updateTime;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Integer getGroupId() {
            return groupId;
        }

        public void setGroupId(Integer groupId) {
            this.groupId = groupId;
        }

        public Integer getCityId() {
            return cityId;
        }

        public void setCityId(Integer cityId) {
            this.cityId = cityId;
        }

        public Date getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(Date updateTime) {
            this.updateTime = updateTime;
        }

    }
    
    private List<HD> genHousingGuideSiteMapData() {
        LinkedList<HD> housingGuideList = new LinkedList<HD>();
        boolean hasNext = true;
        int pageNo = 0;
        do {
            pageNo ++;
            //每次查询300条
            List<HD> response = internalServiceDao.getSimpleHousingGuide((pageNo -1) * 300, 300);
            if (null != response && response.size() > 0) {
                housingGuideList.addAll(response);
            } else {
                hasNext = false;
            }
        } while(hasNext);
        
        return housingGuideList;
    }
 
    /**
     * 宝典id和updatetime
     * @author rogantian
     * @date 2014-5-12
     * @email rogantianwz@gmail.com
     */
    public static class HD {
        private Integer id;
        
        private Date createTime;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Date getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Date createTime) {
            this.createTime = createTime;
        }

    }

}

@DAO(catalog = "xinfang")
interface InternalServiceDAO {

    /**
     * 根据城市id获取导购在pc和mobile的映射关系
     * @param cityId
     * @return
     */
    @SQL("select id, pc_id from build_propose where city_id = :1 and pc_id is not null limit :2, :3")
    List<ProposePM> getProposeMap(Integer cityId, Integer offset, Integer pageSize);
    
    /**
     * 获取宝典列表的id
     * @param offset
     * @param pageSize
     * @return
     */
    @SQL("select id, create_time from housing_guide where status=" + StatusConstants.COMMITED  + " order by create_time desc limit :1,:2")
    List<HD> getSimpleHousingGuide(Integer offset, Integer pageSize);
    
    /**
     * 获取最新发布的宝典的时间
     * @return
     */
    @SQL("select create_time from housing_guide where status=" + StatusConstants.COMMITED + " order by create_time desc limit 1")
    Date getNewestTimeOfHousingGuide();
    
    /**
     * 分页获取楼盘Id、问答Id、cityId和updateTime, 这里是给制作sitemap使用的
     * @param cityId
     * @param offset
     * @param pageSize
     * @return
     */
    @SQL("select id, group_id, city_id, update_time from question where status >= " + QuestionStatusConstants.QA_Q_PASS_NO_ANSWER + " order by update_time desc limit :1,:2")
    List<GQ> getSimpleList(int offset, int pageSize);
    
    /**
     * 获取可以在页面上展示的问答的总条数，这里是给制作sitemap使用的
     * @return
     */
    @SQL("select count(id) from question where status >= " + QuestionStatusConstants.QA_Q_PASS_NO_ANSWER)
    Integer getTotal();

}
