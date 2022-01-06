package com.ankoki.blossom.commands;

import com.ankoki.blossom.commands.annotations.BaseCommand;
import net.kyori.adventure.text.Component;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import com.ankoki.blossom.commands.CommandManager.CommandUser;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public abstract class BlossomCommand {

    private final Class<?> clazz;
    private final Plugin plugin;
    private final String name;
    private String description, permission, permissionMessage;
    private List<String> aliases;
    private CommandUser user;

    public BlossomCommand(Class<?> clazz, Plugin plugin, String name, String description, String permission, String permissionMessage, CommandUser user, String... aliases) {
        this.clazz = clazz;
        this.plugin = plugin;
        this.name = name;
        this.description = description;
        this.permission = permission;
        this.permissionMessage = permissionMessage;
        this.user = user;
        this.aliases = Arrays.asList(aliases);
    }

    public static BlossomCommand from(Class<?> clazz, Plugin plugin, String name) {
        return CommandImpl.fromInternal(clazz, plugin, name);
    }

    public BlossomCommand setDescription(String description) {
        this.description = description;
        return this;
    }

    public BlossomCommand setPermission(String permission) {
        this.permission = permission;
        return this;
    }

    public BlossomCommand setPermissionMessage(String message) {
        permissionMessage = message;
        return this;
    }

    public BlossomCommand setUser(CommandUser user) {
        this.user = user;
        return this;
    }

    public BlossomCommand setAliases(String... aliases) {
        this.aliases = Arrays.asList(aliases);
        return this;
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
            command.setExecutor((sender, cmd, alias, args) -> {
                if (args.length == 0) {
                    BaseCommand base;
                    for (Method method : clazz.getDeclaredMethods()) {
                        method.setAccessible(true);
                        base = method.getAnnotation(BaseCommand.class);
                        if (base == null) continue;
                        if (base.sender() == CommandUser.BOTH || base.sender() == CommandUser.CONSOLE) {
                            try {
                                method.invoke(CommandManager.getInstance(clazz), sender);
                            } catch (ReflectiveOperationException ex) {
                                try {
                                    method.invoke(CommandManager.getInstance(clazz), sender, alias);
                                } catch (ReflectiveOperationException exc) {
                                    exc.printStackTrace();
                                }
                            }
                        } else if (sender instanceof Player) {
                            try {
                                method.invoke(CommandManager.getInstance(clazz), sender);
                            } catch (ReflectiveOperationException ex) {
                                try {
                                    method.invoke(CommandManager.getInstance(clazz), sender, alias);
                                } catch (ReflectiveOperationException exc) {
                                    exc.printStackTrace();
                                }
                            }
                        }
                    }
                }
                return true;
            });
            return command;
        } catch (ReflectiveOperationException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPermission() {
        return permission;
    }

    public String getPermissionMessage() {
        return permissionMessage;
    }

    public List<String> getAliases() {
        return aliases;
    }

    public CommandUser getUser() {
        return user;
    }
}
