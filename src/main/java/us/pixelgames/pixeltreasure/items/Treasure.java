package us.pixelgames.pixeltreasure.items;

import java.util.ArrayList;

public class Treasure {
    private final ArrayList<Loot> treasureLoot;
    private final Chest chest;

    public Treasure(ArrayList<Loot> treasureLoot, Chest chest) {
        this.treasureLoot = treasureLoot;
        this.chest = chest;
    }

    public ArrayList<Loot> getTreasureLoot() {
        return treasureLoot;
    }

    public Chest getChest() {
        return chest;
    }
}