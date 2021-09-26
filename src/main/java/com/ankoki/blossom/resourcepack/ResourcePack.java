package com.ankoki.blossom.resourcepack;

import com.ankoki.blossom.Blossom;
import lombok.SneakyThrows;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public final class ResourcePack {

    private static final boolean PAPER_METHOD_EXISTS;

    static {
        PAPER_METHOD_EXISTS = Blossom.methodExists(Player.class, "setResourcePack", String.class, String.class);
    }

    /**
     * Sends a resource pack to the player.
     *
     * @param address the address of the resource pack.
     * @param hash the hash to send the resource pack with.
     * @param players the players to send it too.
     */
    public static void sendResourcePack(String address, String hash, Player... players) {
        for (Player player : players) {
            if (hash == null) {
                player.setResourcePack(address);
            } else {
                if (PAPER_METHOD_EXISTS)
                    player.setResourcePack(address, hash);
                else
                    player.setResourcePack(address, hexStringToByteArray(hash));
            }
        }
    }

    /**
     * Sends a resource pack to the player.
     *
     * @param address the address of the resource pack.
     * @param players the players to send it too.
     */
    public static void sendResourcePack(String address, Player... players) {
        sendResourcePack(address, null, players);
    }

    /**
     * INTERNAL USE ONLY
     * <p>
     * Converts a string to byte array.
     *
     * @param text the text to convert.
     * @return the converted array.
     */
    private static byte[] hexStringToByteArray(String text) {
        int len = text.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(text.charAt(i), 16) << 4)
                    + Character.digit(text.charAt(i + 1), 16));
        }
        return data;
    }

    public static final class Unicodes {
        private static final Map<String, String> UNICODE_ASSIGNMENT = new HashMap<>();

        /**
         * Gets the no-split character if set.
         *
         * @return the no-split character if set.
         * @throws IllegalArgumentException thrown if setNoSplit hasn't been called previously.
         */
        @SneakyThrows
        public static String getNoSplit() {
            if (UNICODE_ASSIGNMENT.containsKey("no-split")) return UNICODE_ASSIGNMENT.get("no-split");
            throw new IllegalAccessException("You need to set the \"no-split\" character before retrieving!");
        }

        /**
         * Sets the no-split character, used for aligning {@link com.ankoki.blossom.gui.GUI} overlays.
         * <p>
         * Most servers use ᆀ as this character.
         *
         * @param noSplit the new no-split character.
         */
        public static void setNoSplit(String noSplit) {
            UNICODE_ASSIGNMENT.put("no-split", noSplit);
        }

        /**
         * Assigns a unicode a name and a key.
         *
         * @param key   the unicodes key, used to retrieve.
         * @param value the unicode.
         */
        public static void set(String key, String value) {
            UNICODE_ASSIGNMENT.put(key, value);
        }

        /**
         * Gets the assigned unicode to a name.
         *
         * @param key the key to retrieve.
         * @return the object linked to the key. Can be null.
         */
        public static String get(String key) {
            return UNICODE_ASSIGNMENT.get(key);
        }
    }
}