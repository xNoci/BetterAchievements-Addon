package me.noci.core;

import net.labymod.api.Laby;
import net.labymod.api.addon.AddonConfig;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget.SwitchSetting;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget.TextFieldSetting;
import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.DropdownWidget.DropdownSetting;
import net.labymod.api.configuration.loader.annotation.ConfigName;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.settings.annotation.SettingSection;

@SuppressWarnings("FieldMayBeFinal")
@ConfigName("settings")
public class AchievementConfiguration extends AddonConfig {

    @SwitchSetting
    private final ConfigProperty<Boolean> enabled = new ConfigProperty<>(true);

    @SettingSection(value = "singleplayer")
    @DropdownSetting
    private ConfigProperty<AchievementStatus> statusSingleplayer = new ConfigProperty<>(AchievementStatus.DEFAULT);

    @SwitchSetting
    private ConfigProperty<Boolean> showDescriptionSingleplayer = new ConfigProperty<>(true);

    @TextFieldSetting
    private ConfigProperty<String> messageSingleplayer = new ConfigProperty<>("&aYou just received a new advancements: &e%name%");

    @TextFieldSetting
    private ConfigProperty<String> descriptionTextSingleplayer = new ConfigProperty<>("&7&o(&e&o%description%&7&o)");

    @SettingSection(value = "multiplayer")
    @DropdownSetting
    private ConfigProperty<AchievementStatus> statusMultiplayer = new ConfigProperty<>(AchievementStatus.HIDDEN);

    @SwitchSetting
    private ConfigProperty<Boolean> showDescriptionMultiplayer = new ConfigProperty<>(true);

    @TextFieldSetting
    private ConfigProperty<String> messageMultiplayer = new ConfigProperty<>("&aYou just received a new advancements: &e%name%");

    @TextFieldSetting
    private ConfigProperty<String> descriptionTextMultiplayer = new ConfigProperty<>("&7&o(&e&o%description%&7&o)");


    @Override
    public ConfigProperty<Boolean> enabled() {
        return enabled;
    }

    public AchievementStatus status() {
        return get(statusSingleplayer, statusMultiplayer);
    }

    public boolean showDescription() {
        return get(showDescriptionSingleplayer, showDescriptionMultiplayer);
    }

    public String message() {
        return get(messageSingleplayer, messageMultiplayer);
    }

    public String description() {
        return get(descriptionTextSingleplayer, descriptionTextMultiplayer);
    }

    private <T> T get(ConfigProperty<T> singlePlayer, ConfigProperty<T> multiplayer) {
        ConfigProperty<T> currentProperty = Laby.labyAPI().serverController().isConnected() ? multiplayer : singlePlayer;
        return currentProperty.get();
    }

}
