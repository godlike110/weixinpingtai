package cn.focus.dc.focuswap.dao;

import java.util.List;
import java.util.Set;

import cn.focus.dc.focuswap.model.UserDetail;
import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.annotation.ShardBy;
import net.paoding.rose.jade.annotation.ShardParam;

@DAO(catalog = "xinfang")
public interface UserDetailDAO {

        @SQL("SELECT  uid, app_id, nick_name, head_img, age, down_payment, is_accepted, income, city, "
            + "target_area, follow_count, favorite_count, quiz_count, update_time "
            + "FROM $user_detail WHERE uid = :1")
        UserDetail get(Integer id  ,@ShardBy int uid);

        /**
         * 批量获取用户，限定同一张表中
         * @param tableName
         * @param ids
         * @return
         */
        @SQL("SELECT  uid, app_id, nick_name, head_img, age, down_payment, is_accepted, income, city, "
                + "target_area, follow_count, favorite_count, quiz_count, update_time "
                + "FROM $user_detail WHERE uid IN (:1)")
        List<UserDetail> getList(Set<Integer> uids, @ShardBy int shard);
}
