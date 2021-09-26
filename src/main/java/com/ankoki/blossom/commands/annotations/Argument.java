package com.ankoki.blossom.commands.annotations;

import com.ankoki.blossom.commands.CommandManager.CommandUser;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Argument {
    String name();
    Class<?> parameter();
    String permisson() default "";
    CommandUser sender() default CommandUser.BOTH;
}