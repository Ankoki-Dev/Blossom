package com.ankoki.blossom;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

/*
 * Blossom is a utilities library that I am going to have a solid work on:D
 * This will contain stuff i constantly reuse and be shaded into most my projects.
 */
public class Blossom extends JavaPlugin {

    @Getter
    private static JavaPlugin instance;

    @Override
    public void onEnable() {
        long start = System.currentTimeMillis();
        instance = this;
        this.getLogger().info(String.format("Blossom v%s was enabled in %sms",
                this.getDescription().getVersion(),
                System.currentTimeMillis() - start));
    }

    @Override
    public void onDisable() {
        instance = null;
    }
}