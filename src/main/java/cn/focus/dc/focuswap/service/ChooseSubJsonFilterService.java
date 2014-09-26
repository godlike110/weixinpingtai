package cn.focus.dc.focuswap.service;

import cn.focus.dc.focuswap.model.EsProjInfoChild;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;

import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

/**
 * 过滤楼盘筛选页的ajax返回参数
 * @author kanezheng
 *
 */
@Service
public class ChooseSubJsonFilterService extends SimplePropertyPreFilter{

    private static ChooseSubJsonFilterService single=null;
    
    private static Set<String> includes = null;
    
    private Class<?> clazz = null;
    
    @PostConstruct
    public static void init() {
        single = new ChooseSubJsonFilterService();
        SimplePropertyPreFilter father = new SimplePropertyPreFilter(EsProjInfoChild.class, "buildingUrl", "projName",
                "url", "saleStatus", "projAddress", "avgPriceShow", "discount");
        includes = father.getIncludes();
    }
    
    public static ChooseSubJsonFilterService getInstance(){
        return single;
    }
    
    public Class<?> getClazz() {
        return EsProjInfoChild.class;
    }
    
    public boolean apply(JSONSerializer serializer, Object source, String name) {
        if (source == null) {
            return true;
        }

        if (clazz != null && !clazz.isInstance(source)) {
            return true;
        }

        if (includes.size() == 0 || includes.contains(name)) {
            return true;
        }

        return false;
    }
}
