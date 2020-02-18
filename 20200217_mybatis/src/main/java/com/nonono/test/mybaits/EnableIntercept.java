package com.nonono.test.mybaits;


import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Import(InterceptRegistrar.class)
@Documented
public @interface EnableIntercept {
}
