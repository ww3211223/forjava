package com.nonono.test.confiImport;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(AsyncConfigurationSelector.class)
@Documented
public @interface EnableAsync {
}
