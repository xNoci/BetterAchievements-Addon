package me.noci.v1_12_2.mixins;

import me.noci.core.ManagedAchievementAddon;
import me.noci.core.ManagedAchievementConfiguration;
import me.noci.core.utils.AchievementStatus;
import net.minecraft.advancements.Advancement;
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

    private ManagedAchievementAddon addon;
    private ManagedAchievementConfiguration configuration;

    @Inject(method = "read", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/gui/toasts/GuiToast;add(Lnet/minecraft/client/gui/toasts/IToast;)V"),
            locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    public void read(SPacketAdvancementInfo packetAdvancementInfo, CallbackInfo callbackInfo, Iterator it, Map.Entry entry, Advancement advancement) {
        if(addon == null) {
            addon = ManagedAchievementAddon.get();
            configuration = addon.configuration();
        }

        if(!configuration.enabled().get()) {
            return;
        }

        AchievementStatus status = configuration.status().get();
        boolean hideToast =  status == AchievementStatus.CHAT || status == AchievementStatus.HIDDEN;

        addon.sendAdvancement(status, advancement.getDisplay().getTitle().getFormattedText());

        if(hideToast) {
            callbackInfo.cancel();
        }
    }

}
