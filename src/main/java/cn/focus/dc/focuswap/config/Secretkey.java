package cn.focus.dc.focuswap.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于接口的SecretKey, 在header中添加校验值
 * 
 * @author kane
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface Secretkey {
    String value() default "";

    boolean isMd5() default true;
}
