package us.pixelgames.pixeltreasure.items;

public enum EnumAnimation {
    ENTITYFALL,
    HEADFALL,
    HEADRISE;

    public String toString() {
        switch (this) {
            case ENTITYFALL:
                return "ENTITYFALL";
            case HEADFALL:
                return "HEADFALL";
            default:
                return "HEADRISE";
        }
    }
}