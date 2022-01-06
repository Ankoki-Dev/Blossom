package com.ankoki.blossom.utils;

import com.ankoki.blossom.items.Enchant;
import net.md_5.bungee.api.ChatColor;

import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Chat {

    private static final Pattern pattern = Pattern.compile("(<#[\\da-fA-F]{6}>)");

    /**
     * Utility to format convert a string into a coloured string, supporting
     * rainbow (&t) and pastel rainbows (&u).
     *
     * @param text the unformatted text.
     * @return formatted text.
     */
    public static String format(String text) {
        double freq1 = 0.3, freq2 = 0.3, freq3 = 0.3;
        double amp1 = 0, amp2 = 2, amp3 = 4;
        int center = 0;
        int width = 0;
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            String matched = matcher.group(1)
                    .replace("<", "")
                    .replace(">", "");
            text = text.replace("<" + matched + ">", ChatColor.of(matched).toString());
        }
        char[] b = text.toCharArray();
        for(int i = 0; i < b.length - 1; ++i) {
            if (b[i] == '&' && "0123456789AaBbCcDdEeFfKkLlMmNnOoRrXxTtUu".indexOf(b[i + 1]) > -1) {
                b[i] = 167;
                b[i + 1] = Character.toLowerCase(b[i + 1]);
            }
        }
        text = new String(b);
        StringBuilder builder = new StringBuilder();
        int i = 0;
        boolean skippingHex = false;
        boolean isRainbow = false;
        int skipNo = 0;
        String currentColourCode = "§r";
        String currentModifier = "";
        for (String s : text.split("")) {
            if (skippingHex) {
                if (skipNo == 11) {
                    skippingHex = false;
                    continue;
                }
                skipNo++;
                continue;
            } else if (s.equals("§")) {
                i++;
                continue;
            } else if (s.equals(" ")) {
                i++;
                builder.append(" ");
                continue;
            } else if (i > 0) {
                if (text.charAt(i - 1) == '§') {
                    if (s.equalsIgnoreCase("t")) {
                        center = 128;
                        width = 127;
                        currentColourCode = "";
                        currentModifier = "";
                        isRainbow = true;
                        i++;
                        continue;
                    } else if (s.equalsIgnoreCase("u")) {
                        center = 200;
                        width = 55;
                        currentColourCode = "";
                        currentModifier = "";
                        isRainbow = true;
                        i++;
                        continue;
                    } else if (s.equalsIgnoreCase("x")) {
                        int x = i + 14;
                        if (text.length() >= x) {
                            char[] arr = new char[12];
                            System.arraycopy(text.toCharArray(), i + 1, arr, 0, 12);
                            String following = new String(arr);
                            matcher = pattern.matcher(following);
                            if (matcher.find()) {
                                currentColourCode = "§x" + matcher.group(1);
                                i = i + 13;
                                skippingHex = true;
                                skipNo = 0;
                                isRainbow = false;
                                continue;
                            }
                        }
                    } else if ("klmnoKLMNO".contains(s)) {
                        currentModifier += "§" + s;
                        i++;
                        continue;
                    } else if ("abcdefrABCDEFR0123456789".contains(s)) {
                        currentColourCode = "§" + s;
                        currentModifier = "";
                        isRainbow = false;
                        i++;
                        continue;
                    }
                }
            }
            if (!isRainbow) {
                builder.append(currentColourCode)
                        .append(currentModifier).append(s);
                i++;
                continue;
            }
            float red = (float) (Math.sin(freq1 * i + amp1) * width + center);
            float green = (float) (Math.sin(freq2 * i + amp2) * width + center);
            float blue = (float) (Math.sin(freq3 * i + amp3) * width + center);
            if (red > 255 || red < 0) red = 0;
            if (green > 255 || green < 0) green = 0;
            if (blue > 255 || blue < 0) blue = 0;
            builder.append(ChatColor.of(new Color((int) red, (int) green, (int) blue)))
                    .append(currentModifier)
                    .append(s);
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
     * Colours a basic string with bukkits basic colour.
     *
     * @param text the text to be coloured.
     * @return the coloured text.
     */
    public static String coloured(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    /**
     * Converts a string to title case
     * <p>
     * For example, "hello world" to "Hello World"
     *
     * @param text text to be in title case.
     * @return string in title case.
     */
    public static String toTitleCase(String text) {
        if (text == null || text.isEmpty() || text.isBlank()) return "";
        if (text.length() == 1) return text.toUpperCase();
        text = text.replace("_", " ");

        StringBuilder builder = new StringBuilder(text.length());

        Stream.of(text.split(" ")).forEach(stringPart -> {
            char[] charArray = stringPart.toLowerCase().toCharArray();
            charArray[0] = Character.toUpperCase(charArray[0]);
            builder.append(new String(charArray)).append(" ");
        });
        builder.setLength(builder.length() - 1);
        return builder.toString();
    }

    /**
     * Formats an enchant to be the same as minecraft lore.
     *
     * @param enchant the enchantment.
     * @param level level of the enchantment.
     * @return formatted enchantment.
     */
    public static String formattedEnchant(Enchant enchant, int level) {
        return enchant.toString() + " " + Utils.toRoman(level);
    }

    /**
     * Appends the english order suffix to the given number.
     *
     * @param i the number.
     * @return 1st, 2nd, 3rd, 4th, etc.
     */
    public static String numberSuffix(int i) {
        int iModTen = i % 10;
        int iModHundred = i % 100;
        if (iModTen == 1 && iModHundred != 11) return i + "st";
        else if (iModTen == 2 && iModHundred != 12) return i + "nd";
        else if (iModTen == 3 && iModHundred != 13) return i + "rd";
        else return i + "th";
    }
}
