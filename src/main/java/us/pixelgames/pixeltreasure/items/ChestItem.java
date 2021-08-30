package us.pixelgames.pixeltreasure.items;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import us.pixelgames.pixeltreasure.PixelTreasure;
import us.pixelgames.pixeltreasure.tasks.BuildPlatformTask;
import us.pixelgames.pixeltreasure.tasks.OpenChestTask;
import us.pixelgames.pixeltreasure.util.BlockUtil;
import us.pixelgames.pixeltreasure.util.MessageUtil;

import java.util.ArrayList;
import java.util.List;

public class ChestItem {
    private final String displayName;
    private final int itemSlot;
    private final MaterialData materialData;
    private final boolean isEnchanted;
    private final List<String> displayLore;
    private final String chestName;
    private boolean hasBeenPlaced = false;

    public ChestItem(String displayName, int itemSlot, MaterialData materialData, boolean isEnchanted, String chestName, List<String> displayLore) {
        this.displayName = displayName;
        this.itemSlot = itemSlot;
        this.materialData = materialData;
        this.isEnchanted = isEnchanted;
        this.displayLore = displayLore;
        this.chestName = chestName;
    }

    public ChestItem(String displayName, int itemSlot, Material material, byte data, boolean isEnchanted, String chestName, List<String> displayLore) {
        this(displayName, itemSlot, new MaterialData(material, data), isEnchanted, chestName, displayLore);
    }

    /**
     * Create the display item for any GUIs and armorstand displays
     */
    public ItemStack getChestItem() {
        final ItemStack itemStack = new ItemStack(materialData.getItemType(), 1);
        final ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(MessageUtil.colour(displayName));
        if(isEnchanted) {
            itemStack.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 1);
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        List<String> strings = new ArrayList<>();
        for(String rawString:displayLore) {
            strings.add(MessageUtil.colour(rawString));
        }
        itemMeta.setLore(strings);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public void clickItem(PixelTreasure instance, Player whoClicked) {
        Chest chest = BlockUtil.loadChestFromConfig(instance, chestName);

        if(chest != null) {
            if(chest.getBuildAnimation() != null) {
                Bukkit.getScheduler().runTask(instance, new OpenChestTask(instance, chest, whoClicked, BlockUtil.getChestNearPlayer(instance, whoClicked)));
            }
        }
    }

    public int getItemSlot() {
        return itemSlot;
    }
}