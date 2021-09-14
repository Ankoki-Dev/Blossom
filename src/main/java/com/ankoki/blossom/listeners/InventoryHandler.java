package com.ankoki.blossom.listeners;

import com.ankoki.blossom.builders.GUIBuilder;
import com.ankoki.blossom.utils.events.gui.ClickEvent;
import com.ankoki.blossom.utils.events.gui.CloseEvent;
import com.ankoki.blossom.utils.events.gui.DragEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;

import java.util.Map;

public class InventoryHandler implements Listener {

    @EventHandler
    private void onInventoryClick(InventoryClickEvent e) {
        Inventory inv = e.getInventory();
        int slot = e.getSlot();
        for (Map.Entry<Inventory, Map<Integer, ClickEvent>> entry : GUIBuilder.ALL_CLICK_EVENTS.entrySet()) {
            if (inv == entry.getKey()) {
                for (Map.Entry<Integer, ClickEvent> entry1 : entry.getValue().entrySet()) {
                    if (slot == entry1.getKey()) {
                        entry1.getValue().onClick(e);
                    }
                }
            }
        }
    }

    @EventHandler
    private void onInventoryDrag(InventoryDragEvent e) {
        Inventory inv = e.getInventory();
        for (Map.Entry<Inventory, DragEvent> entry : GUIBuilder.ALL_DRAG_EVENTS.entrySet()) {
            if (inv == entry.getKey()) {
                entry.getValue().onDrag(e);
            }
        }
    }

    @EventHandler
    private void onInventoryClose(InventoryCloseEvent e) {
        Inventory inv = e.getInventory();
        for (Map.Entry<Inventory, CloseEvent> entry : GUIBuilder.ALL_CLOSE_EVENTS.entrySet()) {
            if (inv == entry.getKey()) {
                entry.getValue().onClose(e);
            }
        }
    }
}
