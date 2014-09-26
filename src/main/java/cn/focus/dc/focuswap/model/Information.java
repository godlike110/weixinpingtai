package cn.focus.dc.focuswap.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import cn.focus.dc.news.model.es.EsNews;

/**
 * Created with IntelliJ IDEA. User: zhengxuan Date: 13-10-25 Time: 下午1:43
 * http://10.10.90.156/wiki/index.php/News/info
 */
public class Information extends News {

    private static final long serialVersionUID = -5858004648340396089L;
    
    public Information(){}
    
    private String author;
    
    private Integer cityId;
    
    private String keywords;
    
    private Integer splitPage;//正文分页数
    
    private Integer templateType;//0:图片新闻 1:幻灯片新闻
    
    private String sourceName;//新闻来源
    
    private Integer ifCopyright;
    
    private Integer relatedId;//相关新闻的Id
    
    private String newsDesc; 
    
    private String tagsAndKeyword;
    
    private PageContent pageContent;
       
    private List<Map> newsCategoryInfoList;
      
    private String imgLogo;
    
    private Integer status;
    
    private Integer type;
    
    private String newsUrl;
    
    
    

    public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getNewsUrl() {
		return newsUrl;
	}

	public void setNewsUrl(String newsUrl) {
		this.newsUrl = newsUrl;
	}

	public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getImgLogo() {
        return imgLogo;
    }

    public void setImgLogo(String imgLogo) {
        this.imgLogo = imgLogo;
    }

   

    public List<Map> getNewsCategoryInfoList() {
        return newsCategoryInfoList;
    }

    public void setNewsCategoryInfoList(List<Map> newsCategoryInfoList) {
        this.newsCategoryInfoList = newsCategoryInfoList;
    }

    public Integer getIfCopyright() {
        return ifCopyright;
    }

    public void setIfCopyright(Integer ifCopyright) {
        this.ifCopyright = ifCopyright;
    }

    public String getTagsAndKeyword() {
        return tagsAndKeyword;
    }

    public void setTagsAndKeyword(String tagsAndKeyword) {
        this.tagsAndKeyword = tagsAndKeyword;
    }

    public Integer getRelatedId() {
        return relatedId;
    }

    public void setRelatedId(Integer relatedId) {
        this.relatedId = relatedId;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public PageContent getPageContent() {
        return pageContent;
    }

    public void setPageContent(PageContent pageContent) {
        this.pageContent = pageContent;
    }


    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }   

    public String getNewsDesc() {
        return newsDesc;
    }

    public void setNewsDesc(String newsDesc) {
        this.newsDesc = newsDesc;
    }
    
    public Integer getSplitPage() {
        return splitPage;
    }

    public void setSplitPage(Integer splitPage) {
        this.splitPage = splitPage;
    }


    public Integer getTemplateType() {
        return templateType;
    }

    public void setTemplateType(Integer templateType) {
        this.templateType = templateType;
    }
    
    //transform
    public void setPicUrl(String imgLogo) {
        this.imgLogo = imgLogo;
    }
    
    public void setNewsSummary(String newsSummary) {
        this.newsDesc = newsSummary;
    }

    public void setShowTime(String showTime) {
        setTime(showTime);
    }

    public static List<EsNewsExt> toEsNews(List<Information> newsList){
        List<EsNewsExt> result = new ArrayList<EsNewsExt>();
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy年M月d日");       
        for(Information news : newsList){
        	EsNewsExt newsEs = new EsNewsExt();
            newsEs.setTitle(news.getTitle());
            newsEs.setPartContent(news.getNewsDesc());
            newsEs.setImgLogo(news.getImgLogo());
            newsEs.setSourceName(news.getSourceName());
            String time = news.getTime();
            if(null!=news.getNewsId()) {
            	newsEs.setNewsId(news.getNewsId().longValue());
            }
            if(null!=news.getNewsUrl()) {
            	newsEs.setNewsUrl(news.getNewsUrl());
            }
            if(StringUtils.isNotBlank(time)){
                try {
                    Date date = sdf2.parse(time);
                    newsEs.setShowTime(date.getTime());                
                } catch (ParseException e) {
                    newsEs.setShowTime(new Long(0));
                }               
            }
            result.add(newsEs);
        }
        
        return result;
    }

}
