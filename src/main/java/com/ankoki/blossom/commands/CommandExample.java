package com.ankoki.blossom.commands;

import com.ankoki.blossom.Blossom;
import com.ankoki.blossom.commands.annotations.Argument;
import com.ankoki.blossom.commands.annotations.BaseCommand;
import com.ankoki.blossom.commands.annotations.CommandInfo;
import com.ankoki.blossom.commands.CommandManager.CommandUser;
import com.ankoki.blossom.commands.annotations.SubArgument;
import org.bukkit.entity.Player;

// The end goal of this system.
@CommandInfo(name = "Blossom",
            aliases = "bs",
            description = "Handles the blossom commands.")
public class CommandExample {

    static {
        CommandManager.registerCommand(CommandExample.class, Blossom.getInstance());
    }

    @BaseCommand(sender = CommandUser.PLAYER)
    private void myMainCommand(Player sender, String alias) {
        sender.sendMessage("You used the /blossom command with the alias: " + alias);
    }

    @Argument(name = "player-argument",
            parameter = Player.class,
            permisson = "blossom.admin",
            sender = CommandUser.PLAYER)
    private void onArgumentOne(Player sender, Player argument) {
        sender.sendMessage("Your argument for using /blossom ;player-argument; was " + argument.getName());
    }

    @SubArgument(name = "nickname-argument",
                lastArgument = "player-argument",
                parameter = String.class,
                sender = CommandUser.PLAYER)
    private void onArgumentOnesSubArgLol(Player player, Player argumentOne, String argumentTwo) {
        argumentOne.setCustomName(argumentTwo);
        player.sendMessage("You have changed the nickname of ;arg-1; to ;arg-2;!");
    }
}
