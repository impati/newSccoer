package com.example.newscoccer.controller.validator;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * entity 를 찾지 못하는 경우를 검사해야되는 곳에 붙임.
 * 반드시 첫번쨰 인자가 Long 타입 pk
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NotFoundValidation {
}
