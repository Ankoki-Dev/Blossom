package com.ankoki.blossom.commands;

import com.ankoki.blossom.commands.annotations.CommandInfo;

public final class CommandManager {

    public static void registerCommand(Class<?> clazz) {
        if (clazz.getAnnotation(CommandInfo.class) == null) throw new IllegalArgumentException("You need to register a class with the @CommandInfo annotation");
        // ...
    }

    public enum CommandUser {
        CONSOLE, PLAYER, BOTH;
    }
}
