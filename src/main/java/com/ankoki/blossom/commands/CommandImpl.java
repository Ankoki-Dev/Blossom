package com.ankoki.blossom.commands;

import org.bukkit.plugin.Plugin;

public class CommandImpl extends BlossomCommand {

    public CommandImpl(Class<?> clazz, Plugin plugin, String name) {
        super(clazz, plugin, name, null, null, null, null);
    }

    static CommandImpl fromInternal(Class<?> clazz, Plugin plugin, String name) {
        return new CommandImpl(clazz, plugin, name);
    }
}
