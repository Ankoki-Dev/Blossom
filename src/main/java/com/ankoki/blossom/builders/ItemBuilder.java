package com.ankoki.blossom.builders;


import com.ankoki.blossom.utils.Chat;
import com.ankoki.blossom.utils.Enchant;
import org.apache.commons.lang.Validate;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class ItemBuilder {

    /**
     * Creates a new ItemBuilder.
     *
     * @param item base item to use.
     * @param amount amount of the base item.
     * @return newly created ItemBuilder.
     */
    public static ItemBuilder createItem(ItemStack item, int amount) {
        return new ItemBuilder(item, amount);
    }

    /**
     * Creates a new ItemBuilder.
     *
     * @param item base item to use.
     * @return newly created ItemBuilder.
     */
    public static ItemBuilder createItem(ItemStack item) {
        return new ItemBuilder(item);
    }

    /**
     * Creates a new ItemBuilder.
     *
     * @param material base material to use.
     * @return newly created ItemBuilder.
     */
    public static ItemBuilder createItem(Material material) {
        return new ItemBuilder(material);
    }

    /**
     * Creates a new ItemBuilder.
     *
     * @param material base material to use.
     * @param amount amount of the base material.
     * @return newly created ItemBuilder.
     */
    public static ItemBuilder createItem(Material material, int amount) {
        return new ItemBuilder(material, amount);
    }

    /**
     * INTERNAL USE ONLY
     * <p>
     * Creates a new ItemBuilder.
     *
     * @param item the item its based around.
     * @param amount the amount of the given item.
     */
    private ItemBuilder(ItemStack item, int amount) {
        Validate.isTrue(isIllegalType(item.getType()), "Material cannot be air or water!");
        this.item = item;
        item.setAmount(amount);
    }

    /**
     * INTERNAL USE ONLY
     * <p>
     * Creates a new ItemBuilder.
     *
     * @param item the item its based around.
     */
    private ItemBuilder(ItemStack item) {
        this(item, 1);
    }

    /**
     * INTERNAL USE ONLY
     * <p>
     * Creates a new ItemBuilder.
     *
     * @param material the material its based around.
     */
    private ItemBuilder(Material material) {
        this(new ItemStack(material, 1));
    }

    /**
     * INTERNAL USE ONLY
     * <p>
     * Creates a new ItemBuilder.
     *
     * @param material the material its based around.
     * @param amount the amount of the given material.
     */
    private ItemBuilder(Material material, int amount) {
        this(new ItemStack(material, amount));
    }

    private final ItemStack item;

    /**
     * Sets the lore of the item.
     *
     * @param lore the new lore.
     * @return current ItemBuilder for chaining.
     */
    public ItemBuilder setLore(String... lore) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return this;
        meta.setLore(Arrays.asList(lore));
        item.setItemMeta(meta);
        return this;
    }

    /**
     * Sets the display name of the item.
     *
     * @param name the new name.
     * @return current ItemBuilder for chaining.
     */
    public ItemBuilder setDisplayName(String name) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return this;
        meta.setDisplayName(Chat.format(name));
        item.setItemMeta(meta);
        return this;
    }

    /**
     * Adds an enchant to the item.
     *
     * @param enchant the enchant to add.
     * @param level the level of the enchant.
     * @return current ItemBuilder for chaining.
     */
    public ItemBuilder addEnchant(Enchant enchant, int level) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return this;
        meta.addEnchant(enchant.getBukkitEnchantment(), level, false);
        item.setItemMeta(meta);
        return this;
    }

    /**
     * Adds an enchant to the item.
     *
     * @param enchant the enchant to add.
     * @return current ItemBuilder for chaining.
     */
    public ItemBuilder addEnchant(Enchant enchant) {
        return addEnchant(enchant, 1);
    }

    /**
     * Adds multiple enchants to the item.
     *
     * @param enchants the enchants to add.
     * @return current ItemBuilder for chaining.
     */
    public ItemBuilder addEnchants(Enchant... enchants) {
        for (Enchant enchant : enchants) addEnchant(enchant);
        return this;
    }

    /**
     * Adds multiple enchants to the item.
     *
     * @param level the level of all the enchants.
     * @param enchants the enchants to add.
     * @return current ItemBuilder for chaining.
     */
    public ItemBuilder addEnchants(int level, Enchant... enchants) {
        for (Enchant enchant : enchants) addEnchant(enchant, level);
        return this;
    }

    /**
     * Sets the type of the item.
     *
     * @param material the new type of the item.
     * @return current ItemBuilder for chaining.
     */
    public ItemBuilder setType(Material material) {
        Validate.isTrue(isIllegalType(item.getType()), "Material cannot be air or water!");
        item.setType(material);
        return this;
    }

    /**
     * Adds ItemFlags to the item.
     *
     * @param flags the flags to add.
     * @return current ItemBuilder for chaining.
     */
    public ItemBuilder addFlags(ItemFlag... flags) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return this;
        meta.addItemFlags(flags);
        return this;
    }

    /**
     * Sets the item as glowing.
     *
     * @return current ItemBuilder for chaining.
     */
    public ItemBuilder setGlowing() {
        if (item.getType() == Material.BOW || item.getType() == Material.TRIDENT) {
            this.addEnchant(Enchant.EFFICIENCY);
        } else {
            this.addEnchant(Enchant.INFINITY);
        }
        this.addFlags(ItemFlag.HIDE_ENCHANTS);
        return this;
    }

    /**
     * Sets the ItemMeta of this item.
     *
     * @param meta the new ItemMeta.
     * @return current ItemBuilder for chaining.
     */
    public ItemBuilder setItemMeta(ItemMeta meta) {
        item.setItemMeta(meta);
        return this;
    }

    /**
     * Builds the current item.
     *
     * @return the built item.
     */
    public ItemStack build() {
        return item;
    }

    @Override
    public String toString() {
        return item.getType().toString().toLowerCase().replace("_", " ");
    }

    /**
     * INTERNAL USE ONLY
     * <p>
     * Checks if the material is an illegal type.
     *
     * @param materials materials to check.
     * @return if the materials contain a forbidden type.
     */
    private boolean isIllegalType(Material... materials) {
        for (Material mat : materials) {
            if (mat == Material.AIR) return true;
            if (mat == Material.CAVE_AIR) return true;
            if (mat == Material.VOID_AIR) return true;
            if (mat == Material.WATER) return true;
        }
        return false;
    }
}
