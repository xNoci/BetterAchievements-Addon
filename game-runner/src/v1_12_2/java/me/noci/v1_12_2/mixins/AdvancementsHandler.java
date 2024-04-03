package me.noci.v1_12_2.mixins;

import me.noci.core.AchievementAddon;
import me.noci.core.event.AdvancementReceivedEvent;
import net.labymod.api.Laby;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.client.multiplayer.ClientAdvancementManager;
import net.minecraft.network.play.server.SPacketAdvancementInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Iterator;
import java.util.Map;

@Mixin(ClientAdvancementManager.class)
public class AdvancementsHandler {

    @Inject(
            method = "read",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/toasts/GuiToast;add(Lnet/minecraft/client/gui/toasts/IToast;)V"
            ),
            locals = LocalCapture.CAPTURE_FAILHARD,
            cancellable = true
    )
    public void read(SPacketAdvancementInfo packetAdvancementInfo, CallbackInfo callbackInfo, Iterator it, Map.Entry entry, Advancement advancement) {
        if (!AchievementAddon.enabled()) {
            return;
        }

        DisplayInfo display = advancement.getDisplay();
        if (display == null) return;

        AdvancementReceivedEvent event = Laby.fireEvent(new AdvancementReceivedEvent(display.getTitle().getFormattedText(), display.getDescription().getFormattedText()));

        if (event.hideToast()) {
            callbackInfo.cancel();
        }
    }

}
