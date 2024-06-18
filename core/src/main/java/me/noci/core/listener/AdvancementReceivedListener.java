package me.noci.core.listener;

import me.noci.core.AchievementConfiguration;
import me.noci.core.AchievementStatus;
import me.noci.core.event.AdvancementReceivedEvent;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.event.HoverEvent;
import net.labymod.api.event.Subscribe;

public class AdvancementReceivedListener {

    private final AchievementConfiguration config;

    public AdvancementReceivedListener(AchievementConfiguration config) {
        this.config = config;
    }

    @Subscribe
    public void onAdvancementReceived(AdvancementReceivedEvent event) {
        AchievementStatus status = config.status();
        event.hideToast(status == AchievementStatus.CHAT || status == AchievementStatus.HIDDEN);

        if (status != AchievementStatus.BOTH && status != AchievementStatus.CHAT) {
            return;
        }

        String message = config.message();
        message = message.replaceAll("%name%", event.title());
        message = Laby.references().componentMapper().translateColorCodes(message);

        Component component = Component.text(message);

        if (config.showDescription() && !event.description().isBlank()) {
            String descriptionText = config.description();
            descriptionText = descriptionText.replaceAll("%description%", event.description());
            descriptionText = Laby.references().componentMapper().translateColorCodes('&', '\u00a7', descriptionText);
            HoverEvent<Component> hoverText = HoverEvent.showText(Component.text(descriptionText));
            component.hoverEvent(hoverText);
        }

        Laby.labyAPI().minecraft().chatExecutor().displayClientMessage(component);

    }

}
