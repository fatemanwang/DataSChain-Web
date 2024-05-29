package com.sltx.common.utils;

import java.lang.annotation.*;

/**
 * parameter is not empty
 * @author Rlax
 *
 */
@Documented
@Target(ElementType.METHOD)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface NotNullPara {

    String[] value();

    String errorRedirect() default "";
}
