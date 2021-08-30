package us.pixelgames.pixeltreasure.items;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import us.pixelgames.pixeltreasure.util.MessageUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Reward {
    private final String displayName;
    private final MaterialData materialData;
    private final boolean isEnchanted;
    private final boolean isUnique;
    private final String command;
    private final String permissionNode;
    private final EnumRarity enumRarity;

    public Reward(String displayName, MaterialData materialData, boolean isEnchanted, boolean isUnique, String command, String permissionNode, EnumRarity enumRarity) {
        this.displayName = displayName;
        this.materialData = materialData;
        this.isEnchanted = isEnchanted;
        this.isUnique = isUnique;
        this.command = command;
        this.permissionNode = permissionNode;
        this.enumRarity = enumRarity;
    }

    /**
     * Create the display item for any GUIs and armorstand displays
     */
    public ItemStack getRewardItem() {
        final ItemStack itemStack = new ItemStack(materialData.getItemType(), 1);
        final ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(displayName);
        if(isEnchanted) {
            itemStack.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 1);
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }

        itemMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    public EnumRarity getEnumRarity() {
        return enumRarity;
    }

    @Override
    public String toString() {
        return displayName;
    }
}