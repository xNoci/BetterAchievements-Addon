package me.noci.core.event;

import lombok.Getter;
import lombok.Setter;
import net.labymod.api.event.Event;

@Getter
public class AdvancementReceivedEvent implements Event {

    private final String title;
    private final String description;

    @Setter private boolean hideToast = false;

    public AdvancementReceivedEvent(String title, String description) {
        this.title = title;
        this.description = description;
    }

}
