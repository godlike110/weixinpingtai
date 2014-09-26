package cn.focus.dc.focuswap.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 接口校验，添加了此注解的url在调用的时候会自动在查询字符串中添加校验参数，</br>
 * 校验参数的计算方式根据AuthKeyType的值不同而不同
 * @author rogantian
 * @date 2014-4-11
 * @email rogantianwz@gmail.com
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface AuthKey {

    //计算校验值时使用的secretKey
    String key() default "";
    
    //校验方式
    AuthKeyType type();
}
