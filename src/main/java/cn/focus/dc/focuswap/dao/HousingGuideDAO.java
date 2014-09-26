/**
 *
 */
package cn.focus.dc.focuswap.dao;

import cn.focus.dc.focuswap.config.StatusConstants;
import cn.focus.dc.focuswap.model.HousingGuide;
import cn.focus.dc.focuswap.model.HousingGuideCatagory;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.annotation.SQLParam;

/**
 * @author yunguangwang
 * @2013-8-27 @下午4:46:21
 */
@DAO(catalog = "xinfang")
public interface HousingGuideDAO {

    @SQL("SELECT id, title, summary, catagory_id, like_count, pic_url, content, create_time " 
    + "FROM housing_guide WHERE id = :1")
    HousingGuide get(int id);

    @SQL("SELECT id, name FROM housing_guide_catagory where id = :1")
    HousingGuideCatagory getGuideCatagoryById(int id);

    /**
     * 获取相应类别的宝典列表
     * 
     * @param start
     * @param size
     * @param catagoryId
     * @return
     */
    @SQL("SELECT id, title, summary,  catagory_id, like_count, pic_url, content, create_time "
            + " FROM housing_guide WHERE status="
            + StatusConstants.COMMITED
            + " #if(:catagoryId!=null && :catagoryId!=0 ){ AND catagory_id=:catagoryId } "
            + " ORDER BY create_time DESC LIMIT :start,:size")
    List<HousingGuide> getListByCatagoryId(@SQLParam("start") Integer start,
            @SQLParam("size") Integer size,
            @SQLParam("catagoryId") Integer catagoryId);

    /**
     * 获取所有提交的宝典列表
     * 
     * @param start
     * @param size
     * @return
     */
    @SQL("SELECT id, title, summary,  catagory_id, like_count, pic_url, content, create_time "
            + " FROM housing_guide WHERE status="
            + StatusConstants.COMMITED
            + " ORDER BY create_time DESC LIMIT :start,:size")
    List<HousingGuide> getList(@SQLParam("start") Integer start,
            @SQLParam("size") Integer size);
}
