package com.ankoki.blossom.builders;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class LoreBuilder {
    List<String> lines = new ArrayList<>();

    public LoreBuilder(String... lines) {
        for (String line : lines) {
            this.lines.add(ChatColor.translateAlternateColorCodes('&', line));
        }
    }

    public List<String> build() {
        return lines;
    }
}