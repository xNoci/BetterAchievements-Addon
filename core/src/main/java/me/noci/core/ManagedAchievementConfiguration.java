package me.noci.core;

import me.noci.core.utils.AchievementStatus;
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
public class ManagedAchievementConfiguration extends AddonConfig {

    @SwitchSetting
    private final ConfigProperty<Boolean> enabled = new ConfigProperty<>(true);

    @SettingSection(value = "singleplayer")
    @DropdownSetting
    private ConfigProperty<AchievementStatus> statusSingleplayer = new ConfigProperty<>(
            AchievementStatus.DEFAULT);

    @SwitchSetting
    private ConfigProperty<Boolean> showDescriptionSingleplayer = new ConfigProperty<>(true);

    @TextFieldSetting
    private ConfigProperty<String> messageSingleplayer = new ConfigProperty<>(
            "&aYou just received a new advancements: &e%name%");

    @TextFieldSetting
    private ConfigProperty<String> descriptionTextSingleplayer = new ConfigProperty<>(
            "&7&o(&e&o%description%&7&o)");

    @SettingSection(value = "multiplayer")
    @DropdownSetting
    private ConfigProperty<AchievementStatus> statusMultiplayer = new ConfigProperty<>(
            AchievementStatus.HIDDEN);

    @SwitchSetting
    private ConfigProperty<Boolean> showDescriptionMultiplayer = new ConfigProperty<>(true);

    @TextFieldSetting
    private ConfigProperty<String> messageMultiplayer = new ConfigProperty<>(
            "&aYou just received a new advancements: &e%name%");

    @TextFieldSetting
    private ConfigProperty<String> descriptionTextMultiplayer = new ConfigProperty<>(
            "&7&o(&e&o%description%&7&o)");


    @Override
    public ConfigProperty<Boolean> enabled() {
        return enabled;
    }

    public ConfigProperty<AchievementStatus> status() {
        return Laby.labyAPI().serverController().isConnected() ?
                statusMultiplayer : statusSingleplayer;
    }

    public ConfigProperty<Boolean> showDescription() {
        return Laby.labyAPI().serverController().isConnected() ?
                showDescriptionMultiplayer : showDescriptionSingleplayer;
    }

    public ConfigProperty<String> message() {
        return Laby.labyAPI().serverController().isConnected() ?
                messageMultiplayer : messageSingleplayer;
    }

    public ConfigProperty<String> description() {
        return Laby.labyAPI().serverController().isConnected() ?
                descriptionTextMultiplayer : descriptionTextSingleplayer;
    }

}
