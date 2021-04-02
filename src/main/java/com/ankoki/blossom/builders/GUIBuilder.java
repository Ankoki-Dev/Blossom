package com.ankoki.blossom.builders;

import com.ankoki.blossom.utils.Utils;
import com.ankoki.blossom.utils.events.ClickEvent;
import com.ankoki.blossom.utils.events.CloseEvent;
import com.ankoki.blossom.utils.events.DragEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GUIBuilder {
    private final Inventory mainInventory;
    public static Map<Inventory, Map<Integer, ClickEvent>> allClickEvents = new HashMap<>();
    public static Map<Inventory, DragEvent> allDragEvents = new HashMap<>();
    public static Map<Inventory, CloseEvent> allCloseEvents = new HashMap<>();

    public GUIBuilder(int rows, String name) {
        mainInventory = Bukkit.createInventory(null, rows * 9, Utils.coloured(name));
    }

    public GUIBuilder(String name, InventoryType type) {
        mainInventory = Bukkit.createInventory(null, type, Utils.coloured(name));
    }

    public GUIBuilder setItem(int slot, ItemStack item) {
        mainInventory.setItem(slot, item);
        return this;
    }

    public GUIBuilder addItem(ItemStack item) {
        mainInventory.addItem(item);
        return this;
    }

    public GUIBuilder setBorderSlots(ItemStack item) {
        for (int slot : getBorderSlots(this.mainInventory)) {
            mainInventory.setItem(slot, item);
        }
        return this;
    }

    public GUIBuilder setClickEvent(ClickEvent event, int... slots) {
        Map<Integer, ClickEvent> map = allClickEvents.get(mainInventory) == null ? new HashMap<>() : allClickEvents.get(mainInventory);
        for (int i : slots) {
            map.put(i, event);
        }
        allClickEvents.put(mainInventory, map);
        return this;
    }

    public GUIBuilder setDragEvent(DragEvent event) {
        allDragEvents.put(mainInventory, event);
        return this;
    }

    public GUIBuilder setCloseEvent(CloseEvent event) {
        allCloseEvents.put(mainInventory, event);
        return this;
    }

    private Integer[] getBorderSlots(Inventory inv) {
        List<Integer> slotsList = new ArrayList<>();
        InventoryType type = inv.getType();
        int rows = 0;
        if (type == InventoryType.CHEST ||
                type == InventoryType.ENDER_CHEST ||
                type == InventoryType.SHULKER_BOX ||
                type == InventoryType.BARREL) rows = inv.getSize() / 9;
        if (type == InventoryType.DISPENSER ||
                type == InventoryType.DROPPER) rows = 3;
        if (type == InventoryType.HOPPER) return new Integer[]{0, 4};
        if (rows == 0) return new Integer[]{};
        int slots = inv.getSize();
        int slotsPerRow = slots / rows;
        for (int i = 1; i <= rows; i++) {
            slotsList.add(slotsPerRow * (i - 1));
            slotsList.add((slotsPerRow * (i - 1)) + (slotsPerRow - 1));
        }
        for (int i = 1; i <= (slotsPerRow - 2); i++) {
            slotsList.add(i);
        }
        for (int i = slots - 2; i <= ((rows - 1) * slotsPerRow) + 1; i++) {
            slotsList.add(i);
        }
        for (int i = (inv.getSize() - 1); i >= (inv.getSize() - slotsPerRow); i--) {
            slotsList.add(i);
        }
        return slotsList.toArray(new Integer[0]);
    }

    public Inventory build() {
        return mainInventory;
    }
}