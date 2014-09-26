package cn.focus.dc.focuswap.dao;


import cn.focus.dc.focuswap.model.DictCity;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

@DAO(catalog = "xinfang")
public interface DictCityDAO {

    @SQL("SELECT  city_id, city_name, city_coding, city_pinyin, city_pinyin_abbr, domain_name, "
            + "type, priority FROM dict_city WHERE city_id = :1 AND wap_status>=1")
    DictCity get(Integer id);

    @SQL("SELECT  city_id, city_name, city_coding, city_pinyin, city_pinyin_abbr, domain_name, "
            + "type, priority,initial FROM dict_city WHERE wap_status>=1")
    List<DictCity> getAll();
    
    @SQL("SELECT city_id, city_name, city_coding, city_pinyin, city_pinyin_abbr,type, priority FROM dict_city "
    		+"where domain_name=:1  AND wap_status>=1")
    DictCity getCityByDomainname(String domain_name);
    
    @SQL("SELECT city_id, city_name, city_coding, city_pinyin, city_pinyin_abbr, domain_name, "
            + "type, priority FROM dict_city where city_name = :1 AND wap_status>=1")
    DictCity getCityIdByName(String cityName);
    
    @SQL("SELECT city_id, city_name, city_coding, city_pinyin, city_pinyin_abbr, domain_name, "
            + "type, priority FROM dict_city where city_pinyin_abbr = :1 AND wap_status>=1")
    DictCity getCityByPinYin(String pinyin);
        
    @SQL("SELECT  city_id, city_name, city_coding, city_pinyin, city_pinyin_abbr, domain_name, "
            + "type, priority, wap_status FROM dict_city where city_pinyin_abbr = :1")
    DictCity getCityByPinYinIgnoringStatus(String pinYin);
    
    @SQL("SELECT  city_id, city_name, city_coding, city_pinyin, city_pinyin_abbr, domain_name, "
            + "type, priority, wap_status FROM dict_city where city_id = :1")
    DictCity getCityByIdIgnoringStatus(int id);
}
