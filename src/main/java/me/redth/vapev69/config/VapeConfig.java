package me.redth.vapev69.config;

import cc.polyfrost.oneconfig.config.Config;
import cc.polyfrost.oneconfig.config.annotations.HUD;
import cc.polyfrost.oneconfig.config.annotations.Switch;
import cc.polyfrost.oneconfig.config.data.Mod;
import cc.polyfrost.oneconfig.config.data.ModType;

public class VapeConfig extends Config {

    @Switch(name = "Auto Blockhit")
    public static boolean blockhit = false;

    @Switch(name = "Safewalk")
    public static boolean safewalk = false;

    @Switch(name = "Spinning Head")
    public static boolean spinning = false;

    @HUD(name = "List")
    public static VapeHUD hud = new VapeHUD();

    public VapeConfig() {
        super(new Mod("VapeV69", ModType.PVP, "/logo.png", 88, 24), "vapev69.json");
        initialize();
    }
}