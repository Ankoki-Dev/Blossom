package com.ankoki.blossom.commands;

import com.ankoki.blossom.commands.annotations.CommandInfo;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;

public final class CommandManager {

    private static final Map<Class<?>, Object> instances = new HashMap<>();

    public static void registerCommand(Class<?> clazz, Plugin plugin) {
        CommandInfo info = clazz.getAnnotation(CommandInfo.class);
        if (info == null) throw new IllegalArgumentException("You need to register a class with the @CommandInfo annotation");
        PluginCommand command = BlossomCommand.from(clazz, plugin, info.name())
                .setDescription(info.description())
                .setPermission(info.permission())
                .setPermissionMessage(info.permissionMessage())
                .setAliases(info.aliases())
                .createCommand();
        Bukkit.getCommandMap().register(info.name(), command);
    }

    public static Object getInstance(Class<?> clazz) {
        try {
            return instances.containsKey(clazz) ? instances.get(clazz) : clazz.newInstance();
        } catch (ReflectiveOperationException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public enum CommandUser {
        CONSOLE, PLAYER, BOTH;
    }
}
