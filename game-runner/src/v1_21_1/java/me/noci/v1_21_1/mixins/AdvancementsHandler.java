package me.noci.v1_21_1.mixins;

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
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Iterator;
import java.util.Map;

@Mixin(ClientAdvancements.class)
public abstract class AdvancementsHandler {

    @Inject(
            method = "update",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/components/toasts/ToastComponent;addToast(Lnet/minecraft/client/gui/components/toasts/Toast;)V"
            ),
            locals = LocalCapture.CAPTURE_FAILHARD,
            cancellable = true
    )
    public void update(ClientboundUpdateAdvancementsPacket packet, CallbackInfo callbackInfo, Iterator it, Map.Entry entry, AdvancementNode advancement) {

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