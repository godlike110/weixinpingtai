package cn.focus.dc.focuswap.dao;

import cn.focus.dc.focuswap.model.DictProjTypeExtNew;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

@DAO(catalog = "xinfang")
public interface ConditionDAO {

    @SQL("SELECT  type_id_old FROM build_type_map WHERE city_id = :1 AND type_id_new = :2")
    List<DictProjTypeExtNew> getOldIds(Integer cityId,String typeId);
}
