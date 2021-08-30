package us.pixelgames.pixeltreasure.items;

public enum EnumRarity {
    COMMON(0.65f),
    RARE(0.35f),
    LEGENDARY(0.15f);

    private final float rarity;

    EnumRarity(float rarity) {
        this.rarity = rarity;
    }

    public float getRarity() {
        return rarity;
    }

    public String toString() {
        switch (this) {
            case RARE:
                return "RARE";
            case LEGENDARY:
                return "LEGENDARY";
            default:
                return "COMMON";
        }
    }
}