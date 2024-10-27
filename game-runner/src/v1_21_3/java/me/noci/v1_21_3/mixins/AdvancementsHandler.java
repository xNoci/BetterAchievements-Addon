package me.noci.v1_21_3.mixins;

import com.llamalad7.mixinextras.sugar.Local;
import me.noci.core.AchievementAddon;
import me.noci.core.event.AdvancementReceivedEvent;
import net.labymod.api.Laby;
import net.minecraft.advancements.AdvancementNode;
import net.minecraft.client.multiplayer.ClientAdvancements;
import net.minecraft.network.protocol.game.ClientboundUpdateAdvancementsPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientAdvancements.class)
public abstract class AdvancementsHandler {

    @Inject(
            method = "update",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/components/toasts/ToastManager;addToast(Lnet/minecraft/client/gui/components/toasts/Toast;)V"
            ),
            cancellable = true
    )
    public void update(ClientboundUpdateAdvancementsPacket packet, CallbackInfo callbackInfo, @Local AdvancementNode advancement) {
        if (!AchievementAddon.enabled()) {
            return;
        }

        advancement.advancement().display().ifPresent(display -> {
            AdvancementReceivedEvent event = Laby.fireEvent(new AdvancementReceivedEvent(display.getTitle().getString(), display.getDescription().getString()));

            if (event.hideToast()) {
                callbackInfo.cancel();
            }
        });
    }

}