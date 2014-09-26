package cn.focus.dc.focuswap.model;

import cn.focus.dc.commons.model.BaseObject;
import cn.focus.dc.focuswap.service.SearchService.SearchType;

public class QueryData extends BaseObject {

    private static final long serialVersionUID = -3628604009449149371L;
    
    //查询类型
    private SearchType queryType;
    
    //查询值
    private String qSelect;

    public SearchType getQueryType() {
        return queryType;
    }

    public String getQSelect() {
        return qSelect;
    }

    public void setQueryType(SearchType queryType) {
        this.queryType = queryType;
    }

    public void setqSelect(String qSelect) {
        this.qSelect = qSelect;
    }
    
}
