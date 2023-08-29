package me.redth.vapev69.config;

import cc.polyfrost.oneconfig.hud.TextHud;

import java.util.List;

public class VapeHUD extends TextHud {
    public VapeHUD() {
        super(false);
    }

    @Override
    protected void getLines(List<String> lines, boolean example) {
        if (VapeConfig.blockhit) lines.add("Auto Blockhit");
        if (VapeConfig.safewalk) lines.add("Safewalk");
        if (VapeConfig.spinning) lines.add("KillAura");
    }
}
