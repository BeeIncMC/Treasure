package us.pixelgames.pixeltreasure.items;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import us.pixelgames.pixeltreasure.inventory.CratesInventory;
import us.pixelgames.pixeltreasure.util.InventoryUtil;
import us.pixelgames.pixeltreasure.util.MessageUtil;

import java.util.Arrays;
import java.util.List;

public class NextPageItem extends ItemStack {
    private final int itemSlot;
    private final int currentPage;
    private final int nextPage;

    public NextPageItem(int itemSlot, int currentPage, int nextPage) {
        this.itemSlot = itemSlot;
        this.currentPage = currentPage;
        this.nextPage = nextPage;
    }

    /**
     * Create the display item for any GUIs and armorstand displays
     */
    public ItemStack getDisplayItem() {
        final ItemStack itemStack = new ItemStack(Material.ARROW, 1);
        final ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(MessageUtil.colour("&aNext page"));
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    public int getItemSlot() {
        return itemSlot;
    }

    public Inventory getNextPage(CratesInventory inventory) {
        return inventory.getInventory();
    }
}