package com.ankoki.blossom.builders;


import com.ankoki.blossom.utils.Enchant;
import com.ankoki.blossom.utils.Utils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemBuilder {
    private final ItemStack item;

    public ItemBuilder(ItemStack item, int stackSize) {
        this.item = item;

        item.setAmount(stackSize);
    }

    public ItemBuilder(ItemStack item) {
        this.item = item;
    }

    public ItemBuilder(Material material) {
        if (isIllegalType(material)) throw new IllegalArgumentException("Material cannot be air or water!");
        this.item = new ItemStack(material);
    }

    public ItemBuilder(Material material, int count) {
        if (isIllegalType(material)) throw new IllegalArgumentException("Material cannot be air or water!");
        this.item = new ItemStack(material, count);
    }

    public ItemBuilder setLore(LoreBuilder loreBuilder) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return null;
        meta.setLore(loreBuilder.build());
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setDisplayName(String name) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return this;
        meta.setDisplayName(Utils.coloured(name));
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder addEnchant(Enchant enchant, int level) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return this;
        meta.addEnchant(enchant.getBukkitEnchantment(), level, false);
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder addEnchant(Enchant enchant) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return this;
        meta.addEnchant(enchant.getBukkitEnchantment(), 1, false);
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder addEnchants(Enchant... enchants) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return this;
        for (Enchant enchant : enchants) {
            meta.addEnchant(enchant.getBukkitEnchantment(), 1, false);
        }
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder addEnchants(int level, Enchant... enchants) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return this;
        for (Enchant enchant : enchants) {
            meta.addEnchant(enchant.getBukkitEnchantment(), level, false);
        }
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setType(Material material) {
        if (isIllegalType(material)) throw new IllegalArgumentException("Material cannot be air or water!");
        item.setType(material);
        return this;
    }

    public ItemBuilder addFlags(ItemFlag... flags) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return this;
        meta.addItemFlags(flags);
        return this;
    }

    public ItemBuilder setGlowing() {
        if (item.getType() == Material.BOW || item.getType() == Material.TRIDENT) {
            this.addEnchant(Enchant.EFFICIENCY);
        } else {
            this.addEnchant(Enchant.INFINITY);
        }
        this.addFlags(ItemFlag.HIDE_ENCHANTS);
        return this;
    }

    public ItemBuilder setItemMeta(ItemMeta meta) {
        item.setItemMeta(meta);
        return this;
    }

    public String getName() {
        return item.getType().toString().toLowerCase().replace("_", " ");
    }

    public ItemStack build() {
        return item;
    }

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
