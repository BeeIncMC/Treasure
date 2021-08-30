package us.pixelgames.pixeltreasure.tasks;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.material.MaterialData;
import org.bukkit.material.Stairs;
import org.bukkit.scheduler.BukkitRunnable;
import us.pixelgames.pixeltreasure.PixelTreasure;
import us.pixelgames.pixeltreasure.util.BlockUtil;
import xyz.xenondevs.particle.ParticleEffect;
import xyz.xenondevs.particle.data.texture.BlockTexture;

import java.util.Collection;
import java.util.HashSet;

public class PlaceBlockTask implements Runnable {
    private final MaterialData materialData;
    private final Location blockLocation;

    public PlaceBlockTask(MaterialData materialData, Location blockLocation) {
        this.materialData = materialData;
        this.blockLocation = blockLocation;
    }

    @Override
    public void run() {
        Block block = blockLocation.getBlock();
        if(materialData instanceof Stairs || getStairsMaterials().contains(materialData.getItemType())) {
            block.setData(BlockUtil.fixedData(this.materialData.getData()));
            block.setType(this.materialData.getItemType());
            block.setData(BlockUtil.fixedData(this.materialData.getData()));
        } else {
            block.setData(this.materialData.getData());
            block.setType(this.materialData.getItemType());
            block.setData(this.materialData.getData());
        }
        if(this.materialData.getItemType() != Material.AIR) {
            ParticleEffect.BLOCK_CRACK.display(blockLocation, 0.5f, 0.25f, 0.5f, 0.25f, 15, new BlockTexture(this.materialData.getItemType()));
        }
    }

    private Collection<Material> getStairsMaterials() {
        Collection<Material> materials = new HashSet<>();
        materials.add(Material.ACACIA_STAIRS);
        materials.add(Material.SANDSTONE_STAIRS);
        materials.add(Material.SMOOTH_STAIRS);
        materials.add(Material.BRICK_STAIRS);
        materials.add(Material.COBBLESTONE_STAIRS);
        materials.add(Material.QUARTZ_STAIRS);
        materials.add(Material.NETHER_BRICK_STAIRS);
        materials.add(Material.DARK_OAK_STAIRS);
        materials.add(Material.BIRCH_WOOD_STAIRS);
        materials.add(Material.JUNGLE_WOOD_STAIRS);
        materials.add(Material.WOOD_STAIRS);
        materials.add(Material.SPRUCE_WOOD_STAIRS);
        materials.add(Material.RED_SANDSTONE_STAIRS);

        return materials;
    }
}