package com.ankoki.blossom.utils;

import com.ankoki.blossom.Blossom;

public enum Version {

    UNKNOWN(false),
    v1_7_R1(true),
    v1_7_R2(true),
    v1_7_R3(true),
    v1_7_R4(true),
    v1_8_R1(true),
    v1_9_R1(true),
    v1_10_R1(true),
    v1_11_R1(true),
    v1_12_R1(true),
    v1_12_R2(true),
    v1_13_R1(false),
    v1_14_R1(false),
    v1_15_R1(false),
    v1_16_R1(false),
    v1_16_R2(false),
    v1_16_R3(false),
    v1_16_R4(false),
    v1_17_R1(false);


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

    private final boolean legacy;
    Version(boolean legacy) {
        this.legacy = legacy;
    }

    public boolean isLegacy() {
        return legacy;
    }

    public boolean isNewerOrSame() {
        return this.ordinal() >= CURRENT_VERSION.ordinal();
    }
}
