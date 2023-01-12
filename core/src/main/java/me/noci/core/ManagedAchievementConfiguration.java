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
  @TextFieldSetting
  private ConfigProperty<String> messageSingleplayer = new ConfigProperty<>(
      "&aYou just received a new advancements: &e%name%");

  @SettingSection(value = "multiplayer")
  @DropdownSetting
  private ConfigProperty<AchievementStatus> statusMultiplayer = new ConfigProperty<>(
      AchievementStatus.HIDDEN);
  @TextFieldSetting
  private ConfigProperty<String> messageMultiplayer = new ConfigProperty<>(
      "&aYou just received a new advancements: &e%name%");


  @Override
  public ConfigProperty<Boolean> enabled() {
    return enabled;
  }

  public ConfigProperty<AchievementStatus> status() {
    return Laby.labyAPI().serverController().isConnected() ?
        statusMultiplayer : statusSingleplayer;
  }

  public ConfigProperty<String> message() {
    return Laby.labyAPI().serverController().isConnected() ?
        messageMultiplayer : messageSingleplayer;
  }

}
