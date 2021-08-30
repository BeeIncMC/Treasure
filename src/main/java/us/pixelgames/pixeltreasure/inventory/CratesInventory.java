package us.pixelgames.pixeltreasure.inventory;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import us.pixelgames.pixeltreasure.PixelTreasure;
import us.pixelgames.pixeltreasure.items.ChestItem;
import us.pixelgames.pixeltreasure.util.InventoryUtil;
import us.pixelgames.pixeltreasure.util.MessageUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class CratesInventory implements Listener {
    private final PixelTreasure instance;
    private final Inventory inventory;
    private final String displayName;
    private final int itemSlots;
    private final Material fillerItem;
    private final List<ChestItem> chestItems;
    private final Player player;
    private HashMap<Integer, ChestItem> slots = new HashMap<>();

    public CratesInventory(PixelTreasure instance, Player player, String displayName, int itemSlots, Material fillerItem, List<ChestItem> chestItems) {
        this.instance = instance;
        this.inventory = Bukkit.createInventory(player, itemSlots, MessageUtil.colour(displayName));
        this.displayName = displayName;
        this.itemSlots = itemSlots;
        this.fillerItem = fillerItem;
        this.chestItems = chestItems;
        this.player = player;
        for(ChestItem chestItem:chestItems) {
            slots.putIfAbsent(chestItem.getItemSlot(), chestItem);
        }
        init();
    }

    public CratesInventory(PixelTreasure instance, CratesInventory cratesInventory) {
        this(instance, null, cratesInventory.displayName, cratesInventory.itemSlots, cratesInventory.fillerItem, cratesInventory.chestItems);
    }

    public CratesInventory(PixelTreasure instance) {
        this(instance, Objects.requireNonNull(InventoryUtil.loadInventoryFromConfig(instance, null)));
    }

    private void init() {
        for(int i = 0; i < itemSlots; i++) {
            inventory.setItem(i, new ItemStack(fillerItem));
        }
        for(int slot:slots.keySet()) {
            inventory.setItem(slot, slots.get(slot).getChestItem());
        }
    }

    @EventHandler
    public void onInventoryClick(final InventoryClickEvent inventoryClickEvent) {
        if (inventoryClickEvent.getInventory().getName().equals(inventory.getName())) {
            final ItemStack clickedItem = inventoryClickEvent.getCurrentItem();

            if (clickedItem == null || clickedItem.getType() == Material.AIR) return;

            inventoryClickEvent.setCancelled(true);
            for(int slot:slots.keySet()) {
                if(inventoryClickEvent.getSlot() == slot) {
                    slots.get(slot).clickItem(instance, (Player) inventoryClickEvent.getWhoClicked());
                }
            }
        }
    }

    @EventHandler
    public void onInventoryClick(final InventoryDragEvent inventoryDragEvent) {
        if (inventoryDragEvent.getInventory().getName().equals(inventory.getName())) {
            inventoryDragEvent.setCancelled(true);
        }
    }

    public Inventory getInventory() {
        return inventory;
    }
}