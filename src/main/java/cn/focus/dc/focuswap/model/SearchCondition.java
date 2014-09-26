package cn.focus.dc.focuswap.model;

import cn.focus.dc.commons.model.BaseObject;
import cn.focus.dc.focuswap.service.SearchService.SaleStatus;

import java.util.List;

public class SearchCondition extends BaseObject {
    
    private static final long serialVersionUID = -3628604009449149371L;
    
    private int cityId;
    
    private String queryStr;
       
    private String conditionsName;
    
    private int pageSize;
    
    private int pageNo;
    
    private SaleStatus saleStatus;
    
    List<QueryData> queryData;
    
    /**
     * new index about homePage in 2014/5/19
     */
    //private boolean sanDaiTongTang;
    
    //private boolean lowTotalPrice;
    
    //private boolean villa;
    
    //private boolean park;
    
    //private boolean edu;
    
    //private boolean moonHouse;
    
    /**
     * new index about calculator in 2014/5/19
     */
    private boolean notEnsureHouse;
    
    private boolean notSelloutHouse;
    
    /**
     * modified by rogantain @2014-08-07 根据groupId查询楼盘 
     */
    private List<Integer> groupIds;

    public SearchCondition(List<QueryData> list, int pageNo, int pageSize, int cityId){
        this.setQueryData(list);
        this.setPageNo(pageNo);
        this.setPageSize(pageSize);
        this.setCityId(cityId);
    }
    
    public SearchCondition(){
        
    }
    
    public boolean isNotEnsureHouse() {
        return notEnsureHouse;
    }

    public void setNotEnsureHouse(boolean notEnsureHouse) {
        this.notEnsureHouse = notEnsureHouse;
    }

    public boolean isNotSelloutHouse() {
        return notSelloutHouse;
    }

    public void setNotSelloutHouse(boolean notSelloutHouse) {
        this.notSelloutHouse = notSelloutHouse;
    }

    public int getCityId() {
        return cityId;
    }

    public String getQueryStr() {
        return queryStr;
    }

    public List<QueryData> getQueryData() {
        return queryData;
    }

    public int getPageSize() {
        return pageSize == 0 ? 3 : pageSize;
    }

    public int getPageNo() {
        return pageNo == 0 ? 0 : pageNo;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public void setQueryStr(String queryStr) {
        this.queryStr = queryStr;
    }

    public void setQueryData(List<QueryData> queryData) {
        this.queryData = queryData;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public String getConditionsName() {
        return conditionsName;
    }

    public void setConditionsName(String conditionsName) {
        this.conditionsName = conditionsName;
    }

    public SaleStatus getSaleStatus() {
        return saleStatus;
    }

    public void setSaleStatus(SaleStatus saleStatus) {
        this.saleStatus = saleStatus;
    }

    public List<Integer> getGroupIds() {
        return groupIds;
    }

    public void setGroupIds(List<Integer> groupIds) {
        this.groupIds = groupIds;
    }

}