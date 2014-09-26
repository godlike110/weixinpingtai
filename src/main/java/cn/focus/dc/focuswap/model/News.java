package cn.focus.dc.focuswap.model;

import cn.focus.dc.commons.model.BaseObject;
import static cn.focus.dc.focuswap.config.AppConstants.*;

/**
 * Created with IntelliJ IDEA.
 * User: yunguangwang
 * Date: 13-9-2
 * Time: 下午5:06
 * To change this template use File | Settings | File Templates.
 */
public class News extends BaseObject {
    /**
     * 
     */
    private static final long serialVersionUID = -5858004648340396089L;

    private Integer newsId;

    private Integer newsType;

    private String time;
    
    private String imgLogo;

    public String getImgLogo() {
		return imgLogo;
	}

	public void setImgLogo(String imgLogo) {
		this.imgLogo = imgLogo;
	}

	private String title;

    private String description;

    private String content;

    private String listPic;

    private String picUrl;
    

    private String summary;
    
    private String media;


    public News() {
    }

    public Integer getNewsId() {

        return newsId;
    }

    public void setNewsId(Integer newsId) {
        this.newsId = newsId;
    }

    public Integer getNewsType() {
        return newsType;
    }

    public void setNewsType(Integer newsType) {
        this.newsType = newsType;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public String getPicUrl() {
        if(this.picUrl == null) {
            picUrl = DEFAULT_PIC_URL;
        }
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getListPic() {
        return listPic;
    }

    public void setListPic(String listPic) {
        this.listPic = listPic;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

	public String getMedia() {
		return media;
	}

	public void setMedia(String media) {
		this.media = media;
	}


}
