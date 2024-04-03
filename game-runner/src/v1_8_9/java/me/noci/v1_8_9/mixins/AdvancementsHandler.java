package me.noci.v1_8_9.mixins;

import me.noci.core.AchievementAddon;
import me.noci.core.event.AdvancementReceivedEvent;
import net.labymod.api.Laby;
import net.minecraft.client.gui.achievement.GuiAchievement;
import net.minecraft.stats.Achievement;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiAchievement.class)
public abstract class AdvancementsHandler {

    @Inject(method = "displayAchievement", at = @At("HEAD"), cancellable = true)
    public void displayAchievement(Achievement achievement, CallbackInfo callbackInfo) {
        if (!AchievementAddon.enabled()) {
            return;
        }

        AdvancementReceivedEvent event = Laby.fireEvent(new AdvancementReceivedEvent(achievement.getStatName().getFormattedText(), achievement.getDescription()));

        if (event.hideToast()) {
            callbackInfo.cancel();
        }
    }

}
