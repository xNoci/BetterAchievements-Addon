package me.noci.core;

import me.noci.core.utils.AchievementStatus;
import net.labymod.api.Laby;
import net.labymod.api.addon.LabyAddon;
import net.labymod.api.models.addon.annotation.AddonMain;

@AddonMain
public class ManagedAchievementAddon extends LabyAddon<ManagedAchievementConfiguration> {

    private static ManagedAchievementAddon instance;

    public ManagedAchievementAddon() {
        instance = this;
    }

    public static ManagedAchievementAddon get() {
        return instance;
    }

    @Override
    protected void enable() {
        registerSettingCategory();
    }

    @Override
    protected Class<ManagedAchievementConfiguration> configurationClass() {
        return ManagedAchievementConfiguration.class;
    }

    public void sendAdvancement(AchievementStatus status, String displayName) {
        if (status != AchievementStatus.BOTH && status != AchievementStatus.CHAT) {
            return;
        }

        String message = configuration().message().get();
        message = message.replaceAll("%name%", displayName);
        message = Laby.references().componentMapper().translateColorCodes('&', '\u00a7', message);

        displayMessage(message);
    }

}
