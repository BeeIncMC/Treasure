package us.pixelgames.pixeltreasure.items;

public class ChestAnimation {
    private final EnumAnimation chestAnimation;
    private final String headUUID;
    private final String dataValue;

    public ChestAnimation(String chestAnimation, String headUUID, String dataValue) {
        this.headUUID = headUUID;
        this.dataValue = dataValue;
        this.chestAnimation = EnumAnimation.valueOf(chestAnimation);
    }
}