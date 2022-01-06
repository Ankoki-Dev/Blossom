package com.ankoki.blossom.utils;

import com.ankoki.blossom.Blossom;

public enum Version {

    v1_7_R1(),
    v1_7_R2(),
    v1_7_R3(),
    v1_7_R4(),
    v1_8_R1(),
    v1_9_R1(),
    v1_10_R1(),
    v1_11_R1(),
    v1_12_R1(),
    v1_12_R2(),
    v1_13_R1(),
    v1_14_R1(),
    v1_15_R1(),
    v1_16_R1(),
    v1_16_R2(),
    v1_16_R3(),
    v1_16_R4(),
    v1_17_R1(),
    v1_18_R1(),
    UNKNOWN();


    // Can't be final.
    public static Version CURRENT_VERSION;

    static {
        String packageName = Blossom.getInstance().getServer().getClass().getPackage().getName();
        String version = packageName.substring(packageName.lastIndexOf('.') + 1);
        try {
            CURRENT_VERSION = Version.valueOf(version);
        } catch (IllegalArgumentException ex) {
            CURRENT_VERSION = UNKNOWN;
            System.err.println("You are using an unknown version (" + version + "). You could be using a version before 1.7, or a newer version I haven't supported.");
        }
    }

    private final boolean legacy = this.ordinal() < 10;

    public boolean isLegacy() {
        return legacy;
    }

    public boolean isNewerOrSame() {
        return this.ordinal() >= CURRENT_VERSION.ordinal();
    }
}
