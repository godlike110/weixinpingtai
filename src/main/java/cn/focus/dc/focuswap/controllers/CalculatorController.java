package cn.focus.dc.focuswap.controllers;


import static cn.focus.dc.focuswap.config.AppConstants.HOME_BUILDING_LIST_PAGENO;
import static cn.focus.dc.focuswap.config.AppConstants.HOME_BUILDING_LIST_PAGESIZE;
import static cn.focus.dc.focuswap.config.AppConstants.FOCUS_WAP_REDIS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.DefValue;
import net.paoding.rose.web.annotation.Ignored;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import cn.focus.dc.focuswap.config.AppConstants;
import cn.focus.dc.focuswap.model.Condition;
import cn.focus.dc.focuswap.model.DictCity;
import cn.focus.dc.focuswap.model.DictCityPriceExt;
import cn.focus.dc.focuswap.model.DictDistrictExt;
import cn.focus.dc.focuswap.model.EsProjInfoChild;
import cn.focus.dc.focuswap.model.QueryData;
import cn.focus.dc.focuswap.model.SearchCondition;
import cn.focus.dc.focuswap.service.BuildingSearchService;
import cn.focus.dc.focuswap.service.BuildingService;
import cn.focus.dc.focuswap.service.BuyHouseService;
import cn.focus.dc.focuswap.service.ChooseSubJsonFilterService;
import cn.focus.dc.focuswap.service.CityService;
import cn.focus.dc.focuswap.service.RedisHandlerService;
import cn.focus.dc.focuswap.service.SearchService.SearchType;
import cn.focus.dc.focuswap.utils.JsonResponseUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.sohu.sce.repackaged.net.rubyeye.xmemcached.utils.ByteUtils;

@Path("calculator")
public class CalculatorController {
    
    @Autowired
    private CityService cityService;

    @Autowired
    private BuildingSearchService buildingSearchService;
    
    @Autowired
    private BuyHouseService buyHouseService;
    
    @Autowired
    private BuildingService buildingService;

    @Autowired
    private RedisHandlerService redisHandlerService;
    
    private static enum CalType{
        //商业贷款
        SYDK,
        //公积金贷款
        GJJDK,
        //组合贷款
        ZHDK;
    }
    
    /**
     * 获取全国的城市以及地区
     * @param inv
     * @param callback
     * @return
     */
    @Get("/getAreaAjax")
    public String getAreaAjax(Invocation inv,@Param("callback") String callback){
        List<Map> result = new ArrayList<Map>();
        List<DictCity> citys = cityService.getCityList();
        for(DictCity city : citys){
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("cityId", city.getCityId());
            map.put("cityName", city.getCityName());
            Map<SearchType, Object> conditionMap = buildingSearchService.getConditions(city.getCityId(), false);            
            List<DictDistrictExt> distList = (List<DictDistrictExt>) conditionMap.get(SearchType.DISTRICT);
            map.put("district", distList);
            result.add(map);
        }
        // 获取区域条件
        return JsonResponseUtil.jsonp(result, callback);
    }
    
    @Get("/test")
    @Ignored
    public String test(Invocation inv,@Param("callback") String callback,@Param("cityId") @DefValue("1") Integer cityId,@Param(AppConstants.PAGE_NO) @DefValue("1") Integer pageNo,
            @Param(AppConstants.PAGE_SIZE) @DefValue("10") Integer pageSize){
        DictCity city = cityService.getCity(cityId);
        
        SearchCondition searchConditions = new SearchCondition(null, pageNo, pageSize, city.getCityId());
        //searchConditions.setVilla(true);
        //searchConditions.setSanDaiTongTang(true);
        //searchConditions.setEdu(true);
        //searchConditions.setLowTotalPrice(true);
        //searchConditions.setPark(true);
        //searchConditions.setMoonHouse(true);
        Map<String, Object> buildingList = buildingSearchService.filterBuilding(searchConditions);
        return JsonResponseUtil.jsonp(buildingList, callback);
    }
    
    /**
     * 根据价格以及区域获取楼盘列表，如没有取消区域，如再没有最新开盘
     * @param inv
     * @param cityId
     * @param callback
     * @param pageNo
     * @param pageSize
     * @param price
     * @param area
     * @return
     */
    @Get("/getBuildingListAjax")
    public String getBuildingListAjax(Invocation inv,@Param("cityId") @DefValue("1") Integer cityId,@Param("callback") String callback,@Param(AppConstants.PAGE_NO) @DefValue("1") Integer pageNo,
            @Param(AppConstants.PAGE_SIZE) @DefValue("2") Integer pageSize,@Param("price") @DefValue("0") Integer price,@Param("area") @DefValue("") Integer area){
        DictCity city = cityService.getCity(cityId); 
        Map<SearchType, Object> conditionMap = buildingSearchService.getConditions(city.getCityId(), false);
        // 获取价格条件
        List<DictCityPriceExt> priceList = (List<DictCityPriceExt>) conditionMap.get(SearchType.PRICE);
        String priceShow = "";
        for(DictCityPriceExt ext : priceList){           
            String value = ext.getCondValue();
            String[] limits = StringUtils.split(value, "-");
            if (null != limits && limits.length == 2) {
                int from = Integer.valueOf(limits[0]);
                int to = Integer.valueOf(limits[1]);
                if(price > from && price < to){
                    priceShow = value;
                    break;
                }
            }
        }
        String con = "k__"+priceShow+"____/";
        if(area != null){
            con = "k"+area+"__"+priceShow+"____/";
        }
        List<QueryData> list = null;
        Condition chosen = new Condition(con);
        list = chosen.combined();
        
        
        SearchCondition searchConditions = new SearchCondition(list, pageNo, pageSize, city.getCityId());
        searchConditions.setNotEnsureHouse(true);
        searchConditions.setNotSelloutHouse(true);
        Map<String, Object> buildingList = buildingSearchService.filterBuilding(searchConditions);
        if(buildingList == null || buildingList.size() == 0){
            con = "k__"+priceShow+"____/";
            chosen = new Condition(con);
            list = chosen.combined();
            buildingList = buildingSearchService.filterBuilding(searchConditions);
        }
        if(buildingList == null || buildingList.size() == 0){
            searchConditions = buyHouseService.getHomeHouseCondition(
                    city.getCityId(), HOME_BUILDING_LIST_PAGENO, HOME_BUILDING_LIST_PAGESIZE, "ZXKP", "");
            buildingList = buildingSearchService.filterBuilding(searchConditions);
        }
        List<EsProjInfoChild> epj = (List<EsProjInfoChild>) buildingList.get("buildingList");
        epj = buyHouseService.switchBuildingParameter(epj, city,0);
        
        /**
         * @author linfangwang
         * 截字
         */
        for(EsProjInfoChild espj:epj) {
        	//楼盘名
        	if(espj.getProjName().length()>7) {
        		espj.setProjName(espj.getProjName().substring(0, 7) + "...");
        	}
        	//地址
        	if (espj.getProjAddress().length() > 14) {
        		espj.setProjAddress(espj.getProjAddress().substring(0, 14) + "...");
            }
        	//户型
        	if(espj.getRoomInfo().length() > 14) {
        		espj.setRoomInfo(espj.getRoomInfo().substring(0, 14)+"...");
        	}
        }
        
        
        
        //减少输出项
        String sub = JSON.toJSONString(epj, ChooseSubJsonFilterService.getInstance());
        JSONArray json = JSONArray.parseArray(sub);
        buildingList.put("buildingList", json);
        return JsonResponseUtil.jsonp(buildingList, callback);
    }
    
    /**
     * 有用数+1
     * @param inv
     * @param type
     * @return
     */
    @Post("/pluscount")
    public String pluscount(Invocation inv,@Param("type") @DefValue("1") Integer type){
        if(type <= CalType.values().length){
            redisHandlerService.incr(ByteUtils.getBytes(FOCUS_WAP_REDIS+type));
        }
        return null;
    }
    
    /**
     * 获取有用数
     * @param inv
     * @param type
     * @param callback
     * @return
     */
    @Get("/getcount")
    public String getcount(Invocation inv,@Param("type") @DefValue("1") Integer type,@Param("callback") String callback){
        byte[] b = redisHandlerService.get(ByteUtils.getBytes(FOCUS_WAP_REDIS+type));
        
         return JsonResponseUtil.jsonp(new String(b), callback);
    }
    
//    public static void main(String[] args) throws Exception
//    {
//        //创建主题样式  
//        StandardChartTheme standardChartTheme=new StandardChartTheme("CN");  
//        //设置标题字体  
//        standardChartTheme.setExtraLargeFont(new Font("隶书",Font.BOLD,20));  
//        //设置图例的字体  
//        standardChartTheme.setRegularFont(new Font("宋书",Font.PLAIN,15));  
//        //设置轴向的字体  
//        standardChartTheme.setLargeFont(new Font("宋书",Font.PLAIN,15));  
//        //应用主题样式  
//        ChartFactory.setChartTheme(standardChartTheme);  
//        //以上解决中文乱码
//        
//        JFreeChart chart = ChartFactory.createLineChart("result", "", "", getDataset(), PlotOrientation.VERTICAL,false, true, false);
//        chart.setTitle(new TextTitle("计算结果",new Font("宋体",Font.BOLD+Font.ITALIC,20)));
//        
//        CategoryPlot mPlot = (CategoryPlot)chart.getPlot();  
//        //line object 
//        LineAndShapeRenderer renderer = (LineAndShapeRenderer) mPlot  
//                .getRenderer();  
//        // line visible
//        renderer.setBaseShapesVisible(true);
//        // circle
//        renderer.setShape(new Ellipse2D.Float(0, 0, 5, 5));
//        // line color
//        renderer.setPaint(Color.GRAY);
//        // chart backgroudpaint
//        mPlot.setBackgroundPaint(Color.LIGHT_GRAY);  
//        
//        // gridline
//        mPlot.setRangeGridlinesVisible(false);
//        
//        ChartFrame mChartFrame = new ChartFrame("折线图", chart);  
//        mChartFrame.pack();  
//        mChartFrame.setVisible(true);  
//
//         
//        OutputStream os = new FileOutputStream("company.jpeg");//图片是文件格式的，故要用到FileOutputStream用来输出。
//        // pic width height
//        ChartUtilities.writeChartAsJPEG(os, chart, 1000, 800);
//        //使用一个面向application的工具类，将chart转换成JPEG格式的图片。第3个参数是宽度，第4个参数是高度。
//        
//        os.close();//关闭输出流
//    }
//
//    public static CategoryDataset getDataset()  
//    {  
//        DefaultCategoryDataset mDataset = new DefaultCategoryDataset();  
//        mDataset.addValue(5555, "", "一年后");  
//        mDataset.addValue(5222, "", "两年后");  
//        mDataset.addValue(5000, "", "三年后");  
//        mDataset.addValue(4777, "", "四年后");  
//        mDataset.addValue(4222, "", "五年后");  
//        mDataset.addValue(3333, "", "六年后");  
//        return mDataset;  
//    }  
}
