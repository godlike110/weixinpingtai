package cn.focus.dc.focuswap.model;

public interface PhotoRealPathGenerable {

    /**
     * 设置图片的真实路径
     * @param realPath
     */
    void setRealPath(String realPath);
    
    /**
     * 获取图片的名称，最长为64
     * @return
     */
    String getPhotoAlias();
    
    /**
     * 图片相对路径
     * @return
     */
    String getPath();
    
    /**
     * 图片所属城市
     * @return
     */
    Integer getCityId();
    
    Integer getPhotoWidth();
    
}
