package cn.focus.dc.focuswap.model;

import cn.focus.dc.commons.model.BaseObject;
import cn.focus.dc.news.model.es.EsNews;

public class EsNewsDecorate extends BaseObject{

    private static final long serialVersionUID = 1L;

    public String showTime;
    
    public EsNews esNews;
     

    public String getShowTime() {
        return showTime;
    }

    public void setShowTime(String showTime) {
        this.showTime = showTime;
    }

    public EsNews getEsNews() {
        return esNews;
    }

    public void setEsNews(EsNews esNews) {
        this.esNews = esNews;
    }
    
}
