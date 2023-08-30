package me.redth.vapev69;

import me.redth.vapev69.config.VapeConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import javax.swing.*;

import static me.redth.vapev69.config.VapeConfig.distance;

public class EventListener {
    private static final Minecraft mc = Minecraft.getMinecraft();

    public static boolean isInRenderTick;

    public static float prevRotationYawHead;
    public static float rotationYawHead;
    public static float prevRotationPitch;
    public static float rotationPitch;

    public static float prevRotationYawHeadVisual;
    public static float prevRotationPitchVisual;
    public static float rotationYawHeadVisual;
    public static float rotationPitchVisual;


    @SubscribeEvent
    public void playerPreRender(RenderPlayerEvent.Pre e) {
        if (!VapeV69.shouldSpin(e.entityPlayer)) return;

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
        if (!VapeV69.shouldSpin(mc.thePlayer)) return;
        getDirection();
    }

    public static void getDirection() {
        prevRotationYawHeadVisual = rotationYawHeadVisual;
        prevRotationPitchVisual = rotationPitchVisual;

        EntityPlayer nearestEntity = mc.theWorld.findNearestEntityWithinAABB(EntityPlayer.class, mc.thePlayer.getEntityBoundingBox().expand(distance, distance, distance), mc.thePlayer);

        if (nearestEntity == null) {

            rotationYawHeadVisual = VapeConfig.spinning ? System.currentTimeMillis() % 360 : mc.thePlayer.rotationYawHead;
            rotationPitchVisual = VapeConfig.spinning ? ((float) Math.sin(System.currentTimeMillis() / 360D) * 90) : mc.thePlayer.rotationPitch;

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
        if (!VapeV69.shouldSpin(e.entityPlayer)) return;
        mc.thePlayer.prevRotationYawHead = prevRotationYawHead;
        mc.thePlayer.rotationYawHead = rotationYawHead;
        mc.thePlayer.prevRotationPitch = prevRotationPitch;
        mc.thePlayer.rotationPitch = rotationPitch;
    }

    @SubscribeEvent
    public void onRenderTick(TickEvent.RenderTickEvent event) {
        isInRenderTick = event.phase == TickEvent.Phase.START;
    }


}
