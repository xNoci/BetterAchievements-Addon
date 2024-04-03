package me.noci.core.event;

import lombok.Getter;
import lombok.Setter;
import net.labymod.api.event.Event;

public class AdvancementReceivedEvent implements Event {

    @Getter private final String title;
    @Getter private final String description;

    @Getter @Setter private boolean hideToast = false;

    public AdvancementReceivedEvent(String title, String description) {
        this.title = title;
        this.description = description;
    }

}
