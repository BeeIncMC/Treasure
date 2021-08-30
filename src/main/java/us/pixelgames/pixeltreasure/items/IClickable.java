package us.pixelgames.pixeltreasure.items;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public interface IClickable {
    void clickEvent(InventoryClickEvent playerInteractEvent);
}