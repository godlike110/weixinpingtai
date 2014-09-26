package cn.focus.dc.focuswap.model;

import cn.focus.dc.commons.model.BaseObject;

/**
 * Created with IntelliJ IDEA. User: zhengxuan Date: 13-10-25 Time: 下午1:43
 * http://10.10.90.156/wiki/index.php/News_center/news/page/info
 */

public class PageContent extends BaseObject {
    
    private static final long serialVersionUID = -5858004648340396089L;

    private String content;

    private String pageTitle;

    private int page;

    private String graphContent;

    private String frontContent;

    private String newsSummary;
    
    
    
    public String getNewsSummary() {
        return newsSummary;
    }

    public void setNewsSummary(String newsSummary) {
        this.newsSummary = newsSummary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPageTitle() {
        return pageTitle;
    }

    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getGraphContent() {
        return graphContent;
    }

    public void setGraphContent(String graphContent) {
        this.graphContent = graphContent;
    }

    public String getFrontContent() {
        return frontContent;
    }

    public void setFrontContent(String frontContent) {
        this.frontContent = frontContent;
    }

}
