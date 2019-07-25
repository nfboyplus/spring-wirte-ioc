package com.ioc.cn.etc.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * created on 2019/7/24 16:44
 *
 * @author nfboy_liusong@163.com
 * @version 1.0.0
 */
@Target({TYPE, FIELD, METHOD})
@Retention(RUNTIME)
public @interface ExtResource {

}
