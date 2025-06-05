package me.eject;

public class MaterialConfig {
    private final String material;
    private final int slot;
    private final String displayName;

    public MaterialConfig(String material, int slot, String displayName) {
        this.material    = material;
        this.slot        = slot;
        this.displayName = displayName;
    }

    public String getMaterial() {
        return material;
    }

    public int getSlot() {
        return slot;
    }

    public String getDisplayName() {
        return displayName;
    }
}
