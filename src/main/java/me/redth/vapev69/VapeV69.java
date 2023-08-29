package me.redth.vapev69;

import me.redth.vapev69.config.VapeConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod(modid = "@ID@", name = "@NAME@", version = "@VER@")
public class VapeV69 {
    private static final Minecraft mc = Minecraft.getMinecraft();
    public static VapeConfig config;
    public static boolean isInRenderTick;

    public static float prevRotationYawHead;
    public static float rotationYawHead;
    public static float prevRotationPitch;
    public static float rotationPitch;

    public static float prevRotationYawHeadVisual;
    public static float prevRotationPitchVisual;
    public static float rotationYawHeadVisual;
    public static float rotationPitchVisual;

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        MinecraftForge.EVENT_BUS.register(this);
        config = new VapeConfig();
    }

    @SubscribeEvent
    public void playerPreRender(RenderPlayerEvent.Pre e) {
        if (!e.entityPlayer.equals(mc.thePlayer) || !config.enabled || !VapeConfig.spinning) return;

        prevRotationYawHead = mc.thePlayer.prevRotationYawHead;
        rotationYawHead = mc.thePlayer.rotationYawHead;
        prevRotationPitch = mc.thePlayer.prevRotationPitch;
        rotationPitch = mc.thePlayer.rotationPitch;

        mc.thePlayer.prevRotationYawHead = prevRotationYawHeadVisual;
        mc.thePlayer.prevRotationPitch = prevRotationPitchVisual;
        mc.thePlayer.rotationYawHead = rotationYawHeadVisual;
        mc.thePlayer.rotationPitch = rotationPitchVisual;
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent e) {
        if (e.phase != TickEvent.Phase.END) return;
        if (mc.theWorld == null || mc.thePlayer == null) return;
        getDirection();
    }

    public static void getDirection() {
        prevRotationYawHeadVisual = rotationYawHeadVisual;
        prevRotationPitchVisual = rotationPitchVisual;

        EntityPlayer nearestEntity = mc.theWorld.findNearestEntityWithinAABB(EntityPlayer.class, mc.thePlayer.getEntityBoundingBox().expand(5, 5, 5), mc.thePlayer);
        if (nearestEntity == null) {

            rotationYawHeadVisual = System.currentTimeMillis() % 360;
            rotationPitchVisual = (float) Math.sin(System.currentTimeMillis() % 360) * 90;

        } else {

            Vec3 difference = mc.thePlayer.getPositionEyes(1).subtract(nearestEntity.getPositionEyes(1));

            double yaw = Math.toDegrees(Math.atan2(difference.zCoord, difference.xCoord)) + 90;
            yaw = (yaw + 360) % 360;

            double horizontalDistance = Math.sqrt(difference.xCoord * difference.xCoord + difference.zCoord * difference.zCoord);
            double pitch = Math.toDegrees(Math.atan2(difference.yCoord, horizontalDistance));
            pitch = (pitch + 360) % 360;

            rotationYawHeadVisual = (float) yaw;
            rotationPitchVisual = (float) pitch;
        }
    }

    @SubscribeEvent
    public void playerPostRender(RenderPlayerEvent.Post e) {
        if (!e.entityPlayer.equals(mc.thePlayer) || !config.enabled || !VapeConfig.spinning) return;
        mc.thePlayer.prevRotationYawHead = prevRotationYawHead;
        mc.thePlayer.rotationYawHead = rotationYawHead;
        mc.thePlayer.prevRotationPitch = prevRotationPitch;
        mc.thePlayer.rotationPitch = rotationPitch;
    }

    public static boolean isEnabledAndRendering() {
        return config.enabled && isInRenderTick;
    }

    public static boolean isSword(ItemStack item) {
        return VapeConfig.blockhit && item != null && item.getItem() instanceof ItemSword;
    }

    public static boolean hasSafeWalk() {
        return VapeConfig.safewalk;
    }


    @SubscribeEvent
    public void onRenderTick(TickEvent.RenderTickEvent event) {
        isInRenderTick = event.phase == TickEvent.Phase.START;
    }

}
