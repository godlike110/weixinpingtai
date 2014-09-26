package cn.focus.dc.focuswap.service;

import static cn.focus.dc.focuswap.config.AppConstants.CE_BUILDING_SEARCH_TIME;
import static cn.focus.dc.focuswap.config.AppConstants.CK_BUILDING_SEARCH_KEY;
import static cn.focus.dc.focuswap.config.AppConstants.DATA;
import static cn.focus.dc.focuswap.config.AppConstants.DATACENTER_BUILDING_NAME_BUY_CONDITION;
import static cn.focus.dc.focuswap.config.AppConstants.ERRORCODE;
import static cn.focus.dc.focuswap.config.AppConstants.XINFANG_TYPE_ID_RELATION_URL;

import cn.focus.dc.focuswap.model.DictAreaExt;
import cn.focus.dc.focuswap.model.DictCity;
import cn.focus.dc.focuswap.model.DictCityPriceExt;
import cn.focus.dc.focuswap.model.DictDistrictExt;
import cn.focus.dc.focuswap.model.DictProjTypeExt;
import cn.focus.dc.focuswap.model.DictProjTypeExtNew;
import cn.focus.dc.focuswap.model.DictSpecialBuilding;
import cn.focus.dc.focuswap.model.DictTrafficLineExt;
import cn.focus.dc.focuswap.model.SearchCondition;
import cn.focus.dc.focuswap.service.SearchService.HouseType;
import cn.focus.dc.focuswap.service.SearchService.SearchType;
import cn.focus.dc.focuswap.service.SearchService.SearchTypeSpecial;
import cn.focus.dc.focuswap.utils.Utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.ImmutableMap;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * 楼盘筛选服务类，主要提供筛选条件和根据筛选条件筛出楼盘两种服务</br> 获取筛选条件：getCondition(int, boolean)</br>
 * 根据筛选条件筛选出楼盘：filterBuilding(SearchCondition)</br>
 * 
 * @author rogantian
 * @date 2013-11-15
 * @email rogantianwz@gmail.com
 */
@Service
public class BuildingSearchService {

    protected Log logger = LogFactory.getLog(BuildingSearchService.class);

    @Autowired
    public InterfaceService interfaceService;

    @Autowired
    private CacheHandlerService cacheHandler;

    @Autowired
    private SearchService searchService;
    
    private static final Map<Integer, Map<String, String>> districtNameMap;
    
    static {
        districtNameMap = new HashMap<Integer, Map<String, String>>();
        
        Map<String, String> bjNameMap = ImmutableMap.of("北京周边", "周边");
        districtNameMap.put(1, bjNameMap);
       
        Map<String, String> czNameMap = ImmutableMap.of("常州周边", "周边");
        districtNameMap.put(86, czNameMap);
       
        Map<String, String> hzNameMap = ImmutableMap.of("杭州周边", "周边");
        districtNameMap.put(13, hzNameMap);
        
        Map<String, String> nbNameMap = ImmutableMap.of("其他区域", "其他");
        districtNameMap.put(36, nbNameMap);
        
        Map<String, String> shNameMap = ImmutableMap.of("上海周边", "周边");
        districtNameMap.put(2, shNameMap);
        
        Map<String, String> syNameMap = ImmutableMap.of("其他地区", "其他");
        districtNameMap.put(28, syNameMap);
        
        //ImmutableMap.builder().put("小店区", "小店").
        Map<String, String> tyNameMap = new ImmutableMap.Builder<String, String>().put("小店区", "小店")
                .put("迎泽区", "迎泽").put("杏花岭区", "杏花岭").put("尖草坪区", "尖草坪")
                .put("万柏林区", "万柏林").put("晋源区","晋源").put("阳曲县", "阳曲").put("青徐县", "青徐")
                .put("娄烦县", "娄烦").put("古交市", "古交").put("榆次区", "榆次").build();
        districtNameMap.put(49, tyNameMap);
        
        
        Map<String, String> xmNameMap = new ImmutableMap.Builder<String, String>().put("思明区", "思明")
                .put("湖里区", "湖里").put("海沧区", "海沧").put("集美区", "集美")
                .put("同安区", "同安").put("翔安区","翔安").put("厦门周边", "周边").build();
        districtNameMap.put(25, xmNameMap);
        
        Map<String, String> sanyaNameMap = new ImmutableMap.Builder<String, String>().put("三亚市", "三亚")
                .put("陵水县", "陵水").put("万宁市", "万宁").put("乐东县", "乐东")
                .put("五指山市", "五指山").put("保亭县","保亭").put("东方市", "东方").build();
        districtNameMap.put(222, sanyaNameMap);
        //烟台
        Map<String, String> ytNameMap = new ImmutableMap.Builder<String,String>().put("福山区", "福山")
        		.put("开发区","开发").put("莱山区", "莱山").put("高新区","高新")
        		.put("牟平区", "牟平").put("芝罘区","芝罘").put("长岛县", "长岛")
        		.put("海阳市", "海阳").put("龙口市", "龙口").put("莱阳市", "莱阳")
        		.put("莱州市", "莱州").put("蓬莱市", "蓬莱").put("栖霞市", "栖霞")
        		.put("招远市", "招远").build();
        districtNameMap.put(0, ytNameMap);
        
        //南京
        Map<String, String> njNameMap = new ImmutableMap.Builder<String,String>().put("鼓楼区", "鼓楼")
        		.put("建邺区","建邺").put("玄武区", "玄武").put("白下区","白下")
        		.put("秦淮区","秦淮").put("下关区", "下关").put("栖霞区","栖霞")
        		.put("江宁区","江宁").put("浦口区", "浦口").put("溧水区","溧水")
        		.put("六合区","六合").put("高淳区", "高淳").build();
        districtNameMap.put(10, njNameMap);
        
        //石家庄
        Map<String, String> sjzNameMap = new ImmutableMap.Builder<String,String>().put("桥东区", "桥东")
        		.put("桥西区","桥西").put("新华区", "新华").put("裕华区","裕华")
        		.put("长安区", "长安").build();
        districtNameMap.put(33, sjzNameMap);
        
        //成都
        Map<String, String> cdNameMap = new ImmutableMap.Builder<String,String>().put("温江区", "温江")
        		.put("双流县","双流").build();
        districtNameMap.put(11, cdNameMap);
        
        //长春
        Map<String, String> ccNameMap = new ImmutableMap.Builder<String,String>().put("朝阳区", "朝阳")
        		.put("南关区","南关").put("宽城区", "宽城").put("绿园区","绿园")
        		.put("二道区","二道").put("净月区", "净月").put("经开区","经开")
        		.put("汽开区","汽开").build();
        districtNameMap.put(39, ccNameMap);
        
        //大连
        Map<String, String> dlNameMap = ImmutableMap.of("长海县", "长海");
        districtNameMap.put(30, dlNameMap);
        
        //东莞
        Map<String, String> dgNameMap = new ImmutableMap.Builder<String,String>().put("东莞周边", "周边")
        		.put("莞城区","莞城").put("东城区", "东城").put("西城区","西城")
        		.put("万江区","万江").put("虎门镇", "虎门").put("厚街镇","厚街")
        		.put("樟木头镇","樟木头").put("常平镇", "常平").put("长安镇","长安")
        		.put("石碣镇","石碣").put("石龙镇", "石龙").put("凤岗镇","凤岗")
        		.put("黄江镇","黄江").put("塘厦镇", "塘厦").put("清溪镇","清溪")
        		.put("茶山镇","茶山").put("石排镇", "石排").put("企石镇","企石")
        		.put("桥头镇","桥头").put("谢岗镇", "谢岗").put("大朗镇","大朗")
        		.put("寮步镇","寮步").put("东坑镇", "东坑").put("横沥镇","横沥")
        		.put("大岭山镇","大岭山").put("沙田镇", "沙田").put("道滘镇","道滘")
        		.put("高埗镇","高埗").put("望牛墩镇", "望牛墩").put("中堂镇","中堂")
        		.put("麻涌镇","麻涌").put("洪梅镇", "洪梅").build();
        districtNameMap.put(50, dgNameMap);
        
        //福州
        Map<String, String> fzNameMap = new ImmutableMap.Builder<String,String>().put("福州周边", "周边")
        		.put("仓山区","仓山").put("鼓楼区", "鼓楼").put("晋安区","晋安")
        		.put("马尾区","马尾").put("闽侯县", "闽侯").put("台江区","台江")
        		.put("福清市","福清").put("长乐市", "长乐").put("连江县","连江")
        		.put("闽清县","闽清").put("永泰县", "永泰").put("平潭县","平潭")
        		.put("罗源县","罗源").build();
        districtNameMap.put(26, fzNameMap);
        
        //广州
        Map<String, String> gzNameMap = new ImmutableMap.Builder<String,String>().put("番禹区", "番禹")
        		.put("天河区","天河").put("海珠区", "海珠").put("白云区","白云")
        		.put("南沙区","南沙").put("黄埔区", "黄埔").put("越秀区","越秀")
        		.put("荔湾区","荔湾").put("花都区", "花都").put("增城区","增城")
        		.put("从化区","从化").build();
        districtNameMap.put(5, gzNameMap);
        
        //哈尔滨
        Map<String, String> hrbNameMap = new ImmutableMap.Builder<String,String>().put("南岗区", "南岗")
        		.put("道里区","道里").put("道外区", "道外").put("香坊区","香坊")
        		.put("呼兰区","呼兰").put("松北区", "松北").put("平房区","平房")
        		.put("阿城区","阿城").build();
        districtNameMap.put(19, hrbNameMap);
        
       //合肥
        Map<String, String> hfbNameMap = new ImmutableMap.Builder<String,String>().put("蜀山区", "蜀山")
        		.put("庐阳区", "庐阳").put("新站区", "新站").put("经开区","经开")
        		.put("政务区", "政务").put("瑶海区", "瑶海").put("包河区","包河")
        		.put("滨湖区", "滨湖").build();
        districtNameMap.put(23, hfbNameMap);
        
        //海南
        Map<String, String> hnNameMap = ImmutableMap.of("其他地区", "其他");
        districtNameMap.put(27, hnNameMap);
        
        //济南
        Map<String, String> jnNameMap = ImmutableMap.of("长清区", "长清");
        districtNameMap.put(15, jnNameMap);
        
        //南宁
        Map<String, String> nnNameMap = new ImmutableMap.Builder<String,String>().put("青秀区", "青秀")
        		.put("西乡塘区", "西乡塘").put("江南区", "江南").put("兴宁区","兴宁")
        		.put("良庆区", "良庆").put("邕宁区", "邕宁").build();
        districtNameMap.put(83, nnNameMap);
        
        //秦皇岛
        Map<String, String> qhdNameMap = new ImmutableMap.Builder<String,String>().put("海港区", "海港")
        		.put("北戴河区", "北戴河").put("山海关区", "山海关").build();
        districtNameMap.put(16, qhdNameMap);
        
        //青岛
        Map<String, String> qdNameMap = new ImmutableMap.Builder<String,String>().put("市南区", "市南")
        		.put("市北区", "市北").put("崂山区", "崂山").put("李沧区","李沧")
        		.put("城阳区", "城阳").put("黄岛区", "黄岛").build();
        districtNameMap.put(29, qdNameMap);
        
        //深圳
        Map<String, String> szNameMap = new ImmutableMap.Builder<String,String>().put("深圳周边", "周边")
        		.put("福田区", "福田").put("罗湖区", "罗湖").put("南山区","南山")
        		.put("盐田区", "盐田").put("宝安区", "宝安").put("龙岗区","龙岗").build();
        districtNameMap.put(6, szNameMap);
        
        //苏州
        Map<String, String> suzhouNameMap = ImmutableMap.of("姑苏区", "姑苏");
        districtNameMap.put(14, suzhouNameMap);
        
        //天津
        Map<String, String> tjNameMap = new ImmutableMap.Builder<String,String>().put("天津周边", "周边")
        		.put("和平区", "和平").put("河西区", "河西").put("南开区","南开")
        		.put("河北区", "河北").put("河东区", "河东").put("红桥区","红桥")
        		.put("北辰区", "北辰").put("西青区", "西青").put("津南区","津南").build();
        districtNameMap.put(9, tjNameMap);
        
        //无锡
        Map<String, String> wuxiNameMap = new ImmutableMap.Builder<String,String>().put("北塘区", "北塘")
        		.put("滨湖区", "滨湖").put("崇安区", "崇安").put("惠山区","惠山")
        		.put("马山区", "马山").put("南长区", "南长").put("锡山区","锡山").build();
        districtNameMap.put(34, wuxiNameMap);
        
        //郑州
        Map<String, String> zzNameMap = new ImmutableMap.Builder<String,String>().put("中原区", "中原")
        		.put("管城区", "管城").put("二七区", "二七").put("金水区","金水")
        		.put("惠济区", "惠济").put("上街区", "上街").build();
        districtNameMap.put(48, zzNameMap);
        
        //珠海
        Map<String, String> zhNameMap = new ImmutableMap.Builder<String,String>().put("珠海周边", "周边")
        		.put("香洲区", "香洲").put("斗门区", "斗门").put("金湾区","金湾").build();
        districtNameMap.put(128, zhNameMap);
    }
    
    /**
     * 因为special的对勾要变化,不能放入类变量里面,所以每次调用都需要new
     * @return
     */
    private List<DictSpecialBuilding> buildSpecial(){
        List<DictSpecialBuilding> specialBuildingCondtions = new ArrayList<DictSpecialBuilding>();
        specialBuildingCondtions.add(new DictSpecialBuilding(SearchTypeSpecial.DZLP.name(), "打折楼盘"));
        specialBuildingCondtions.add(new DictSpecialBuilding(SearchTypeSpecial.ZXKP.name(), "最新开盘"));
        specialBuildingCondtions.add(new DictSpecialBuilding(SearchTypeSpecial.XHX.name(), "小户型"));
        specialBuildingCondtions.add(new DictSpecialBuilding(SearchTypeSpecial.XF.name(), "现房"));
        specialBuildingCondtions.add(new DictSpecialBuilding(SearchTypeSpecial.DZJ.name(), "低总价"));
        specialBuildingCondtions.add(new DictSpecialBuilding(SearchTypeSpecial.HF.name(), "婚房"));
        specialBuildingCondtions.add(new DictSpecialBuilding(SearchTypeSpecial.JYSQ.name(), "教育社区"));
        specialBuildingCondtions.add(new DictSpecialBuilding(SearchTypeSpecial.SKZJ.name(), "三代同堂"));
        specialBuildingCondtions.add(new DictSpecialBuilding(SearchTypeSpecial.GYPB.name(), "公园旁边"));
        specialBuildingCondtions.add(new DictSpecialBuilding(SearchTypeSpecial.CSHZ.name(), "城市豪宅"));
        return specialBuildingCondtions;
    }
    
    /**
     * 获取楼盘的筛选条件:先从cache中取，取不到的话就调楼盘中心的接口获取并且将获取的结果存入cache中
     * 
     * @param cityId
     * @param needSpecialSearch 是否需要返回特殊的搜索条件（打折、本月开盘、小户型和现房）
     * @return
     */
    public Map<SearchType, Object> getConditions(int cityId, boolean needSpecialSearch) {
        Map<SearchType, Object> ret = null;
        String cacheKey = CK_BUILDING_SEARCH_KEY + cityId;
        //cacheHandler.removeCache(CK_BUILDING_SEARCH_KEY + cityId);
        ret = cacheHandler.getCacheValue(cacheKey, Map.class);
        if (null == ret || ret.size() == 0) {
            ret = getConditionsFromInterface(cityId);
            if (null != ret && ret.size() > 0) {
                cacheHandler.setCache(cacheKey, CE_BUILDING_SEARCH_TIME, ret);
                logger.debug("城市:"+cityId+"setcache success");
            } else {
                ret = Collections.emptyMap();
            }
        }else{
            logger.debug("城市:"+cityId+"getcache success");
        }

        if (needSpecialSearch) {
            ret.put(SearchType.SPECIAL, buildSpecial());
        }
        return ret;
    }

    /**
     * 获取被选中的值以及面包屑显示的值
     * @param allConditions
     * @param c
     * @param city
     * @param con
     * @return
     */
    @SuppressWarnings("unchecked")
    public Map<String,Object> getChosenCondition(Map<SearchType, Object> allConditions,DictCity city,String con){
        if(StringUtils.isNotBlank(con)){
            con +="/";            
        }else{
            con="k______/";
        }
        int index = -1; //条件索引值
        String[] sp = con.split("_");
        String tdk = "";
        Map<String,Object> result = new HashMap<String,Object>();
        List<Object> showCondition = new ArrayList<Object>();
        StringBuilder linkUrl = new StringBuilder("/"+city.getCityPinyinAbbr()+"/loupan/");
        for(SearchType q : allConditions.keySet()){
            switch (q) {
                case DISTRICT:
                    List<DictDistrictExt> dde = (List<DictDistrictExt>) allConditions.get(SearchType.DISTRICT); 
                    index = 0; 
                    String[] arr0 = sp.clone();
                    for (DictDistrictExt dd : dde) {
                        arr0[index] = dd.getCondValue();
                        dd.setLinkUrl(linkUrl.toString()+"k"+StringUtils.join(arr0,"_"));
                        //获取选中项
                        if(StringUtils.isNotBlank(sp[index]) && sp[index].equals("k"+dd.getCondValue())){
                            //获取面包屑
                            result.put("bread", dd.getCondName());                                                        
                            dd.setSelect("selected");
                            showCondition.add(dd.clone(index));
                            tdk += dd.getCondName();
                        }
                    }
                    break;
                case TYPE:
                    List<DictProjTypeExtNew> dpt = (List<DictProjTypeExtNew>) allConditions.get(SearchType.TYPE); 
                    index = 1; 
                    String[] arr1 = sp.clone();
                    for (DictProjTypeExtNew dd : dpt) {
                        arr1[1] = dd.getCondValue();
                        dd.setLinkUrl(linkUrl.toString()+StringUtils.join(arr1,"_"));
                        //获取选中项
                        if(StringUtils.isNotBlank(sp[index]) && sp[index].equals(dd.getCondValue())){
                            dd.setSelect("selected");
                            showCondition.add(dd.clone(index));
                            tdk += dd.getCondName();
                        }
                    }
                    break;
                case PRICE:
                    List<DictCityPriceExt> dcp = (List<DictCityPriceExt>) allConditions.get(SearchType.PRICE);
                    index = 2; 
                    String[] arr2 = sp.clone();
                    for (DictCityPriceExt dd : dcp) {                             
                        arr2[2] = dd.getCondValue();
                        dd.setLinkUrl(linkUrl.toString()+StringUtils.join(arr2,"_"));
                        //获取选中项
                        if(StringUtils.isNotBlank(sp[index]) && sp[index].equals(dd.getCondValue())){
                            dd.setSelect("selected");
                            showCondition.add(dd.clone(index));
                            tdk += dd.getCondName();
                        }
                    }
                    break;
                case HOT:
                    List<DictAreaExt> dae = (List<DictAreaExt>) allConditions.get(SearchType.HOT); 
                    index = 3; 
                    String[] arr3 = sp.clone();
                    for (DictAreaExt dd : dae) {     
                        arr3[3] = dd.getCondValue();
                        dd.setLinkUrl(linkUrl.toString()+StringUtils.join(arr3,"_"));
                        //获取选中项
                        if(StringUtils.isNotBlank(sp[index]) && sp[index].equals(dd.getCondValue())){
                            dd.setSelect("selected");
                            showCondition.add(dd.clone(index));
                            tdk += dd.getCondName();
                        }
                    }
                    break;
                case SUBWAY:
                    List<DictTrafficLineExt> dtl = (List<DictTrafficLineExt>) allConditions.get(SearchType.SUBWAY); 
                    index = 4; 
                    String[] arr4 = sp.clone();
                    for (DictTrafficLineExt dd : dtl) {
                        arr4[4] = dd.getCondValue();
                        dd.setLinkUrl(linkUrl.toString()+StringUtils.join(arr4,"_"));
                        //获取选中项
                        if(StringUtils.isNotBlank(sp[index]) && sp[index].equals(dd.getCondValue())){
                            dd.setSelect("selected");
                            showCondition.add(dd.clone(index));
                            tdk += dd.getCondName();
                        }
                    }
                    break;
                case SPECIAL:
                    List<DictSpecialBuilding> dsb = (List<DictSpecialBuilding>) allConditions.get(SearchType.SPECIAL); 
                    index = 5; 
                    String[] arr5 = sp.clone();
                    for (DictSpecialBuilding dd : dsb) {
                        arr5[5] = dd.getCondValue();
                        dd.setLinkUrl(linkUrl.toString()+StringUtils.join(arr5,"_"));
                        //获取选中项
                        if (StringUtils.isNotBlank(sp[index]) && sp[index].equals(dd.getCondValue())) {                 
                            dd.setSelect("selected");
                            showCondition.add(dd.clone(index));
                            tdk += dd.getCondName();
                        }
                    }
                    break;
                default:
                    break;
                }
            }            
        result.put("chosen", showCondition);
        result.put("tdk", tdk);
        return result;
    }
    
    
    /**
     * 根据筛选条件筛选出楼盘,参考SearchService.projSearch(JSONObject sCond)
     * 
     * @param searchConditions
     * @return
     */
    public Map<String, Object> filterBuilding(SearchCondition searchConditions) {
        if (null == searchConditions) {
            return Collections.emptyMap();
        }
            return searchService.projSearch(searchConditions);
    }

    /**
     * 从楼盘中心接口获取城市的楼盘筛选条件，参考wiki：http://10.10.90.156/wiki/index.php/Dict/search/tab</br>
     * 其中的价格条件已经处理过了，详细见decoratePriceCondition(List..)方法</br> 返回结果:</br>
     * <table>
     * <tr>
     * <td>key</td>
     * <td>value</td>
     * </tr>
     * <tr>
     * <td>SearchType.SUBWAY</td>
     * <td>List&lt;DictTrafficLineExt&gt;</td>
     * </tr>
     * <tr>
     * <td>SearchType.DISTRICT</td>
     * <td>List&lt;DictDistrictExt&gt;</td>
     * </tr>
     * <tr>
     * <td>SearchType.TYPE</td>
     * <td>List&lt;DictProjTypeExt&gt;</td>
     * </tr>
     * <tr>
     * <td>SearchType.HOT</td>
     * <td>Map&lt;String, List&lt;DictAreaExt&gt;&gt;</td>
     * </tr>
     * <tr>
     * <td>SearchType.PRICE</td>
     * <td>Map&lt;String, List&lt;DictCityPriceExt&gt;&gt;</td>
     * </tr>
     * </table>
     * 
     * @param cityId
     * @return
     */
    public Map<SearchType, Object> getConditionsFromInterface(int cityId) {
        Map<SearchType, Object> ret = new HashMap<SearchType, Object>();

        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("cityId", cityId);
        // JSONObject jsonObject =
        // interfaceService.getJSONFromInterface("http://wap-building.apps.sohuno.com/dict/search/tab?cityId={cityId}",
        // variables);
        JSONObject jsonObject = interfaceService
                .getJSONFromInterface(DATACENTER_BUILDING_NAME_BUY_CONDITION, variables);
        String data = extractJSONData(jsonObject);

        if (null != data) {

            Map<String, Object> conditions = JSONObject.parseObject(data, new TypeReference<Map<String, Object>>() {
            });
            JSONArray lineArray = (JSONArray) conditions.get("lines");
            List<DictTrafficLineExt> lineList = parseConditionArray(lineArray, DictTrafficLineExt.class);
            if(!lineList.isEmpty()){
                ret.put(SearchType.SUBWAY, lineList);
            }

            Object districtArray = conditions.get("districts");
            String districtStr = districtArray.toString();
            List<DictDistrictExt> districtList = JSONObject.parseArray(districtStr, DictDistrictExt.class);
            /**
             *2014-06-10修改，区域名称映射 by rogantian 
             */
            Map<String, String> districtNameMapOfCity = districtNameMap.get(cityId);
            if (null != districtNameMapOfCity && null != districtList && districtList.size() > 0) {
                for (DictDistrictExt district : districtList) {
                    String newDistrictName = districtNameMapOfCity.get(district.getCondName());
                    if (StringUtils.isNotBlank(newDistrictName)) {
                        district.setCondName(newDistrictName);
                    }
                }
            }
            
            ret.put(SearchType.DISTRICT, districtList);

            List<DictProjTypeExt> typeList = new ArrayList<DictProjTypeExt>();
            JSONObject types = interfaceService.getJSONFromInterface(XINFANG_TYPE_ID_RELATION_URL,variables);
            if(types != null && types.get("data") != null){
                for(HouseType ht : HouseType.values()){
                    DictProjTypeExtNew type= new DictProjTypeExtNew();
                    type.setTypeName(ht.name());
                    type.setTypeId(ht.ordinal()+1);
                    
                    //通过新的id获取关联的旧ids
                    //List<DictProjTypeExtNew> types = conditionDAO.getOldIds(cityId,type.getTypeId().toString());
                    JSONArray ja = (JSONArray) types.get("data");
                    //Integer[] args = new Integer[ja.size()];
                    List<Integer> args = new ArrayList<Integer>();
                    for (Object ob : ja) {
                        JSONObject jo = (JSONObject) ob;
                        if (type.getTypeId() == jo.get("typeIdNew")) {
                            args.add((Integer) jo.get("typeIdOld"));
                        }
                    }
                    type.setOldIds(args);
                    typeList.add(type);
                }
            }        
            ret.put(SearchType.TYPE, typeList);

            //Map<String, List<DictAreaExt>> areaMap = new HashMap<String, List<DictAreaExt>>();
            JSONObject areaObject = (JSONObject) conditions.get("areas");
            ///*2次注释,因为产品不需要热点了 2014.1.6
            JSONArray areaArray = areaObject.getJSONArray("-1");
            if(areaArray == null){
                areaArray = areaObject.getJSONArray("0");
            }
            if(areaArray != null && !areaArray.isEmpty()){
                List<DictAreaExt> areaList = parseConditionArray(areaArray, DictAreaExt.class);
                ret.put(SearchType.HOT, areaList);
            }
            JSONObject priceObject = (JSONObject) conditions.get("prices");
            JSONArray priceArray = priceObject.getJSONArray("0");
            List<DictCityPriceExt> priceList = parseConditionArray(priceArray, DictCityPriceExt.class);
            decoratePriceCondition(priceList);
            ret.put(SearchType.PRICE, priceList);
        }

        return ret;
    }

    /**
     * 将“价格”转换成适合的 名称和值</br> 如:将10000一下转换成 1万一下(名称)和 0-10000(值)</br>
     * 
     * @param priceMap
     */
    protected void decoratePriceCondition(List<DictCityPriceExt> priceList) {
        if (null == priceList) {
            return;
        }

        Iterator<DictCityPriceExt> it = priceList.iterator();
        while (it.hasNext()) {
            DictCityPriceExt priceExt = it.next();
            String price = priceExt.getPrice();
            if (StringUtils.equals("0", price)) {
                it.remove();
                continue;
            }
            String condName = Utils.stringPattern(price);
            if (StringUtils.isBlank(condName)) {
                it.remove();
                continue;
            }

            String condValue = Utils.stringPatternId(price);
            if (StringUtils.isBlank(condValue)) {
                it.remove();
                continue;
            }

            priceExt.setCondName(condName);
            priceExt.setCondValue(condValue);
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private <T> List<T> parseConditionArray(JSONArray conditionArray, Class T){
        String arrStr = conditionArray.toJSONString();
        return JSONObject.parseArray(arrStr, T);
    }

    /**
     * 从标准的接口返回值中提取所需数据
     * 
     * @param jsonObject
     * @return
     */
    private String extractJSONData(JSONObject jsonObject) {
        if (null != jsonObject) {
            Integer errorCode = jsonObject.getInteger(ERRORCODE);
            if (null != errorCode && errorCode == 0) {
                return jsonObject.getString(DATA);
            }
        }
        return null;
    }

    public static void main(String[] args) {
        final RestTemplate template = new RestTemplate(new HttpComponentsClientHttpRequestFactory(){
            private static final String DEFAULT_SIGN_NAME = "sign";

            @Override
            protected void postProcessHttpRequest(HttpUriRequest request) {
                if (StringUtils.equals(HttpGet.METHOD_NAME, request.getMethod())) {
                    URI url = request.getURI();
                    String query = url.getQuery();
                    if (null != query) {
                        String sign = genSignParam(query);
                        request.addHeader(DEFAULT_SIGN_NAME, (String) sign);
                    }
                }

                super.postProcessHttpRequest(request);

            }

            private String genSignParam(String queryStr) {
                if (StringUtils.isBlank(queryStr)) {
                    return DigestUtils.md5Hex("e0cd45bf9ee7773cc9b72bd824f3b35c");
                } else {
                    StringBuilder sb = new StringBuilder();
                    sb.append(StringUtils.chomp(queryStr, "&")).append("e0cd45bf9ee7773cc9b72bd824f3b35c");
                    return DigestUtils.md5Hex(sb.toString());
                }

            }
        });
        InterfaceService interfaceService = new InterfaceService() {
            public JSONObject getJSONFromInterface(String url, Map<String, Object> params) {
                JSONObject ret = null;
                try {
                    String jsonStr = template.getForObject(url, String.class, params);
                    if (StringUtils.isNotBlank(jsonStr)) {
                        ret = JSONObject.parseObject(jsonStr);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return ret;
            }
        };

        BuildingSearchService buildingService = new BuildingSearchService();
        buildingService.interfaceService = interfaceService;
    }    

}
