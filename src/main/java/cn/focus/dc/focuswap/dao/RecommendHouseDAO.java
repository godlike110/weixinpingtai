package cn.focus.dc.focuswap.dao;

import cn.focus.dc.focuswap.model.RecommendHouse;

import java.util.Date;
import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

@DAO(catalog = "xinfang")
public interface RecommendHouseDAO {

    @SQL("SELECT group_id ,pic_url,recommend_date from recommend_house where city_id = :1 and recommend_date >= :4  "
            + "and recommend_date < :5 and status = 4 order by soft desc limit :2,:3")
    List<RecommendHouse> getRecommendIdList(int cityId, int offset, int limits, long startDate, long endDate);

    @SQL("SELECT effective_time from recommend_house_effective_time where city_id = :1")
    Date getRecommendStartDate(int cityId);
    
    @SQL("SELECT group_id, reason from recommend_house where city_id = :1 and proj_id = :2 and status = 4 order by "
            +"recommend_date desc limit 1 ")
    RecommendHouse getRecommendHouseReason(int cityId, int projId);

}
