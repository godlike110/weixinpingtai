package cn.focus.dc.focuswap.model;

import cn.focus.dc.commons.model.BaseObject;

/**
 * Created with IntelliJ IDEA.
 * User: yunguangwang
 * Date: 13-10-14
 * Time: 下午12:13
 * To change this template use File | Settings | File Templates.
 */
public class Picture extends BaseObject {
    /**
     * 
     */
    private static final long serialVersionUID = -3628604009449149371L;

    private String url;

    private int width;

    private int height;

    public Picture() {
    }

    public Picture(String url, int width, int height) {
        this.url = url;
        this.width = width;
        this.height = height;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
