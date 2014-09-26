package cn.focus.dc.focuswap.model;

import cn.focus.dc.news.model.es.EsNews;

public class EsNewsExt extends EsNews {

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
	
}
