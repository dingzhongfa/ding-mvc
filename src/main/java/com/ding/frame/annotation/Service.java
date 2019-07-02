package com.ding.frame.annotation;

import java.lang.annotation.*;

/**
 * @author dingzhongfa
 * @email : 71964899@qq.com
 * @date 2019-07-01 下午9:46
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Service {
    String value() default "";
}
