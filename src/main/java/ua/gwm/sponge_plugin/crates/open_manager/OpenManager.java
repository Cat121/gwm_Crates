package ua.gwm.sponge_plugin.crates.open_manager;

import ninja.leaping.configurate.ConfigurationNode;
import org.spongepowered.api.entity.living.player.Player;
import ua.gwm.sponge_plugin.crates.manager.Manager;

public abstract class OpenManager {

    protected OpenManager() {
    }

    public OpenManager(ConfigurationNode node) {
    }

    public abstract void open(Player player, Manager manager);
}
