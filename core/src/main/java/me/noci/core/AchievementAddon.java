package me.noci.core;

import me.noci.core.listener.AdvancementReceivedListener;
import net.labymod.api.addon.LabyAddon;
import net.labymod.api.models.addon.annotation.AddonMain;

@AddonMain
public class AchievementAddon extends LabyAddon<AchievementConfiguration> {

    private static AchievementAddon instance;

    public static boolean enabled() {
        return instance.configuration().enabled().get();
    }

    public AchievementAddon() {
        instance = this;
    }

    @Override
    protected void enable() {
        registerSettingCategory();
        registerListener(new AdvancementReceivedListener(configuration()));
    }

    @Override
    protected Class<AchievementConfiguration> configurationClass() {
        return AchievementConfiguration.class;
    }

}
