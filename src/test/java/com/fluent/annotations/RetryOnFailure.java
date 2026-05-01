package com.fluent.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Mark a test method to retry on transient failures.
 * Works with RetryExtension registered on AndroidBaseTest.
 *
 * Usage: @RetryOnFailure(times = 2)
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RetryOnFailure {
    int times() default 1;
}
