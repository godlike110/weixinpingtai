package cn.focus.dc.focuswap.controllers.portal;


import cn.focus.dc.commons.model.UserBasic;
import cn.focus.dc.focuswap.config.AppConstants;
import cn.focus.dc.focuswap.config.AppConstants.Layout;
import cn.focus.dc.focuswap.model.BuildingAroundInfo;
import cn.focus.dc.focuswap.model.BuildingDaoGou;
import cn.focus.dc.focuswap.model.BuildingLayoutPhotoes;
import cn.focus.dc.focuswap.model.ProjPhotoesExt;
import cn.focus.dc.focuswap.model.Question;
import cn.focus.dc.focuswap.model.QuestionInfo;
import cn.focus.dc.focuswap.model.RecentNews;
import cn.focus.dc.focuswap.model.TuanGouDetail;
import cn.focus.dc.focuswap.service.BuildingDaoGouService;
import cn.focus.dc.focuswap.service.BuildingProposeService;
import cn.focus.dc.focuswap.service.BuildingService;
import cn.focus.dc.focuswap.service.PaFangTuanService;
import cn.focus.dc.focuswap.service.PftService;
import cn.focus.dc.focuswap.service.QuestionService;
import cn.focus.dc.focuswap.service.RencentNewsService;
import cn.focus.dc.focuswap.service.TuanGouService;
import cn.focus.dc.focuswap.service.XinFangCommonApiService;
import cn.focus.dc.focuswap.service.XinFangCommonApiService.EditorComment;
import cn.focus.dc.focuswap.service.XinFangCommonApiService.Pafangtuan;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Path("building")
public class BuildingController {

    private static Logger logger = LoggerFactory.getLogger(BuildingController.class);

    @Autowired
    private BuildingService buildingService;

    @Autowired
    private QuestionService questionService;
    
    
    @Autowired
    private RencentNewsService recentNewsService;
    
    @Autowired
    private XinFangCommonApiService xinFangCommonApiService;
    
    @Autowired
    private BuildingProposeService buildingProposeService;
    
    @Autowired
    private BuildingDaoGouService buildingDaoDouService;
    
    @Autowired
    private PftService pftService;
    
    @Autowired
    private PaFangTuanService paFangTuanService;
    
    @Autowired
    private TuanGouService tuangouService;

    @Get("test")
    public String test() {
        return "@test";
    }
    
    @Get("tuangou")
    public String getTuangou(Invocation inv,@Param("cityId") int cityId,@Param("groupId") int groupId) throws ParseException {
    	
    	String groupids = tuangouService.getTuanGouGroupids(cityId);
    	if(null!=groupids && groupids.contains(String.valueOf(cityId) + "_"  + String.valueOf(groupId))) {
            JSONObject tgList = tuangouService.getTuanGouList(cityId, 0, Integer.MAX_VALUE);

            List<TuanGouDetail> list = null;
			if(null!=tgList) { 
				if(StringUtils.isNotBlank(tgList.getString("data"))) {
				//list = JSON.toJavaObject(tgList.getJSONArray("data"), List.class);
				list = JSONArray.parseArray(tgList.getJSONArray("data").toString(), TuanGouDetail.class);
				}
			}
			
			for(TuanGouDetail tg:list) {
				if(tg.getGroup_id()==groupId) {
					inv.addModel("tg1", tg);
				}
			}
    	}
    	
    	return "buildinghome/phone/tuangou";
    }

    /**
     * 最新动态
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
    @Get("hotnews")
    public String getHotNews(Invocation inv, @Param("projId") int projId, @Param("cityId") int cityId,
            @Param("pageNo") int pageNo, @Param("pageSize") int pageSize) {
        try {
            //最新动态
            Map<String, Object> recentNewsResult = recentNewsService.getRecentNewsList(projId,
                    cityId, pageNo, pageSize);
            List<RecentNews> recentNewsList = null;
            if (null != recentNewsResult) {
                Object recentNewsObject = recentNewsResult.get("recentNewsList");
                if (null != recentNewsObject) {
                    recentNewsList = (List<RecentNews>) recentNewsObject;
                    for(RecentNews news:recentNewsList) {
                    	String content = news.getInfocontent();
                    	content = content.replaceAll("-break-", "");
                    	news.setInfocontent(content.substring(0, 90) + "...");
                    }
                    inv.addModel("recentNews", recentNewsList);
                    inv.addModel("qcount", recentNewsList.size());
                }
            }
            return "buildinghome/phone/newsnew";
        } catch (Exception e) {
            logger.info("", e.getMessage());
            return null;
        }
    }

    /**
     * 热门资讯
     * 
     * @return
     */
    @Get("questions")
    public String getQuestion(Invocation inv, @Param("cityId") int cityId, @Param("projId") int projId) {
        try {
            Integer uid = null;
            Object o = inv.getModel(AppConstants.FOCUS_USER);
            if (null != o) {
                uid = ((UserBasic) o).getUid();
            }
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            // 热门资讯
            List<QuestionInfo> questions = questionService.getBuildingQuestion(cityId, projId, uid);
            int qSize = questions.size();
            for (int i = 0; i < qSize; i ++ ) {
                if (i == 3) {
                    break;
                }
                Question q = questions.get(i);
                q.setCreateTimeStr(sf.format(q.getCreateTime()));
                if (q.getStatus() != AppConstants.QAStatus.QA_ANSWERED) {
                    continue;
                }
                String as = q.getAnswer();
                if(as.length()>50) {
                    q.setAnswer(as.substring(0, 50) + "...");
                }
                q.setUpdateTimeStr(sf.format(q.getUpdateTime()));
            }
            inv.addModel("questions", questions);
            boolean asked = false;
            if (qSize > 0) {
                asked = questionService.didUserAsked(cityId, projId, uid);
            }
//            1:咨询条数<3;
//            2:咨询条数>3但是当前用户未提问;
//            3:咨询条数>3且当前用户提问过
            int checkAllStatus = 1;
            
            if (qSize > 3) {
              if (asked) {
                  checkAllStatus = 3;
              } else {
                  checkAllStatus = 2;
              }
            }
            
            inv.addModel("checkAll", checkAllStatus);
            return "buildinghome/phone/questionnew";
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }
    }

    /**
     * 周边楼盘
     * 
     * @return
     */
    @Get("arroundbuilding")
    public String getArroundBuilding(Invocation inv, @Param("groupId") int groupId, @Param("limit") int limit) {
        try {

            List<BuildingAroundInfo> aroundBuildings = buildingService.getAroundBuildings(groupId, limit);
            aroundBuildings = buildingService.setArroundBuildingListPrice(aroundBuildings);
            
            for(BuildingAroundInfo info:aroundBuildings) {
                if(info.getProjName().length()>8) {
                    String name = info.getProjName();
                    name = name.substring(0, 7) + "...";
                    info.setProjName(name);
                }
                buildingService.getBuildingArroundLog(info, false);
            }
            
            inv.addModel("arroundbuilding", aroundBuildings);
            return "buildinghome/phone/arroundbuildingnew";
        } catch (Exception e) {
            logger.info("", e.getMessage());
            return null;
        }
    }
    
    /**
     * 户型图
     * @param inv
     * @param groupId
     * @param cityId
     * @param device
     * @return
     */
    @Get("layouts")
    public String getLayout(Invocation inv, @Param("groupId") int groupId, @Param("cityId") int cityId,@Param("price") Integer price) {
        int layoutCount = 0;
        try {
            List<BuildingLayoutPhotoes> buildingLayoutPhotoes = buildingService.getBuildingPicInfo(groupId);
            
            for (BuildingLayoutPhotoes layout : buildingLayoutPhotoes) {
                buildingService.decorateBuildingPics(layout.getPhotoes(), false, 120, false);
            }
            
            buildingLayoutPhotoes = decraPhotos(buildingLayoutPhotoes,price);
            
            layoutCount = buildingLayoutPhotoes.size();            
            inv.addModel("layouts", buildingLayoutPhotoes);
            inv.addModel("groupId", groupId);
            
        } catch (Exception e) {
            logger.error("", e);
        }
        inv.addModel("layoutCount", layoutCount);
        return "buildinghome/phone/layoutnew";
    }
    
    /**
     * 获取楼盘图片的张数
     * @param inv
     * @param groupId
     * @return
     */
    @Get("buildingpiccount")
    public String countBuildingPics(Invocation inv, @Param("groupId") int groupId) {
        try {
            List<ProjPhotoesExt> projPhotoes = buildingService.getBuildingPic(groupId);
            if (null != projPhotoes && projPhotoes.size() > 0) {
                inv.addModel("photocounts", projPhotoes.size());
            } else {
                return null;
            }
        } catch (Exception e) {
            logger.error("", e);
        }
        return "buildinghome/phone/photocountnew";
    }
    
    /**
     * daogou
     * @param inv
     * @param groupId
     * @param device
     * @return
     */
    @Get("rpropose")
    public String getRelatePropose(Invocation inv, @Param("groupId") int groupId, @Param("cityId") int cityId) {
    	try {
    		
//    		List<BuildingPropose> bpList = new ArrayList<BuildingPropose>();
//    		if(null!=dgList && dgList.size()>0 ) {
//    			
//    			for(XinfangDaogou dg:dgList) {
//    				BuildingPropose bp = null;
//    				bp = buildingProposeService.getPropose(dg.getProposeId(), dg.getCityId());
//    				bpList.add(bp);
//    			}
//    		}
    		//List<BuildingDaoGou> bpList = new ArrayList<BuildingDaoGou>();
    		List<BuildingDaoGou> bpList = null;
//     		if(null!=dgList && dgList.size()>0 ) {
//    			for(XinfangDaogou dg:dgList) {
//    				BuildingDaoGou bp = null;
//    				bp = buildingDaoDouService.getDaoGou(dg.getCityId(),dg.getProposeId());
//    				if(null==bp) {
//    					bp = buildingDaoDouService.getDgFromPC(dg.getCityId(), dg.getProposeId());
//    				}
//    				bpList.add(bp);
//    			}
//    		}
    		bpList = xinFangCommonApiService.getPcDaogouByGroupid(groupId, 1, 2);
    		if(null!=bpList && bpList.size()>2) {
    			bpList = bpList.subList(0, 2);
    		}
    		inv.addModel("dg", bpList);
    		return "buildinghome/phone/daogou";
    	} catch (Exception e) {
    		logger.error("",e);
    		return null;
    	}
    }
    
    
    /**
     * editorcomment
     * @param inv
     * @param groupId
     * @param device
     * @return
     */
    @Get("editorcomment")
    public String getEditorComment(Invocation inv, @Param("groupId") int groupId) {
    	try {
    		
    		EditorComment ec = xinFangCommonApiService.getEditorComment(groupId);
    		
    		String comment = null;
    		
    		if(null!=ec) {
    			comment = ec.getRecommendContent();
    		}
    		
    		if(StringUtils.isNotBlank(comment) && comment.length()>200 ) {
    			ec.setRecommendContent(comment.substring(0, 200)+ "...");
    		}
    		
    		if(ec!=null) {
    			inv.addModel("comment",ec);
    		}
    		
    		return "buildinghome/phone/editorcomment";
    	} catch (Exception e) {
    		logger.error("",e);
    		return null;
    	}
    }
    
    /**
     * 
     * @param inv
     * @param groupId
     * @param cityId
     * @param device
     * @return
     */
    @Get("pft")
    public String getPftNew(Invocation inv, @Param("groupId") int groupId,@Param("cityId") int cityId) {
       try {
           List<Pafangtuan> pftList = paFangTuanService.getPafangtuanListByGroupId(cityId, groupId);
           if (null == pftList || pftList.size() < 1) {
               return null;
           }
           inv.addModel("pft", pftList.get(0));
           return "buildinghome/phone/pafangtuannew";
       } catch (Exception e) {
           logger.error("", e.getMessage());
           return null;
       }
    }
    
    
    
    /**
     * 户型图添加修改数据
     * 
     * @param photosList
     * @return
     */
    private List<BuildingLayoutPhotoes> decraPhotos(List<BuildingLayoutPhotoes> photosList,int price) {
        for (BuildingLayoutPhotoes photos : photosList) {
            List<ProjPhotoesExt> list = photos.getPhotoes();
            float min = list.get(0).getBuildArea() == null ? 0 : list.get(0).getBuildArea();
            float max = list.get(0).getBuildArea() == null ? 0 : list.get(0).getBuildArea();
            for (ProjPhotoesExt photo : list) {
                if (photo.getBuildArea() == null) {
                    photo.setBuildArea((float) 0);
                }
                if (photo.getBuildArea() > max) {
                    max = photo.getBuildArea();
                }
                if (photo.getBuildArea() < min && photo.getBuildArea() > 0) {
                    min = photo.getBuildArea();
                }

                if (min <= 1.0 && photo.getBuildArea() < max) {
                    min = photo.getBuildArea();
                }
                photo.setTypeClassId(decraClassTypeId(photo.getTypeClassId()));
            }
            photos.setPicUrl(list.get(0).getRealPath());
            photos.setTypeClassStr(list.get(0).getTypeClassId());

            if(photos.getType()==-1) {
            	photos.setTypeStr(photos.getTypeClassStr() + "[主力户型]");
            } else {
            	photos.setTypeStr(getSwapType(photos.getType()) + "居室");
            }
            
            int a = (int) Math.ceil(min);
            int minPrice = a*price/10000;
            int b = (int) Math.ceil(max);
            int MaxPrice = b*price/10000;
            if (b == 0) {
                photos.setMinMaxArea(Layout.getLayoutName(photos.getType().toString()));
                photos.setMinMaxPrice("待定");
            } else if (a == b || a == 0) {
                photos.setMinMaxArea(Layout.getLayoutName(photos.getType().toString()) + "  " + b + "平米");
                photos.setMinMaxPrice(MaxPrice + "万");
            } else {
                photos.setMinMaxArea(Layout.getLayoutName(photos.getType().toString()) + "  " + a + "--" + b
                        + "平米");
                photos.setMinMaxPrice(minPrice + "--" + MaxPrice + "万");
            }
        }
        return photosList;
    }
    
    private String decraClassTypeId(String typeId) {
        if (typeId == null) {
            return "";
        }
        char[] a = typeId.toCharArray();
        String result = "";
        if (a.length == 3) {
            result = getSwapType(Integer.parseInt(String.valueOf(a[0]))) + "室" + getSwapType(Integer.parseInt(String.valueOf(a[1]))) + "厅" + getSwapType(Integer.parseInt(String.valueOf(a[2]))) + "卫";
        } else if (a.length == 2) {
            result = getSwapType(Integer.parseInt(String.valueOf(a[0]))) + "室" + getSwapType(Integer.parseInt(String.valueOf(a[1]))) + "厅";
        } else if (a.length == 1) {
            if (a[0] == '0') {
                result = "0" ;
            } else {
                result = getSwapType(Integer.parseInt(String.valueOf(a[0]))) + "室";
            }
        } else {
            result = "0";
        }
        return result;
    }
    
    public static String getSwapType(int typeId) {
    	String type=null;
    	switch(typeId) {
    	case 1:type="一";break;
    	case 2:type="二";break;
    	case 3:type="三";break;
    	case 4:type="四";break;
    	case 5:type="五";break;
    	case 6:type="六";break;
    	case 7:type="七";break;
    	case 8:type="八";break;
    	case 9:type="九";break;
    	default:type="未知";
    	
    	}
    	return type;
    }
    
    public static void main(String argv[]) {
    	BuildingController bc =new BuildingController();
    	bc.decraClassTypeId("332");
    }

}