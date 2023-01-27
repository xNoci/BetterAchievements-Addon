package me.noci.v1_17_1;

import me.noci.core.ManagedAchievementAddon;
import me.noci.core.ManagedAchievementConfiguration;
import me.noci.core.utils.AchievementStatus;
import net.minecraft.advancements.Advancement;
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

    private ManagedAchievementAddon addon;
    private ManagedAchievementConfiguration configuration;

    @Inject(method = "update", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/gui/components/toasts/ToastComponent;addToast(Lnet/minecraft/client/gui/components/toasts/Toast;)V"),
            locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    public void update(ClientboundUpdateAdvancementsPacket packet, CallbackInfo callbackInfo,
                       Iterator it, Map.Entry entry, Advancement advancement) {

        if (this.addon == null) {
            this.addon = ManagedAchievementAddon.get();
            this.configuration = this.addon.configuration();
        }

        if (!configuration.enabled().get()) {
            return;
        }

        AchievementStatus status = configuration.status().get();
        boolean hideToast = status == AchievementStatus.CHAT || status == AchievementStatus.HIDDEN;

        this.addon.sendAdvancement(status, advancement.getDisplay().getTitle().getString());

        if (hideToast) {
            callbackInfo.cancel();
        }
    }

}
