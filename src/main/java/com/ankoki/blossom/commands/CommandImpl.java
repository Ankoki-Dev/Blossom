package com.ankoki.blossom.commands;

import org.bukkit.plugin.Plugin;

public class CommandImpl extends BlossomCommand {

    public CommandImpl(Plugin plugin, String name) {
        super(plugin, name, null, null, null);
    }

    static CommandImpl fromInternal(Plugin plugin, String name) {
        return new CommandImpl(plugin, name);
    }
}
