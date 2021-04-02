package com.ankoki.blossom;

import com.ankoki.blossom.listeners.InventoryHandler;
import com.ankoki.blossom.utils.Utils;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Blossom is a utility library to make life a little less painful, and
 * to stop/combat repetitive code.                                                   b o n k
 */
public class Blossom extends JavaPlugin {

    @Getter
    private static JavaPlugin instance;

    @Override
    public void onEnable() {
        long start = System.currentTimeMillis();
        instance = this;
        Utils.registerListeners(this, new InventoryHandler());
        this.getLogger().info(String.format("Blossom v%s was enabled in %sms",
                this.getDescription().getVersion(),
                System.currentTimeMillis() - start));
    }

    @Override
    public void onDisable() {
        instance = null;
    }
}