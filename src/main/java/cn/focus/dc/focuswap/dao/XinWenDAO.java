package cn.focus.dc.focuswap.dao;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;
import cn.focus.dc.focuswap.model.Information;

@DAO(catalog = "xinfang")
public interface XinWenDAO {

    @SQL("SELECT news_id,city_id,title,pic_url,time,source_name,news_summary,news_url,type FROM local_news WHERE city_id = :1 and mode = 0 order by priority")
    List<Information> getHandNews(Integer cityId);
}

