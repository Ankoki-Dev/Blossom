package com.ankoki.blossom.commands;

import com.ankoki.blossom.commands.annotations.CommandInfo;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.SimplePluginManager;

import java.lang.reflect.Field;

public final class CommandManager {

    public static void registerCommand(Class<?> clazz, Plugin plugin) {
        if (clazz.getAnnotation(CommandInfo.class) == null) throw new IllegalArgumentException("You need to register a class with the @CommandInfo annotation");
        String name = null;
        PluginCommand command = BlossomCommand.from(plugin, name).createCommand();
        Bukkit.getCommandMap().register(name, command);
    }

    private static CommandMap getCommandMap() {
        try {
            Bukkit.getCommandMap();
            if (Bukkit.getPluginManager() instanceof SimplePluginManager) {
                Field field = SimplePluginManager.class.getDeclaredField("commandMap");
                field.setAccessible(true);
                return (CommandMap) field.get(Bukkit.getPluginManager());
            }
        } catch (ReflectiveOperationException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public enum CommandUser {
        CONSOLE, PLAYER, BOTH;
    }
}
