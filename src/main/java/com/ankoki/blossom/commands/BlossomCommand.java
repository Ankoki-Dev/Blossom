package com.ankoki.blossom.commands;

import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.List;

public abstract class BlossomCommand {

    @Getter
    private final Plugin plugin;
    @Getter
    private final String name;
    @Getter
    @Setter
    private String description, permission, permissionMessage;
    @Getter
    private List<String> aliases;

    public BlossomCommand(Plugin plugin, String name, String description, String permission, String permissionMessage, List<String> aliases) {
        this.plugin = plugin;
        this.name = name;
        this.description = description;
        this.permission = permission;
        this.permissionMessage = permissionMessage;
        this.aliases = aliases;
    }

    public BlossomCommand(Plugin plugin, String name, String description, String permission, String permissionMessage, String... aliases) {
        super(plugin, name, description, permission, permissionMessage, Arrays.asList(aliases));
    }

    public static BlossomCommand from(Plugin plugin, String name) {
        return CommandImpl.fromInternal(plugin, name);
    }

    public void setAliases(String... aliases) {
        this.aliases = Arrays.asList(aliases);
    }

    protected PluginCommand createCommand() {
        try {
            Constructor<PluginCommand> constructor = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
            constructor.setAccessible(true);
            PluginCommand command = constructor.newInstance(name, plugin);
            command.setDescription(description);
            command.setPermission(permission);
            command.permissionMessage(Component.text(permissionMessage));
            command.setAliases(aliases);
            return command;
        } catch (ReflectiveOperationException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
