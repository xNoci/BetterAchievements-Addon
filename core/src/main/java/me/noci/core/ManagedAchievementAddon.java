package me.noci.core;

import com.google.inject.Singleton;
import me.noci.core.utils.AchievementStatus;
import net.labymod.api.addon.LabyAddon;
import net.labymod.api.client.render.font.ComponentMapper;
import net.labymod.api.inject.LabyGuice;
import net.labymod.api.models.addon.annotation.AddonListener;

@Singleton
@AddonListener
public class ManagedAchievementAddon extends LabyAddon<ManagedAchievementConfiguration> {

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
        message = LabyGuice.getInstance(ComponentMapper.class).translateColorCodes('&', '§', message);

        displayMessage(message);
    }

}
