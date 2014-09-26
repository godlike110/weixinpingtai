package cn.focus.dc.focuswap.config;

import java.lang.reflect.Field;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.SimpleTypeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

@Component
public class PropertyInjectBeanPostProcessor extends InstantiationAwareBeanPostProcessorAdapter {

    @Autowired
    private ConfigBean configBean;

    private SimpleTypeConverter typeConverter = new SimpleTypeConverter();

    public boolean postProcessAfterInstantiation(Object bean, String beanName) {
        if ("appConstants".equals(beanName)) {
            findPropertyAutowiringMetadata(bean);
        }
        return true;
    }

    private void findPropertyAutowiringMetadata(final Object bean) {
        ReflectionUtils.doWithFields(bean.getClass(), new ReflectionUtils.FieldCallback() {
            public void doWith(Field field) throws IllegalAccessException {
                ConfigProperty annotation = field.getAnnotation(ConfigProperty.class);
                if (annotation != null) {
                    Object strValue = configBean.getProperty(annotation.value());
                    if (null != strValue) {
                        findPropertySecretKey(field,String.valueOf(strValue));
                        findPropertyAuthKey(field,String.valueOf(strValue));
                        Object value = typeConverter.convertIfNecessary(strValue, field.getType());
                        ReflectionUtils.makeAccessible(field);
                        field.set(null, value);
                    }
                }
            }
        });
    }
    
    /**
     * @author kanezheng
     * @查询每个field上面是否有Secretkey
     * @param field
     * @param key
     */
    private void findPropertySecretKey(Field field,String key) {
        Secretkey annotation = field.getAnnotation(Secretkey.class);
        if (annotation != null) {   
            Object strValue = configBean.getProperty(annotation.value());        
            Boolean isMd5 = annotation.isMd5();    
            String path = StringUtils.substringBefore(key, "?");
            path = StringUtils.substringAfter(path, "http://");
            AppConstants.secretKeyMap.put(path, new SecretkeyPair(isMd5, String.valueOf(strValue)));
        }
    }
    
    /**
     * 解析注解AuthKey
     * @param field
     * @param url
     */
    private void findPropertyAuthKey(Field field, String url) {
        AuthKey annotation = field.getAnnotation(AuthKey.class);
        if (null != annotation) {
            Object keyVal = configBean.getProperty(annotation.key());
            String path = StringUtils.substringBefore(url, "?");
            path = StringUtils.substringAfter(path, "http://");
            AuthKeyType type = annotation.type();
            AppConstants.authKeyMap.put(path, new AuthKeyPair(type,String.valueOf(keyVal)));
        }
    }
}