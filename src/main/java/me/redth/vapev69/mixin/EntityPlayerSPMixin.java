package me.redth.vapev69.mixin;

import com.mojang.authlib.GameProfile;
import me.redth.vapev69.VapeV69;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityPlayerSP.class)
public abstract class EntityPlayerSPMixin extends AbstractClientPlayer {

    public EntityPlayerSPMixin(World worldIn, GameProfile playerProfile) {
        super(worldIn, playerProfile);
    }

    @Inject(method = "isSneaking", at = @At("HEAD"), cancellable = true)
    public void trueDuringRenderThread(CallbackInfoReturnable<Boolean> cir) {
        if (VapeV69.isEnabledAndRendering() && VapeV69.hasSafeWalk()) cir.setReturnValue(false);
    }

    @Override
    public int getItemInUseCount() {
//        return 10;
        if (VapeV69.isEnabledAndRendering() && VapeV69.isSword(getHeldItem())) return 10;
        return super.getItemInUseCount();
    }

    @Override
    public boolean isBlocking() {
        return (VapeV69.isEnabledAndRendering() && VapeV69.isSword(getHeldItem())) || super.isBlocking();
    }
}
