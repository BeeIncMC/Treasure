package us.pixelgames.pixeltreasure.items;

import java.util.ArrayList;
import java.util.List;

public class Chest {
    private final String name;
    private final String schematicName;
    private final int chestAmount;
    private final List<Reward> commons;
    private final List<Reward> rare;
    private final List<Reward> legendaries;
    private final List<String> buildAnimation;
    private final ChestAnimation chestAnimation;

    public Chest(String name, String schematicName, int chestAmount, List<Reward> rewards, List<String> buildAnimation, ChestAnimation chestAnimation) {
        this.name = name;
        this.schematicName = schematicName;
        this.chestAmount = chestAmount;
        this.buildAnimation = buildAnimation;
        this.chestAnimation = chestAnimation;
        this.commons = new ArrayList<>();
        this.rare = new ArrayList<>();
        this.legendaries = new ArrayList<>();

        for(Reward rewardItem:rewards) {
            if(rewardItem.getEnumRarity().equals(EnumRarity.COMMON)) {
                commons.add(rewardItem);
            }
            if(rewardItem.getEnumRarity().equals(EnumRarity.RARE)) {
                rare.add(rewardItem);
            }
            if(rewardItem.getEnumRarity().equals(EnumRarity.LEGENDARY)) {
                legendaries.add(rewardItem);
            }
        }
    }

    public String getSchematicName() {
        return schematicName;
    }

    public int getChestAmount() {
        return chestAmount;
    }

    public List<String> getBuildAnimation() {
        return buildAnimation;
    }

    public List<Reward> getCommons() {
        return commons;
    }

    public List<Reward> getRare() {
        return rare;
    }

    public List<Reward> getLegendaries() {
        return legendaries;
    }

    public String getName() {
        return name;
    }
}