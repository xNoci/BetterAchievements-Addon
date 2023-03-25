package me.noci.v1_8_9.mixins;

import me.noci.core.ManagedAchievementAddon;
import me.noci.core.ManagedAchievementConfiguration;
import me.noci.core.utils.AchievementStatus;
import net.minecraft.client.gui.achievement.GuiAchievement;
import net.minecraft.stats.Achievement;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiAchievement.class)
public abstract class AdvancementsHandler {

    private ManagedAchievementAddon addon;
    private ManagedAchievementConfiguration configuration;

    @Inject(method = "displayAchievement", at = @At("HEAD"), cancellable = true)
    public void displayAchievement(Achievement achievement, CallbackInfo callbackInfo) {
        if (this.addon == null) {
            this.addon = ManagedAchievementAddon.get();
            this.configuration = this.addon.configuration();
        }

        if (!configuration.enabled().get()) {
            return;
        }

        AchievementStatus status = configuration.status().get();
        boolean hideToast = status == AchievementStatus.CHAT || status == AchievementStatus.HIDDEN;

        this.addon.sendAdvancement(status, achievement.getStatName().getUnformattedText());

        if (hideToast) {
            callbackInfo.cancel();
        }
    }

}
