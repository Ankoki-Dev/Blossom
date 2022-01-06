package com.ankoki.blossom;

import com.ankoki.blossom.listeners.InventoryHandler;
import com.ankoki.blossom.utils.Utils;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Method;

/**
 * Blossom is a utility library to make life a little less painful, and
 * to stop/combat repetitive code in spigot plugins.
 * <p>
 *
 * @author Jay | Ankoki
 */
public class Blossom extends JavaPlugin {

    private static JavaPlugin instance;

    @Override
    public void onEnable() {
        long start = System.currentTimeMillis();
        instance = this;
        Utils.registerListeners(this, new InventoryHandler());
        this.getLogger().info(String.format("Blossom v%s has been enabled (%sms)",
                this.getDescription().getVersion(),
                System.currentTimeMillis() - start));
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    /**
     * Looks to see if a method exists in a class.
     *
     * @param clazz the class.
     * @param method the method name.
     * @param parameters the parameters of the method.
     * @return if said method exists.
     */
    public static boolean methodExists(Class<?> clazz, String method, Class<?>... parameters) {
        try {
            clazz.getDeclaredMethod(method, parameters);
            return true;
        } catch (NoSuchMethodException ex) {
            return false;
        }
    }

    /**
     * Looks to see if a method exists in a class.
     *
     * @param clazz the class.
     * @param returnType the return type of the method.
     * @param method the method name.
     * @param parameters the parameters of the method.
     * @return if said method exists.
     */
    public static boolean methodExists(Class<?> clazz, Class<?> returnType, String method, Class<?>... parameters) {
        try {
            Method m = clazz.getDeclaredMethod(method, parameters);
            return m.getReturnType() == returnType;
        } catch (NoSuchMethodException ex) {
            return false;
        }
    }

    public static JavaPlugin getInstance() {
        return instance;
    }
}