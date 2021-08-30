package us.pixelgames.pixeltreasure.items;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import us.pixelgames.pixeltreasure.PixelTreasure;
import us.pixelgames.pixeltreasure.tasks.PlaceBlockTask;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Loot {
    private final Chest chest;
    private final Random random;
    private final Location location;
    private Reward reward;
    private final Player player;
    private BlockFace blockFace;
    private boolean isOpened;

    public Loot(Chest chest, BlockFace blockFace, Random random, Location location, Player player) {
        this.chest = chest;
        this.random = random;
        this.location = location;
        this.player = player;
        this.blockFace = blockFace;
        this.isOpened = false;
        roll();
    }

    public void roll() {
        float rarity = random.nextFloat();

        if(rarity <= EnumRarity.LEGENDARY.getRarity()) {
            // Roll Legendary Item
            if(chest.getLegendaries() != null) {
                Reward reward = chest.getLegendaries().get(random.nextInt(chest.getLegendaries().size()));

                if(reward != null) {
                    this.reward = reward;
                }
            }
        } else if(rarity <= EnumRarity.RARE.getRarity()) {
            // Roll Rare Item
            Reward reward = chest.getRare().get(random.nextInt(chest.getRare().size()));

            if(reward != null) {
                this.reward = reward;
            }
        } else {
            // Roll Common Item
            Reward reward = chest.getCommons().get(random.nextInt(chest.getCommons().size()));

            if(reward != null) {
                this.reward = reward;
            }
        }
    }

    public void openCrate() {
        if(!isOpened) {
            this.isOpened = true;
        }
    }

    public Location getLocation() {
        return location;
    }

    public Reward getReward() {
        return this.reward;
    }

    public BlockFace getBlockFace() {
        return blockFace;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean isOpened() {
        return isOpened;
    }

}