package cn.focus.dc.focuswap.service;

import cn.focus.dc.focuswap.config.AppConstants;
import cn.focus.dc.focuswap.model.BuildingDaoGou;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.ImmutableMap;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 新房公用api的service
 * @author rogantian
 * @date 2014-4-14
 * @email rogantianwz@gmail.com
 */
@Component
public class XinFangCommonApiService {
    
    private static final Log logger  = LogFactory.getLog(XinFangCommonApiService.class);
    
    @Autowired
    private InterfaceService interfaceService;

    //业务类型
    public static enum OperateType {
        ALL {
            int getValue() {
                return 0;
            }
        },
        
        QUESTION { 
            int getValue() {
                return 1;
            }
        },
        
        HOUSE_GUIDE {
            int getValue() {
                return 2;
            }
        },
        
        BUILDING_PROPOSE {
            int getValue() {
                return 3;
            }
            
        },
        
        PA_FANG_TUAN {
            int getValue() {
                return 4;
            }
            
        },
        
        ZI_XUN {
            int getValue() {
                return 5;
            }
        },
        
        DAOGOU_FROM_PC{
        	int getValue(){
        		return 6;
        	}
        };
        
        abstract int getValue();
    }
    
    /**
     * 业务收藏（包括问答、宝典、导购、爬房团、新闻）
     * @param type  收藏类型
     * @param itemId    业务ID    
     * @param uid   用户ID
     * @param srcUid    收藏项的编辑ID
     * @return true:收藏成功;false:收藏失败
     */
    public boolean addFav(OperateType type, int itemId, int uid, int srcUid) {
        if (type.equals(OperateType.ALL) || itemId <=0 || uid <= 0 || srcUid <= 0) {
            return false;
        }
        
        Map<String, Object> variables = ImmutableMap.of("type", (Object)(type.getValue()), "itemId", (Object)itemId, "uid", (Object)uid, "srcUid", (Object)srcUid);
        String rsp = interfaceService.postObjectForString(AppConstants.XIN_FANG_API_V4_FAV_ADD_URL, null, variables);
     
        try {
            if (null != rsp) {
                return extractJSONAndReturnBoolean(rsp);
            }
        } catch (Exception e) {
            logger.error("", e);
        }
        
        return false;
    }
    /**
     * 本地业务收藏（包括问答、宝典、导购、爬房团、新闻）
     * @param type  收藏类型
     * @param itemId    业务ID    
     * @param uid   用户ID
     * @param srcUid    收藏项的编辑ID
     * @return true:收藏成功;false:收藏失败
     */
    public boolean addFavLocal(OperateType type, int itemId, int uid, int srcUid) {
        if (type.equals(OperateType.ALL) || itemId <=0 || uid <= 0 || srcUid <= 0) {
            return false;
        }
        
        Map<String, Object> variables = ImmutableMap.of("type", (Object)(type.getValue()), "itemId", (Object)itemId, "uid", (Object)uid, "srcUid", (Object)srcUid);
        String rsp = interfaceService.postObjectForString(AppConstants.XIN_FANG_API_V4_FAV_ADD_LOCAL_URL, null, variables);
     
        try {
            if (null != rsp) {
                return extractJSONAndReturnBoolean(rsp);
            }
        } catch (Exception e) {
            logger.error("", e);
        }
        
        return false;
    }
    
    /**
     * 取消业务收藏（包括问答、宝典、导购、爬房团、新闻）
     * @param type
     * @param itemId
     * @param uid
     * @return true:取消成功;false:取消失败
     */
    public boolean deleteFav(OperateType type, int itemId, int uid) {
        if (type.equals(OperateType.ALL) || itemId <= 0 || uid <= 0) {
            return false;
        }
        
        Map<String, Object> variables = ImmutableMap.of("type", (Object)(type.getValue()), "itemId", (Object)itemId, "uid", (Object)uid);
        
        String rsp = interfaceService.postObjectForString(AppConstants.XIN_FANG_API_V4_FAV_DELETE_URL, null, variables);

        try {
            if (null != rsp) {
                return extractJSONAndReturnBoolean(rsp);
            }
        } catch (Exception e) {
            logger.error("", e);
        }
        
        return false;
    }
    /**
     * 本地取消业务收藏（包括问答、宝典、导购、爬房团、新闻）
     * @param type
     * @param itemId
     * @param uid
     * @return true:取消成功;false:取消失败
     */
    public boolean deleteFavLocal(OperateType type, int itemId, int uid) {
        if (type.equals(OperateType.ALL) || itemId <= 0 || uid <= 0) {
            return false;
        }
        
        Map<String, Object> variables = ImmutableMap.of("type", (Object)(type.getValue()), "itemId", (Object)itemId, "uid", (Object)uid);
        
        String rsp = interfaceService.postObjectForString(AppConstants.XIN_FANG_API_V4_FAV_DELETE_LOCAL_URL, null, variables);

        try {
            if (null != rsp) {
                return extractJSONAndReturnBoolean(rsp);
            }
        } catch (Exception e) {
            logger.error("", e);
        }
        
        return false;
    }
    
    /**
     * TODO:收藏列表
     * @param type
     * @param uid
     * @return
     */
    public JSONObject favList(OperateType type, int uid) {
    	
    	if(uid <= 0)
    	{
    		return null;
    	}
    	
    	Map<String, Object> variables = ImmutableMap.of("type", (Object)(type.getValue()), "uid", (Object)uid);
    	
    	String rsp = interfaceService.getStringFromInterface(AppConstants.XIN_FANG_API_V4_FAV_LIST_URL, variables);
    	try {
			if(rsp!=null)
			{
				return extractJSONAndReturnData(rsp);
			}
		} catch (Exception e) {
			logger.error("", e);
		}
        return null;
    }
    
    /**
     * 查看业务是否被收藏，单条查询
     * @param type
     * @param itmeId
     * @param uid
     * @return true:收藏；false:未收藏
     */
    public boolean isFav(OperateType type, int itemId, int uid) {
        if (type.equals(OperateType.ALL) || itemId <= 0 || uid <= 0) {
            return false;
        }
        
        Map<String, Object> variables = ImmutableMap.of("type", (Object)(type.getValue()), "itemId", (Object)itemId, "uid", (Object)uid);
        
        String rsp = interfaceService.getStringFromInterface(AppConstants.XIN_FANG_API_V4_IS_FAV_URL, variables);
        
        try {
            if (null != rsp) {
                Boolean ret = extractJSONAndReturnObject(rsp, Boolean.class);
                return null == ret ? false : ret; 
            }
        } catch (Exception e) {
            logger.error("", e);
        }
        
        return false;
    }
    
    /**
     * 设置业务有用(只限于问答）
     * @param type
     * @param itemId
     * @param uid
     * @param srcUid
     * @return true:设置成功；false：设置失败
     */
    public boolean addUseful(OperateType type, int itemId, int uid, int srcUid) {
        if (!type.equals(OperateType.QUESTION) || itemId <= 0 || uid <= 0 || srcUid <= 0) {
            return false;
        }
        
        Map<String, Object> variables = ImmutableMap.of("type", (Object)(type.getValue()), "itemId", (Object)itemId, "uid", (Object)uid, "srcUid", (Object)srcUid);
        
        String rsp = interfaceService.postObjectForString(AppConstants.XIN_FANG_API_V4_USEFUL_ADD_URL, null, variables);
        
        try {
            if (null != rsp) {
                return extractJSONAndReturnBoolean(rsp);
            }
        } catch (Exception e) {
            logger.error("", e);
        }
        
        return false;
    }
    
    
    /**
     * 取消设置有用（只限于问答）
     * @param type
     * @param itemId
     * @param uid
     * @return true:取消成功；false：取消失败
     */
    public boolean deleteUseful(OperateType type, int itemId, int uid) {
        if (!type.equals(OperateType.QUESTION) || itemId <= 0 || uid <= 0) {
            return false;
        }
        
        Map<String, Object> variables = ImmutableMap.of("type", (Object)(type.getValue()), "itemId", (Object)itemId, "uid", (Object)uid);
        
        String rsp = interfaceService.postObjectForString(AppConstants.XIN_FANG_API_V4_USEFUL_DELETE_URL, null, variables);
        
        try {
            if (null != rsp) {
                return extractJSONAndReturnBoolean(rsp);
            }
        } catch (Exception e) {
            logger.error("", e);
        }
        
        return false;
    }
    
    /**
     * 查询是否设置业务有用（只限于问答）
     * @param type
     * @param itemId
     * @param uid
     * @return true:已设置；false：未设置
     */
    public boolean isUseful(OperateType type, int itemId, int uid) {
        if (!type.equals(OperateType.QUESTION) || itemId <= 0 || uid <= 0) {
            return false;
        }
        
        Map<String, Object> variables = ImmutableMap.of("type", (Object)(type.getValue()), "itemId", (Object)itemId, "uid", (Object)uid);
        
        String rsp = interfaceService.getStringFromInterface(AppConstants.XIN_FANG_API_V4_IS_USEFUL_URL, variables);
        
        try {
            if (null != rsp) {
                Boolean ret = extractJSONAndReturnObject(rsp, Boolean.class);
                return null == ret ? false : ret; 
            }
        } catch (Exception e) {
            logger.error("", e);
        }
        
        return false;
    }
    
    /**
     * 添加楼盘关注
     * @param uid
     * @param groupId
     * @return
     */
    public boolean addHouseFav(int uid, int groupId) {
        if (uid <= 0 || groupId <= 0) {
            return false;
        }
        
        Map<String, Object> variables = ImmutableMap.of("groupId", (Object)groupId, "uid", (Object)uid);
        
        String rsp = interfaceService.postObjectForString(AppConstants.XIN_FANG_API_V4_HOUSE_FAV_ADD_URL, null, variables);
        
        try {
            if (null != rsp) {
                return extractJSONAndReturnBoolean(rsp);
            }
        } catch (Exception e) {
            logger.error("", e);
        }
        
        return false;
    }
    
    /**
     * 批量取消楼盘关注
     * @param uid
     * @param groupIds
     * @return 失败的条数  -2 表示异常,-1表示参数错误，0表示都成功了， >0表示部分失败了<br/>如果某个groupId没有关注过，则其会失败 
     */
    public int deleteHouseFav(int uid, Integer[] groupIds) {
        if (uid <= 0 || groupIds == null) {
            return -1;
        }
        
        if (groupIds.length == 0) {
            return 0;
        }
        
        int groupsLen = groupIds.length;
        int loop = groupsLen - 2;
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<=loop; i++) {
            sb.append(groupIds[i]).append(',');
        }
        sb.append(groupIds[loop + 1]);
        
        Map<String, Object> variables = ImmutableMap.of("uid", (Object)uid, "groupIds", sb.toString());
        
        String rsp = interfaceService.postObjectForString(AppConstants.XIN_FANG_API_V4_HOUSE_FAV_DELETE_URL, null, variables);
        
        try {
            if (null != rsp) {
                JSONObject data = extractJSONAndReturnData(rsp);
                if (null == data) {
                    return -2;
                }
                int successed = data.getInteger("count");
                return groupsLen - successed;
            }
            
        } catch (Exception e) {
            logger.error("", e);
        }
        
        return -2;
    }
    
    /**
     * 关注的楼盘列表
     * @param uid
     * @param pageNo 默认为1
     * @param pageSize 默认为10
     */
    public List<FavBuildingModel> houseFavList(int uid, int pageNo, int pageSize) {
        if (uid <= 0) {
            return null;
        }
        
        if (pageNo <= 0) {
            pageNo = 1;
        }
        
        if (pageSize <= 0) {
            pageSize = 10;
        }
        
        Map<String, Object> variables = ImmutableMap.of("uid", (Object)uid, "pageNo", (Object)pageNo, "pageSize", (Object)pageSize);
        
        String rsp = interfaceService.getStringFromInterface(AppConstants.XIN_FANG_API_V4_HOUSE_FAV_LIST_URL, variables);
        
        try {
            if (null != rsp) {
                JSONObject data = extractJSONAndReturnData(rsp);
                if (null == data) {
                    return null;
                }
                JSONArray dataArray = data.getJSONArray("data");
                if (null != dataArray && dataArray.size() > 0) {
                    //TODO:判断data.getJSONArray("data")再.toJSONString()是不是获取String的最短路径
                    return JSON.parseArray(dataArray.toJSONString(), FavBuildingModel.class);
                }
            }
        } catch (Exception e) {
            logger.error("", e);
        }
        
        return null;
    }
     
    /**
     * 添加问答
     * @param uid
     * @param groupId
     * @param question
     * @param isAnonymnous 默认false
     * @return < 0 表示错误，>0表示生成的问答ID
     * TODO 检查question字段长度
     */
    public int addQuestion(int uid, int groupId, String question, boolean isAnonymous, int cityId, int buildId) {
        if (uid <=0 || groupId <=0 || StringUtils.isBlank(question)) {
            return -1;
        }
        
        Map<String, Object> variables = new ImmutableMap.Builder<String, Object>().put("uid", uid).put("groupId", groupId)
                .put("question", question).put("isAnonymous", isAnonymous).put("cityId", cityId).put("buildId", buildId).build();
        
        String rsp = interfaceService.postObjectForString(AppConstants.XIN_FANG_API_V4_QUESTION_ADD_URL, null, variables);
        
        try {
            if (null != rsp) {
                JSONObject data = extractJSONAndReturnData(rsp);
                if (null == data) {
                    return -2;
                }
                return data.getIntValue("questionId");
            }
        } catch (Exception e) {
            logger.error("", e);
        }
        
        return -1;
    }
    
    /**
     * 批量删除我的问答
     * @param uid
     * @param questionIds
     * @return
     */
    public boolean deleteQuestions(int uid, Integer[] questionIds) {
        if (uid <= 0 || questionIds == null || questionIds.length == 0) {
            return false;
        }
        
        int questionsLen = questionIds.length;
        int loop = questionsLen - 2;
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<=loop; i++) {
            sb.append(questionIds[i]).append(',');
        }
        sb.append(questionIds[loop + 1]);
        
        Map<String, Object> variables = ImmutableMap.of("uid", (Object)uid, "questionIds", sb.toString());
        
        String rsp = interfaceService.postObjectForString(AppConstants.XIN_FANG_API_V4_QUESTION_DELETE_URL, null, variables);
        
        try {
            if (null != rsp) {
                return extractJSONAndReturnBoolean(rsp);
            }
        } catch (Exception e) {
            logger.error("", e);
        }
        
        return false;
    }
    
    /**
     * 分页查询问答列表
     * @param uid
     * @param pageNo
     * @param pageSize
     */
    public List<FavQuestionModel> questionList(int uid, int pageNo, int pageSize) {
        if (uid <= 0) {
            return null;
        }
        
        if (pageNo <= 0) {
            pageNo = 1;
        }
        
        if (pageSize <= 0) {
            pageSize = 10;
        }
        
        Map<String, Object> variables = ImmutableMap.of("uid", (Object)uid, "pageNo", (Object)pageNo, "pageSize", (Object)pageSize);
        
        String rsp = interfaceService.getStringFromInterface(AppConstants.XIN_FANG_API_V4_QUESTION_LIST_URL, variables);
        
        try {
            if (null != rsp) {
                JSONObject data = extractJSONAndReturnData(rsp);
                if (null == data) {
                    return null;
                }
                JSONArray dataArray = data.getJSONArray("data");
                if (null != dataArray && dataArray.size() > 0) {
                    //TODO:判断data.getJSONArray("data")再.toJSONString()是不是获取String的最短路径
                    return JSON.parseArray(dataArray.toJSONString(), FavQuestionModel.class);
                }
            }
        } catch (Exception e) {
            logger.error("", e);
        }
        
        return null;
    }
    
    /**
     * get editor comment
     * @param goupId
     * @return
     */
    public EditorComment getEditorComment(int groupId) {
    	Map<String ,Object> params = new HashMap<String,Object>();
    	params.put("groupId", groupId);
    	EditorComment ec = null;
    	try {
    		String rsp = interfaceService.getStringFromInterface(AppConstants.XIN_FANG_API_V4_EDITOR_COMMENT_URL, params);
    		if(null!=rsp) {
    			JSONObject data = JSON.parseObject(rsp);
    			if(null==data) {
    				return ec;
    			} 
    			JSONArray ja = data.getJSONArray("data");
    			if(ja!=null && ja.size()>=1) {
    				ec = JSONObject.parseObject(ja.get(0).toString(),EditorComment.class);
    			}
    		}
    		return ec;
    	} catch (Exception e) {
    		logger.error("",e);
    	}
		return ec;	
    }
    
    /**
     * 爬房团城市信息
     * @param cityId
     * @return
     */
    public PftCityInfo getPafangtuanCitiInfo(int cityId) {
    	Map<String,Object> variables = new HashMap<String,Object>();
    	variables.put("cityId", cityId);
    	PftCityInfo info = null;
    	String rst = interfaceService.getStringFromInterface(AppConstants.XIN_FANG_API_V4_PAFANGTUAN_CITY_INFO_URL, variables);
    	try {
    		if(rst!=null && StringUtils.isNotBlank(rst)) {
    			JSONObject obj = extractJSONAndReturnData(rst);
    			if(obj!=null) {
    			info = JSONObject.toJavaObject(obj, PftCityInfo.class);
    			} else {
    				info = null;
    			}
    		}
    		return info;
    	} catch (Exception e ) {
    		logger.error("", e);
    	}
    	return null;
    }
    
    /**
     * 爬房团列表
     * @param cityId
     * @param pageSize
     * @param pageNo
     * @param endDate
     * @return
     */
    public JSONObject getPftListJo(int cityId,int pageSize,int pageNo,Long endDate) {
    	Map<String,Object> variables = new HashMap<String,Object>();
    	variables.put("cityId", cityId);
    	variables.put("pageSize", pageSize);
    	variables.put("pageNo", pageNo);
    	variables.put("endDate", endDate);
    	
    	String rst = interfaceService.getStringFromInterface(AppConstants.XIN_FANG_API_V4_PAFANGTUAN_LIST_URL, variables);
    	
    	try {
    		if(rst!=null && StringUtils.isNotBlank(rst)) {
    			return extractJSONAndReturnData(rst);
    		}
    	} catch (Exception e ) {
    		logger.error("", e);
    	}
    	

    	
    	
    	return null;
    }
    /**
     * 
     */
    public List<PafangtuanDeclaration> getPftDeclaration()
    {
    	String rst = interfaceService.getStringFromInterface(AppConstants.XIN_FANG_API_V4_PAFANGTUAN_DECLARATION_URL, null);
    	try {
    		if(rst!=null && StringUtils.isNotBlank(rst)) {
    			
    			JSONObject obj = JSONObject.parseObject(rst);
    	        if (null != obj) {
    	             Integer errorCode = obj.getInteger("errorCode");
    	             if (null !=errorCode && errorCode == 0) {
    	            	 JSONArray listObj = obj.getJSONArray("data");
    	            	 if(listObj!=null) {
    	    				return JSON.parseArray(listObj.toJSONString(), PafangtuanDeclaration.class);
    	            	 }
    	             }
    	             else {
						return null;
					}
    	         }
    		}
    	} catch (Exception e ) {
    		logger.error("", e);
    	}
    	return null;
    	
    }
    
    public List<PafangtuanTips> getPftTips(Integer cityId)
    {
    	Map<String,Object> variables = new HashMap<String,Object>();
    	variables.put("cityId", cityId);
    	String rst = interfaceService.getStringFromInterface(AppConstants.XIN_FANG_API_V4_PAFANGTUAN_TIPS_URL, variables);
    	try {
    		if(rst!=null && StringUtils.isNotBlank(rst)) {
    	            
    			JSONObject obj = JSONObject.parseObject(rst);
    	        if (null != obj) {
    	             Integer errorCode = obj.getInteger("errorCode");
    	             if (null !=errorCode && errorCode == 0) {
    	            	 JSONArray listObj = obj.getJSONArray("data");
    	            	 if(listObj!=null) {
    	    				return JSON.parseArray(listObj.toJSONString(), PafangtuanTips.class);
    	            	 }
    	             }
    	             else {
						return null;
					}
    	         }
    		}
    	} catch (Exception e ) {
    		logger.error("", e);
    	}
    	return null;
    }

    
    public List<Pafangtuan> getPftList(int cityId,int pageSize,int pageNo,long endDate) {
    	Map<String,Object> variables = new HashMap<String,Object>();
    	variables.put("cityId", cityId);
    	variables.put("pageSize", pageSize);
    	variables.put("pageNo", pageNo);
    	variables.put("endDate", endDate);
    	List<Pafangtuan> pftList = null;
    	String rst = interfaceService.getStringFromInterface(AppConstants.XIN_FANG_API_V4_PAFANGTUAN_LIST_URL, variables);
    	try {
    		if(rst!=null && StringUtils.isNotBlank(rst)) {
    			JSONObject obj = extractJSONAndReturnData(rst);
    			if(obj!=null) {
    				JSONArray listObj = obj.getJSONArray("data");
    				if(listObj!=null) {
    					pftList = JSON.parseArray(listObj.toJSONString(), Pafangtuan.class);
    				}
    			} else {
    				pftList = null;
    			}
    		}
    		return pftList;
    	} catch (Exception e ) {
    		logger.error("", e);
    	}
    	return null;
    }
    
    
    
    /**
     * 爬房团详细
     * @param cityId
     * @param lineId
     * @return
     */
    public Pafangtuan getPftInfo(int cityId,int lineId) {
    	Map<String,Object> variables = new HashMap<String,Object>();
    	variables.put("cityId", cityId);
    	variables.put("lineId", lineId);
    	Pafangtuan pftInfo = null;
    	String rst = interfaceService.getStringFromInterface(AppConstants.XIN_FANG_API_V4_PAFANGTUAN_INFO_URL, variables);
    	try {
    		if(rst!=null && StringUtils.isNotBlank(rst)) {
    			JSONObject obj = extractJSONAndReturnData(rst);
    			if(obj!=null) {
    				pftInfo = JSONObject.toJavaObject(obj, Pafangtuan.class);
    			} else {
    				pftInfo = null;
    			}
    		}
    		return pftInfo;
    	} catch (Exception e ) {
    		logger.error("", e);
    	}
    	return null;
    }
    
    
    
    
    /**
     * 爬房团报名
     * @param cityId
     * @param lineId
     * @param mobile
     * @param name
     * @param uid
     * @return
     */
    public String pftSign(Integer uid ,Integer cityId,Integer lineId,String mobile,String name) {
    	Map<String,Object> variables = new HashMap<String,Object>();
    	variables.put("cityId", cityId);
    	variables.put("lineId", lineId);
    	variables.put("mobile", mobile);
    	variables.put("name", name);
    	if(uid==null)
    	{
    		return interfaceService.postObjectForString(AppConstants.XIN_FANG_API_V4_PAFANGTUAN_SIGN_NOUID_URL, null, variables);
    	}
    	else {
    		variables.put("uid", uid);
    		return interfaceService.postObjectForString(AppConstants.XIN_FANG_API_V4_PAFANGTUAN_SIGN_URL, null,variables);
		}
    	
    	
    }
    
    
    /**
     * groupId获取导购列表
     * @param groupId
     * @param limit
     * @return
     */
    public List<XinfangDaogou> getDaogouByGroupid(int groupId,int limit) {
    	Map<String,Object> variables = new HashMap<String,Object>();
    	variables.put("groupId", groupId);
    	variables.put("limit", limit);
    	List<XinfangDaogou> daogou = null;
    	String rst = interfaceService.getStringFromInterface(AppConstants.XIN_FANG_API_V4_DAOGOU_BY_GROUPID_URL, variables);
    	try {
    		if(rst!=null && StringUtils.isNotBlank(rst)) {
    			JSONArray obj = JSON.parseObject(rst).getJSONArray("data");
    			if(obj!=null) {
    				daogou = JSON.parseArray(obj.toJSONString(), XinfangDaogou.class);
    			} else {
    				daogou = null;
    			}
    		}
    		return daogou;
    	} catch (Exception e ) {
    		logger.error("", e);
    	}
    	return null;
    }
    
    /**
     * groupId获取pc导购列表
     * @param groupId
     * @param limit
     * @return
     */
    public List<BuildingDaoGou> getPcDaogouByGroupid(int groupId,int pageNo,int pageSize) {
    	Map<String,Object> variables = new HashMap<String,Object>();
    	variables.put("groupId", groupId);
    	variables.put("pageNo", pageNo);
    	variables.put("pageSize", pageSize);
    	List<BuildingDaoGou> daogou = null;
    	String rst = interfaceService.getStringFromInterface(AppConstants.XIN_FANG_API_V4_DAOGOU_PC_BY_GROUPID_URL, variables);
    	try {
    		if(rst!=null && StringUtils.isNotBlank(rst)) {
    			JSONArray obj = JSON.parseObject(rst).getJSONObject("data").getJSONArray("data");
    			if(obj!=null) {
    				daogou = JSON.parseArray(obj.toJSONString(), BuildingDaoGou.class);
    			} else {
    				daogou = null;
    			}
    		}
    		return daogou;
    	} catch (Exception e ) {
    		logger.error("", e);
    	}
    	return null;
    }
    
    
    /**
     *解析如下所示的json结构<br/>
     *如果errorCode != 0则返回null<br/>
     *如果errorCode == 0 则返回data的值<br/>  
     * {
     *      "data": {
     *       "flag": true
     *      },
     *      "errorCode": 0,
     *      "errorMessage": "成功"
     *}
     * 
     * @param rsp
     * @return
     */
    private JSONObject extractJSONAndReturnData(String rsp) {
        if (StringUtils.isNotBlank(rsp)) {
            JSONObject obj = JSONObject.parseObject(rsp);
            if (null != obj) {
                Integer errorCode = obj.getInteger("errorCode");
                if (null !=errorCode && errorCode == 0) {
                    return obj.getJSONObject("data");
                }
            }
        }
        return null;
    }
    
    /**
     * 解析如下所示的json结构<br/>
     * 如果errorCode == 0则返回data的数据，否则返回null</br>
     * {
     *      "data":true,
     *      "errorCode":0,
     *      "errorMessage":"成功"
     * }
     * @param rsp
     * @return
     */
    private <T> T extractJSONAndReturnObject(String rsp, Class<T> clazz) {
        if (StringUtils.isNotBlank(rsp)) {
            JSONObject obj = JSONObject.parseObject(rsp);
            if (null != obj) {
                Integer errorCode = obj.getInteger("errorCode");
                if (null != errorCode && errorCode == 0) {
                    return obj.getObject("data", clazz);
                }
            }
        }
        return null;
    }
    
    /**
     * 解析如下所示的json结构<br/>
     * 如果errorCode == 0则返回true，否则返回false</br>
     * {
     *      "data":"",
     *      "errorCode":0,
     *      "errorMessage":"成功"
     * }
     * @param rsp
     * @return
     */
    private boolean extractJSONAndReturnBoolean(String rsp) {
        if (StringUtils.isNotBlank(rsp)) {
            JSONObject obj = JSONObject.parseObject(rsp);
            if (null != obj) {
                Integer errorCode = obj.getInteger("errorCode");
                if (null != errorCode && errorCode == 0) {
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * 用户关注的楼盘Model,为了减少可能的冲突和减少异步获取时的流量，这里没有使用BuildingInfo类
     * @author rogantian
     * @date 2014-4-22
     * @email rogantianwz@gmail.com
     * TODO: 根据页面实际需要而定，有几个字段是不需要的，找个时间和API那边重新定义一下。
     */
    public static class FavBuildingModel {
        
        private String address;
        
        private String area;
        
        private Integer buildId;
        
        private Integer cityId;
        
        private Integer groupId;
        
        private String mainType;
        
        private String name;
        
        private String picUrl;
        
        private String priceDescription;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public Integer getBuildId() {
            return buildId;
        }

        public void setBuildId(Integer buildId) {
            this.buildId = buildId;
        }

        public Integer getCityId() {
            return cityId;
        }

        public void setCityId(Integer cityId) {
            this.cityId = cityId;
        }

        public Integer getGroupId() {
            return groupId;
        }

        public void setGroupId(Integer groupId) {
            this.groupId = groupId;
        }

        public String getMainType() {
            return mainType;
        }

        public void setMainType(String mainType) {
            this.mainType = mainType;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public String getPriceDescription() {
            return priceDescription;
        }

        public void setPriceDescription(String priceDescription) {
            this.priceDescription = priceDescription;
        }
        
        
    }
    
    /**
     * 同FavBuildingModel一样，这里没有使用QuestionInfo类
     * @author rogantian
     * @date 2014-4-22
     * @email rogantianwz@gmail.com
     * TODO: 根据页面实际需要而定，有几个字段是不需要的，找个时间和API那边重新定义一下。
     */
    public static class FavQuestionModel {
        
        private Integer buildId;
        
        private String buildingName;
        
        private String buildPic;
        
        private Integer cityId;
        
        private Integer groupId;
        
        private Integer id;
        
        private boolean isAnswered;
        
        private Integer status;
        
        private String question;
        
        private Integer type;

        public Integer getBuildId() {
            return buildId;
        }

        public void setBuildId(Integer buildId) {
            this.buildId = buildId;
        }

        public String getBuildingName() {
            return buildingName;
        }

        public void setBuildingName(String buildingName) {
            this.buildingName = buildingName;
        }

        public String getBuildPic() {
            return buildPic;
        }

        public void setBuildPic(String buildPic) {
            this.buildPic = buildPic;
        }

        public Integer getCityId() {
            return cityId;
        }

        public void setCityId(Integer cityId) {
            this.cityId = cityId;
        }

        public Integer getGroupId() {
            return groupId;
        }

        public void setGroupId(Integer groupId) {
            this.groupId = groupId;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public boolean isAnswered() {
            return isAnswered;
        }

        public void setAnswered(boolean isAnswered) {
            this.isAnswered = isAnswered;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }
    }
    
    /**
     * 爬房团城市信息
     * @author zhiweiwen
     *
     */
    public static class PftCityInfo implements Serializable{
        private static final long serialVersionUID = -4401376895828352454L;
        private int cityId;
    	private String phone;
    	private String phoneVisiable;
		public int getCityId() {
			return cityId;
		}
		public void setCityId(int cityId) {
			this.cityId = cityId;
		}
		public String getPhone() {
			return phone;
		}
		public void setPhone(String phone) {
			this.phone = phone;
		}
		public String getPhoneVisiable() {
			return phoneVisiable;
		}
		public void setPhoneVisiable(String phoneVisiable) {
			this.phoneVisiable = phoneVisiable;
		}
    }
    
    /**
     * 爬房团楼盘
     * @author zhiweiwen
     *
     */
    public static class PftBuildModel implements Serializable{
        private static final long serialVersionUID = -2681162540671249505L;
        private Integer buildId;//
    	private Integer cityId;
    	private Integer groupId;
    	private String latitude;//纬度
    	private String longitude;//经度
    	private String mainPic;//楼盘图片
    	private String name;//楼盘名称
    	private String nameAbbr;//楼盘名称缩写
    	private String phone;//楼盘电话
    	private String phone2;//对楼盘电话进行格式化后
    	private String phoneFenji;//fenji
    	private String refPrice;//楼盘价格
    	private String address;//楼盘地址
    	private String discount;//优惠
    	private String recommendReason;//推荐原因
		public String getPhoneFenji() {
			return phoneFenji;
		}
		public void setPhoneFenji(String phoneFenji) {
			this.phoneFenji = phoneFenji;
		}
		public Integer getBuildId() {
			return buildId;
		}
		public void setBuildId(Integer buildId) {
			this.buildId = buildId;
		}
		public Integer getCityId() {
			return cityId;
		}
		public void setCityId(Integer cityId) {
			this.cityId = cityId;
		}
		public Integer getGroupId() {
			return groupId;
		}
		public void setGroupId(Integer groupId) {
			this.groupId = groupId;
		}
		public String getLatitude() {
			return latitude;
		}
		public void setLatitude(String latitude) {
			this.latitude = latitude;
		}
		public String getLongitude() {
			return longitude;
		}
		public void setLongitude(String longitude) {
			this.longitude = longitude;
		}
		public String getMainPic() {
			return mainPic;
		}
		public void setMainPic(String mainPic) {
			this.mainPic = mainPic;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getPhone() {
			return phone;
		}
		public void setPhone(String phone) {
			this.phone = phone;
		}
		public String getRefPrice() {
			return refPrice;
		}
		public void setRefPrice(String refPrice) {
			this.refPrice = refPrice;
		}
		public String getNameAbbr() {
			return nameAbbr;
		}
		public void setNameAbbr(String nameAbbr) {
			this.nameAbbr = nameAbbr;
		}
		public String getAddress() {
			return address;
		}
		public void setAddress(String address) {
			this.address = address;
		}
		public String getDiscount() {
			return discount;
		}
		public void setDiscount(String discount) {
			this.discount = discount;
		}
		public String getRecommendReason() {
			return recommendReason;
		}
		public void setRecommendReason(String recommendReason) {
			this.recommendReason = recommendReason;
		}
		public String getPhone2() {
			return phone2;
		}
		public void setPhone2(String phone2) {
			this.phone2 = phone2;
		}
    }
    
    /**
     * 爬房团
     * @author zhiweiwen
     */
    public static class Pafangtuan implements Serializable, Comparable<Pafangtuan>{
    
		private static final long serialVersionUID = 1L;
		private String activeDate;//开团日期
    	private Integer applyRemainedDays;//剩余时间
    	private List<PftBuildModel> builds;
    	private String lightspot;//线路描述
    	private Integer lineId;
    	private Integer signUpNum;//已报名人数
    	private Integer signUpStatus;//1：正常；3已截止报名； 4下线
    	private HouseCollege houseCollege;
    	private String pic;
    	private int buildNum;
    	private String title;//线路标题
    	private Integer commentNum;//评论总数
    	private String lastSignUpDate;//报名截止日期
    	private Integer leftNum;//剩余席位
    	private String interactive;//现场交互事件
    	
    	
    	//地图数据显示 以；分隔的buildsbuildsbuilds
    	private String lnglatsUrl;
    	
    	//将activeDate字段转成Date类型后的字段
    	private Date activeDateRel;
    	
		public HouseCollege getHouseCollege() {
			return houseCollege;
		}
		public void setHouseCollege(HouseCollege houseCollege) {
			this.houseCollege = houseCollege;
		}
		public Integer getLeftNum() {
			return leftNum;
		}
		public void setLeftNum(Integer leftNum) {
			this.leftNum = leftNum;
		}
		public String getLightspot() {
			return lightspot;
		}
		public void setLightspot(String lightspot) {
			this.lightspot = lightspot;
		}
		public Integer getLineId() {
			return lineId;
		}
		public void setLineId(Integer lineId) {
			this.lineId = lineId;
		}
		public Integer getSignUpNum() {
			return signUpNum;
		}
		public void setSignUpNum(Integer signUpNum) {
			this.signUpNum = signUpNum;
		}
		public Integer getSignUpStatus() {
			return signUpStatus;
		}
		public void setSignUpStatus(Integer signUpStatus) {
			this.signUpStatus = signUpStatus;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getActiveDate() {
			return activeDate;
		}
		public void setActiveDate(String activeDate) {
			this.activeDate = activeDate;
		}
		public List<PftBuildModel> getBuilds() {
			return builds;
		}
		public int getBuildNum() {
			return buildNum;
		}
		public void setBuildNum(int buildNum) {
			this.buildNum = buildNum;
		}
		public String getPic() {
			return pic;
		}
		public void setPic(String pic) {
			this.pic = pic;
		}
		public void setBuilds(List<PftBuildModel> builds) {
			this.builds = builds;
		}
		@Override
		public int compareTo(Pafangtuan o) {
			// TODO Auto-generated method stub
		    int thisDate = Integer.valueOf(this.getActiveDate());
		    int thatDate = Integer.valueOf(o.getActiveDate());
		    return thisDate - thatDate;
		}
		public Integer getApplyRemainedDays() {
			return applyRemainedDays;
		}
		public void setApplyRemainedDays(Integer applyRemainedDays) {
			this.applyRemainedDays = applyRemainedDays;
		}
		public Integer getCommentNum() {
			return commentNum;
		}
		public void setCommentNum(Integer commentNum) {
			this.commentNum = commentNum;
		}
		public String getLastSignUpDate() {
			return lastSignUpDate;
		}
		public void setLastSignUpDate(String lastSignUpDate) {
			this.lastSignUpDate = lastSignUpDate;
		}
		public String getInteractive() {
			return interactive;
		}
		public void setInteractive(String interactive) {
			this.interactive = interactive;
		}

		public String getLnglatsUrl() {
			return lnglatsUrl;
		}
		public void setLnglatsUrl(String lnglatsUrl) {
			this.lnglatsUrl = lnglatsUrl;
		}
        public Date getActiveDateRel() {
            return activeDateRel;
        }
        public void setActiveDateRel(Date activeDateRel) {
            this.activeDateRel = activeDateRel;
        }
    }
    
    /**
     * 爬房团活动声明
     */
    public static class PafangtuanDeclaration implements Serializable
    {

		private static final long serialVersionUID = 1L;
    	private String content;
    	private String title;
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
    }
    
    
    /**
     * 爬房团小贴士
     * @author linfangwang
     *
     */
    public static class PafangtuanTips implements Serializable
    {

		private static final long serialVersionUID = 1L;
		private Integer cityId;
    	private String content;
    	private Integer count;
    	private String createTime;
    	private String createrId;
    	private String createrName;
    	private Integer id;
    	private Integer status;
    	private String title;
		public Integer getCityId() {
			return cityId;
		}
		public void setCityId(Integer cityId) {
			this.cityId = cityId;
		}
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
		public Integer getCount() {
			return count;
		}
		public void setCount(Integer count) {
			this.count = count;
		}
		public String getCreateTime() {
			return createTime;
		}
		public void setCreateTime(String createTime) {
			this.createTime = createTime;
		}
		public String getCreaterId() {
			return createrId;
		}
		public void setCreaterId(String createrId) {
			this.createrId = createrId;
		}
		public String getCreaterName() {
			return createrName;
		}
		public void setCreaterName(String createrName) {
			this.createrName = createrName;
		}
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		public Integer getStatus() {
			return status;
		}
		public void setStatus(Integer status) {
			this.status = status;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
    }
    
    
    
    /**
     * 新房导购
     * @author zhiweiwen
     *
     */
    public static class XinfangDaogou {
    	private int cityId;
    	private int proposeId;
    	private String title;
		public int getCityId() {
			return cityId;
		}
		public void setCityId(int cityId) {
			this.cityId = cityId;
		}
		public int getProposeId() {
			return proposeId;
		}
		public void setProposeId(int proposeId) {
			this.proposeId = proposeId;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
    }
    
    /**
     * 学区房
     * @author zhiweiwen
     *
     */
    public static class HouseCollege {
    	private int id;//编辑ID号
    	private String nickName;//编辑姓名
    	private List<String> outline;//编辑大纲
    	private String pic;//编辑图片
    	private String title;//编辑头衔
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getNickName() {
			return nickName;
		}
		public void setNickName(String nickName) {
			this.nickName = nickName;
		}
		public List<String> getOutline() {
			return outline;
		}
		public void setOutline(List<String> outline) {
			this.outline = outline;
		}
		public String getPic() {
			return pic;
		}
		public void setPic(String pic) {
			this.pic = pic;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
    }
    
    /**
     * editor commmit
     * @author zhiweiwen
     *
     */
    public static class EditorComment {
    	
    	private String editorTitle;
    	private String nickName;
    	private String picUrl;
    	private String recommendContent;
		public String getEditorTitle() {
			return editorTitle;
		}
		public void setEditorTitle(String editorTitle) {
			this.editorTitle = editorTitle;
		}
		public String getNickName() {
			return nickName;
		}
		public void setNickName(String nickName) {
			this.nickName = nickName;
		}
		public String getPicUrl() {
			return picUrl;
		}
		public void setPicUrl(String picUrl) {
			this.picUrl = picUrl;
		}
		public String getRecommendContent() {
			return recommendContent;
		}
		public void setRecommendContent(String recommendContent) {
			this.recommendContent = recommendContent;
		}
    	
    	
    	
    }
    
    /**
     * 测试obj.getString("data")底层的执行逻辑
     * @param args
     */
    public static void main(String[] args) {
        //JSONObject obj = JSONObject.parseObject("{\"data\":{\"flag\":true},\"errorCode\":0,\"errorMessage\":\"成功\"}");
        JSONObject obj = JSONObject.parseObject("{\"data\":\"\",\"errorCode\":0,\"errorMessage\":\"成功\"}");
        if (null != obj) {
            Integer errorCode = obj.getInteger("errorCode");
            if (null !=errorCode && errorCode == 0) {
                String a = obj.getString("data");//obj.getString("data")如果data里不为空则获取到的是JSONObject，然后调用该JSONObject.toString();如果data里是空字符串""则会获取到空字符串，然后返回"".toString()，还是""
                JSONObject b = obj.getJSONObject("data");//如果data里为空字符串会报错
                System.out.println("a:" + a);
                System.out.println("b:" + b);
            }
        }
    }
    
}
