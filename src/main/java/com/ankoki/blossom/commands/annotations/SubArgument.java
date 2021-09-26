package com.ankoki.blossom.commands.annotations;

import com.ankoki.blossom.commands.CommandManager;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SubArgument {
    String name();
    String lastArgument();
    Class<?> parameter();
    CommandManager.CommandUser sender() default CommandManager.CommandUser.BOTH;
}