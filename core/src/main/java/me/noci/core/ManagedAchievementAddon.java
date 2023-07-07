package me.noci.core;

import me.noci.core.utils.AchievementStatus;
import net.labymod.api.Laby;
import net.labymod.api.addon.LabyAddon;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.event.HoverEvent;
import net.labymod.api.models.addon.annotation.AddonMain;
import net.labymod.api.util.StringUtil;

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

    public void sendAdvancement(AchievementStatus status, String displayName, String description) {
        if (status != AchievementStatus.BOTH && status != AchievementStatus.CHAT) {
            return;
        }

        String message = configuration().message().get();
        message = message.replaceAll("%name%", displayName);
        message = Laby.references().componentMapper().translateColorCodes('&', '\u00a7', message);

        Component component = Component.text(message);

        if(configuration().showDescription().get() && !description.isBlank()) {
            String descriptionText = configuration().description().get();
            descriptionText = descriptionText.replaceAll("%description%", description);
            descriptionText = Laby.references().componentMapper().translateColorCodes('&', '\u00a7', descriptionText);
            HoverEvent<Component> hoverText = HoverEvent.showText(Component.text(descriptionText));
            component.hoverEvent(hoverText);
        }

        displayMessage(component);
    }

}
