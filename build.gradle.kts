plugins {
    id("net.labymod.labygradle")
    id("net.labymod.labygradle.addon")
}

val versions = providers.gradleProperty("net.labymod.minecraft-versions").get().split(";")

group = "me.noci"
version = providers.environmentVariable("VERSION").getOrElse("1.3.4")

labyMod {
    defaultPackageName = "me.noci"

    minecraft {
        registerVersion(versions.toTypedArray()) {
            runs {
                getByName("client") {
                    // When the property is set to true, you can log in with a Minecraft account
                    // devLogin = true
                    //TODO
                }
            }
        }
    }

    addonInfo {
        namespace = "managedachievement"
        displayName = "Better Achievements"
        author = "Noci"
        description = "This addon gives you the possibility to change the display type for achievements in single and multiplayer mode. \nPossible display types are: \"DEFAULT, CHAT, BOTH, HIDDEN\""
        minecraftVersion = "1.8.9<1.21.3"
        version = rootProject.version.toString()
    }
}

subprojects {
    plugins.apply("net.labymod.labygradle")
    plugins.apply("net.labymod.labygradle.addon")

    group = rootProject.group
    version = rootProject.version
}