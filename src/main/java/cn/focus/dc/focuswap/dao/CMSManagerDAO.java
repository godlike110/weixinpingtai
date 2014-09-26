package cn.focus.dc.focuswap.dao;

import cn.focus.dc.focuswap.model.CMSManager;

import java.util.List;
import java.util.Set;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

@DAO(catalog = "xinfang")
public interface CMSManagerDAO {

    @SQL("SELECT  id, account_id, email, password, city_id, name, position, last_login_ip, "
            + "last_login_date, phone_number, expiry_date, status FROM cms_manager WHERE id = :1")
    CMSManager get(Integer id);

    @SQL("SELECT  id, account_id, email, password, city_id, name, position, last_login_ip, "
            + "last_login_date, phone_number, expiry_date, status FROM cms_manager")
    List<CMSManager> getAll();
    
    @SQL("SELECT  id, account_id, email, password, city_id, name, position, "
            + " name, pic_url "
            + " FROM cms_manager WHERE id IN (:1)")
    List<CMSManager> getList(Set<Integer> idSet);
}
