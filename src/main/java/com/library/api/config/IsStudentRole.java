package com.library.api.config;/*
 * @created 7/31/2021
 *
 * @Author Poran chowdury
 */

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasRole('ROLE_STUDENT')")
public @interface IsStudentRole {
}
