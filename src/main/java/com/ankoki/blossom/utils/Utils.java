package com.ankoki.blossom.utils;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.awt.*;
import java.util.Arrays;
import java.util.TreeMap;

public class Utils {
    private static final TreeMap<Integer, String> ROMAN_NUMERALS = new TreeMap<>();

    static {
        ROMAN_NUMERALS.put(1000, "M");
        ROMAN_NUMERALS.put(900, "CM");
        ROMAN_NUMERALS.put(500, "D");
        ROMAN_NUMERALS.put(400, "CD");
        ROMAN_NUMERALS.put(100, "C");
        ROMAN_NUMERALS.put(90, "XC");
        ROMAN_NUMERALS.put(50, "L");
        ROMAN_NUMERALS.put(40, "XL");
        ROMAN_NUMERALS.put(10, "X");
        ROMAN_NUMERALS.put(9, "IX");
        ROMAN_NUMERALS.put(5, "V");
        ROMAN_NUMERALS.put(4, "IV");
        ROMAN_NUMERALS.put(1, "I");
    }

    /**
     * Utility method to translate a number into roman numerals.
     *
     * @param number The number you want in roman numerals.
     * @return The roman numeric value of the given number.
     */
    public static String toRoman(int number) {
        int l = ROMAN_NUMERALS.floorKey(number);
        if (number == l) {
            return ROMAN_NUMERALS.get(number);
        }
        return ROMAN_NUMERALS.get(l) + toRoman(number - l);
    }

    /**
     * Utility method to translate the colours of a string.
     *
     * @param string The string you want translated.
     * @return The coloured string.
     */
    public static String coloured(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    /**
     * Utility method to make a rainbowified string. Supports colour codes. &r goes back to rainbow.
     *
     * @param string The string you want to be rainbow.
     * @param pastel True if you want the rainbow to be pastel, else false.
     * @return The rainbowified string.
     */
    public static String rainbow(String string, boolean pastel) {
        double freq1 = 0.3;
        double freq2 = 0.3;
        double freq3 = 0.3;
        double amp1 = 0;
        double amp2 = 2;
        double amp3 = 4;
        int center = pastel ? 200 : 128;
        int width = pastel ? 55 : 127;
        StringBuilder builder = new StringBuilder();
        string = coloured(string);

        int i = 0;
        String currentColourCode = "";
        for (String s : string.split("")) {
            if (s.equals("§")) {
                i++;
                continue;
            }
            if (i > 0) {
                if (string.charAt(i - 1) == '§') {
                    if (s.equalsIgnoreCase("r")) {
                        currentColourCode = "";
                        i++;
                        continue;
                    } else if ("abcdefklmnor0123456789".contains(s)) {
                        currentColourCode += "§" + s;
                        i++;
                        continue;
                    }
                }
            }
            float red = (float) (Math.sin(freq1 * i + amp1) * width + center);
            float green = (float) (Math.sin(freq2 * i + amp2) * width + center);
            float blue = (float) (Math.sin(freq3 * i + amp3) * width + center);
            if (red > 255 || red < 0) red = 0;
            if (green > 255 || green < 0) green = 0;
            if (blue > 255 || blue < 0) blue = 0;
            builder.append(net.md_5.bungee.api.ChatColor.of(new Color((int) red, (int) green, (int) blue)));
            builder.append(currentColourCode).append(s);
            i++;
        }
        return builder.toString();
    }

    /**
     * Utility method to make a string monochrome. Supports colour codes. &r goes back to monochrome.
     *
     * @param string The string you want to be monochrome.
     * @return The monochrome string.
     */
    public static String monochrome(String string) {
        double frequency = 0.3;
        int amplitude = 127;
        int center = 128;
        StringBuilder builder = new StringBuilder();
        int i = 0;
        String currentColourCode = "";
        for (String s : string.split("")) {
            if (s.equals("§")) {
                i++;
                continue;
            }
            if (i > 0) {
                if (string.charAt(i - 1) == '§') {
                    if (s.equalsIgnoreCase("r")) {
                        currentColourCode = "";
                        i++;
                        continue;
                    } else if ("abcdefklmnor0123456789".contains(s)) {
                        currentColourCode += "§" + s;
                        i++;
                        continue;
                    }
                }
            }
            double v = Math.sin(frequency * i) * amplitude + center;
            builder.append(net.md_5.bungee.api.ChatColor.of(new Color((int) v, (int) v, (int) v)));
            builder.append(currentColourCode).append(s);
            i++;
        }
        return builder.toString();
    }

    /**
     * Utility method to register listenners.
     *
     * @param plugin The owning plugin of these listeners.
     * @param listeners The instances of the listener class.
     */
    public static void registerListeners(JavaPlugin plugin, Listener... listeners) {
        Arrays.stream(listeners).forEach(listener -> Bukkit.getServer().getPluginManager().registerEvents(listener, plugin));
    }
}