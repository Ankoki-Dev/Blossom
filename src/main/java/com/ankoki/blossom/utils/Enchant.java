package com.ankoki.blossom.utils;

import org.bukkit.enchantments.Enchantment;

public enum Enchant {
    PROTECTION(Enchantment.PROTECTION_ENVIRONMENTAL),
    FIRE_PROTECTION(Enchantment.PROTECTION_FIRE),
    FEATHER_FALLING(Enchantment.PROTECTION_FALL),
    BLAST_PROTECTION(Enchantment.PROTECTION_EXPLOSIONS),
    PROJECTILE_PROTECTION(Enchantment.PROTECTION_PROJECTILE),
    RESPIRATION(Enchantment.OXYGEN),
    AQUA_AFFINITY(Enchantment.WATER_WORKER),
    THORNS(Enchantment.THORNS),
    FROST_WALKER(Enchantment.FROST_WALKER),
    BINDING_CURSE(Enchantment.BINDING_CURSE),
    SHARPNESS(Enchantment.DAMAGE_ALL),
    SMITE(Enchantment.DAMAGE_UNDEAD),
    BANE_OF_ARTHROPODS(Enchantment.DAMAGE_ARTHROPODS),
    KNOCKBACK(Enchantment.KNOCKBACK),
    FIRE_ASPECT(Enchantment.FIRE_ASPECT),
    LOOTING(Enchantment.LOOT_BONUS_MOBS),
    SWEEPING(Enchantment.SWEEPING_EDGE),
    EFFICIENCY(Enchantment.DIG_SPEED),
    SILK_TOUCH(Enchantment.SILK_TOUCH),
    UNBREAKING(Enchantment.DURABILITY),
    FORTUNE(Enchantment.LOOT_BONUS_BLOCKS),
    POWER(Enchantment.ARROW_DAMAGE),
    PUNCH(Enchantment.ARROW_KNOCKBACK),
    FLAME(Enchantment.ARROW_FIRE),
    INFINITY(Enchantment.ARROW_INFINITE),
    LUCK_OF_THE_SEA(Enchantment.LUCK),
    LURE(Enchantment.LURE),
    LOYALTY(Enchantment.LOYALTY),
    IMPALING(Enchantment.IMPALING),
    RIPTIDE(Enchantment.RIPTIDE),
    CHANNELING(Enchantment.CHANNELING),
    MULTISHOT(Enchantment.MULTISHOT),
    QUICK_CHARGE(Enchantment.QUICK_CHARGE),
    PIERCING(Enchantment.PIERCING),
    MENDING(Enchantment.MENDING),
    VANISHING_CURSE(Enchantment.VANISHING_CURSE),
    SOUL_SPEED(Enchantment.SOUL_SPEED);

    private final Enchantment bukkitEnchantment;

    Enchant(Enchantment bukkitEnchantment) {
        this.bukkitEnchantment = bukkitEnchantment;
    }

    public Enchantment getBukkitEnchantment() {
        return this.bukkitEnchantment;
    }
}
