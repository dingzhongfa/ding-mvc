package com.ding.frame.annotation;

import java.lang.annotation.*;

/**
 * @author dingzhongfa
 * @email : 71964899@qq.com
 * @date 2019-07-01 下午9:41
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DingAutowired {
    String value() default "";
}
