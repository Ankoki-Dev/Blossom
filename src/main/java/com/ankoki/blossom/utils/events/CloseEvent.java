package com.ankoki.blossom.utils.events;

import org.bukkit.event.inventory.InventoryCloseEvent;

public interface CloseEvent {
    void onClose(InventoryCloseEvent event);
}
