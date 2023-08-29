package me.redth.vapev69;

import me.redth.vapev69.config.VapeConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid = "@ID@", name = "@NAME@", version = "@VER@")
public class VapeV69 {
    private static final Minecraft mc = Minecraft.getMinecraft();
    public static VapeConfig config;

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        MinecraftForge.EVENT_BUS.register(new EventListener());
        config = new VapeConfig();
    }

    public static boolean isEnabledAndRendering() {
        return config.enabled && EventListener.isInRenderTick;
    }

    public static boolean isSword(ItemStack item) {
        return VapeConfig.blockhit && item != null && item.getItem() instanceof ItemSword;
    }

    public static boolean hasSafeWalk() {
        return VapeConfig.safewalk;
    }

    public static boolean shouldSpin(EntityPlayer player) {
        return player.equals(mc.thePlayer) && config.enabled && VapeConfig.aimbot;
    }


}
