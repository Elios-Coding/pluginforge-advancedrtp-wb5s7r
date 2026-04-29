package com.pluginforge.advancedrtp;

import java.util.Arrays;
import org.bukkit.plugin.java.JavaPlugin;

public class AdvancedRtpPlugin extends JavaPlugin {
    private CombatTracker combatTracker;
    private CooldownManager cooldownManager;

    @Override
    public void onEnable() {
        saveDefaultSettings();
        this.combatTracker = new CombatTracker(this);
        this.cooldownManager = new CooldownManager(this);

        RtpCommand command = new RtpCommand(this, cooldownManager, combatTracker);
        if (getCommand("rtp") != null) {
            getCommand("rtp").setExecutor(command);
            getCommand("rtp").setTabCompleter(command);
        }

        getServer().getPluginManager().registerEvents(combatTracker, this);
        getServer().getPluginManager().registerEvents(new RtpGuiListener(this), this);
    }

    private void saveDefaultSettings() {
        getConfig().addDefault("rtp-range.min", 100);
        getConfig().addDefault("rtp-range.max", 2500);
        getConfig().addDefault("cooldown-seconds", 300);
        getConfig().addDefault("combat-seconds", 10);
        getConfig().addDefault("warmup-seconds", 3);
        getConfig().addDefault("allow-water", false);
        getConfig().addDefault("allowed-worlds", Arrays.asList("world"));
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    public int getSelectedMin() {
        return getConfig().getInt("rtp-range.min", 100);
    }

    public int getSelectedMax() {
        return getConfig().getInt("rtp-range.max", 2500);
    }

    public void setSelectedRange(int min, int max) {
        getConfig().set("rtp-range.min", min);
        getConfig().set("rtp-range.max", max);
        saveConfig();
    }
}
