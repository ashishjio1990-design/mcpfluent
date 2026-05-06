package com.fluent.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Skips clearAppDataAndRestart() in @BeforeEach for tests that intentionally
 * chain off the app state left by a preceding @Order test.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SkipAppReset {
}
