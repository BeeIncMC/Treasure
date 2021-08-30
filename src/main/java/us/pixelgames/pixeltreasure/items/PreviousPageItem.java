package us.pixelgames.pixeltreasure.items;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import us.pixelgames.pixeltreasure.inventory.CratesInventory;
import us.pixelgames.pixeltreasure.util.MessageUtil;

public class PreviousPageItem implements IClickable {
    private final int itemSlot;
    private final int currentPage;
    private final int previousPage;

    public PreviousPageItem(int itemSlot, int currentPage, int previousPage) {
        this.itemSlot = itemSlot;
        this.currentPage = currentPage;
        this.previousPage = previousPage;
    }

    /**
     * Create the display item for any GUIs and armorstand displays
     */
    public ItemStack getDisplayItem() {
        final ItemStack itemStack = new ItemStack(Material.ARROW, 1);
        final ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(MessageUtil.colour("&aPrevious page"));
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    public int getItemSlot() {
        return itemSlot;
    }

    public Inventory getPreviousPage(CratesInventory inventory) {
        return inventory.getInventory();
    }

    public void clickEvent(InventoryClickEvent playerInteractEvent) {
        playerInteractEvent.getWhoClicked().openInventory(getPreviousPage((CratesInventory) playerInteractEvent.getClickedInventory()));
    }
}