package cn.focus.dc.focuswap.utils;

import cn.focus.dc.focuswap.model.DictCity;

public final class FactoryUtil {

    private static FactoryUtil factory = new FactoryUtil();

    public static FactoryUtil getFactory() {
        return factory;
    }

    public DictCity getCityById(int cityId) {
        DictCity city = new DictCity();
        return city;
    }

    public DictCity getCityByNameOrPinyin(String nameOrPinyin) {
        DictCity city = new DictCity();
        return city;
    }

}
